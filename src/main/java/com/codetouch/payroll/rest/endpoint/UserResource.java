package com.codetouch.payroll.rest.endpoint;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.resource.spi.work.SecurityContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import com.codetouch.payroll.GsonResponse;
import com.codetouch.payroll.StatusMessage;
import com.codetouch.payroll.dto.UserDTO;
import com.codetouch.payroll.entity.User;
import com.codetouch.payroll.hash.PasswordHash;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

@Path("/users")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class UserResource {

	@Inject
	EntityManager em;

	private Gson gson;
	private GsonResponse response = new GsonResponse();

	@Inject
	Mailer mailer;
	
	@Inject
    JsonWebToken jwt;

	@ConfigProperty(name = "server.ip")
	String server;
	
	@PostConstruct
	private void postConstruct() {
		GsonBuilder builder = new GsonBuilder();

		gson = builder.create();

	}
	
	  List<StatusMessage> ml = new ArrayList<>();
	  StatusMessage m = new StatusMessage();
	  
	@SuppressWarnings("unchecked")
	@GET
	public Response viewAllUsers() {
		
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);
		List<User> el = em.createQuery(" select c from User c where c.deleted='false'").getResultList();
		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(el);
		String j = gson.toJson(response);

		return Response.status(200).entity(j).build();
	}
	@GET
	@Path("/view/{id}")
	public Response viewUser(@PathParam("id") Long userId) {
		
		m.setCode("00");
		m.setDescription("Success");
		ml.add(m);
		
		User el = em.find(User.class, userId);
		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(el);
		String j = gson.toJson(response);

		return Response.status(200).entity(j).build();
	}
	@Path("/signup")
	@POST
	@Transactional
	public Response signup(UserDTO user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		User us = new User();	
		us.setLastName(user.getLastName());
		us.setEmail(user.getEmail());
		us.setCountry(user.getCountry());
		us.setFirstName(user.getFirstName());
		us.setCity(user.getCity());
	
		String pass= PasswordHash.hash(user.getPassword());
		us.setPassword(pass);
		us.setPhoneNumber(user.getPhoneNumber());
		us.setSex(user.getSex());
		us.setState(user.getState());
		us.setConfirmationToken(UUID.randomUUID().toString());
		us.setCreatedDate(LocalDate.now());
		us.setTimeCreated(LocalTime.now());
		us.setActive(false);
		
		em.persist(us);
		
		mailer.send(Mail.withText(us.getEmail(), "Click to complete registration:",
				server+"/signup/activate?id=" + us.getConfirmationToken()));
		
		m.setCode("00");
		m.setDescription("Success, an email has been sent to you");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String json = gson.toJson(response);
		return Response.status(200).entity(json).build();
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/signup/activate")
	public Response getPendingUser(@QueryParam("id") String uId) {
		String sql = "SELECT a FROM User a WHERE a.confirmationToken=:token";
		Query query = em.createQuery(sql);
		query.setParameter("token", uId);	
		List<User>  n = query.getResultList();	
		
		if(n.isEmpty()) {
			m.setCode("00");
			m.setDescription("Sorry, Token does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}
		//question: should i put token expiry for users, if yes, when token expires, how do user complete/activate his account or request for another token
		m.setCode("00");
		m.setDescription("Token exists");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String j = gson.toJson(response);
		return Response.status(200).entity(j).build();
	}
	
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/signup/activate")
	@Transactional
	public Response activate( @QueryParam("id") String uId) {

		String sql = "SELECT a FROM User a WHERE a.confirmationToken=:token";
		Query query = em.createQuery(sql);
		query.setParameter("token", uId);	
		List<User>  n = query.getResultList();	
		
		User es =n.get(0);
		es.setActive(true);
		es.setDateActivated(LocalDateTime.now());
		em.merge(es);
	
		m.setCode("00");
		m.setDescription("Registration completed");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String j = gson.toJson(response);
		return Response.status(200).entity(j).build();
	}
	
	@PUT
	@Transactional
	@Path("/update/{id}")
	public Response updateuser(@PathParam("id") Long userId, UserDTO user) {
	
		User u = em.find(User.class, userId);
		if (u==null) {
			
			m.setCode("00");
			m.setDescription("User does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}
		u.setLastName(user.getLastName());
		u.setEmail(user.getEmail());
		u.setCountry(user.getCountry());
		u.setFirstName(user.getFirstName());
		u.setCity(user.getCity());
		u.setPassword(user.getPassword());
		u.setPhoneNumber(user.getPhoneNumber());
		u.setSex(user.getSex());
		u.setState(user.getState());
		em.merge(u);
		
		m.setCode("00");
		m.setDescription("User details successfully updated");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String j = gson.toJson(response);
		return Response.status(200).entity(j).build();
	}
	
	@PUT
	@Transactional
	@Path("/delete/{id}")
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response deleteUser(@PathParam("id") Long userId, @Context SecurityContext ctx ) {
		
		Long convert=Long.valueOf(jwt.getSubject());
		User us = em.find(User.class, convert);
		
		if(us==null) {
			
			m.setCode("00");
			m.setDescription("User does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}
		
		us.setDeleted(true);
		us.setActive(false);
		us.setDeletedBy(us);
		us.setDateDeleted(LocalDate.now());
		us.setTimeDeleted(LocalTime.now());
		em.merge(us);
		
		m.setCode("00");
		m.setDescription("User successfully deleted");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String j = gson.toJson(response);
		return Response.status(200).entity(j).build();
	}
}
