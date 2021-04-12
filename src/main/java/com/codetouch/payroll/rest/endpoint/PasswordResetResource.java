package com.codetouch.payroll.rest.endpoint;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.codetouch.payroll.GsonResponse;
import com.codetouch.payroll.StatusMessage;
import com.codetouch.payroll.dto.PasswordDTO;
import com.codetouch.payroll.entity.PasswordReset;
import com.codetouch.payroll.entity.PayrollParameter;
import com.codetouch.payroll.entity.User;
import com.codetouch.payroll.hash.PasswordHash;
import com.codetouch.payroll.qualifier.SystemParameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.vertx.core.json.JsonObject;

@Path("/password")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PasswordResetResource {

	@Inject
	EntityManager em;

	private Gson gson;
	private GsonResponse response = new GsonResponse();

	@Inject
	Mailer mailer;

	@Inject
	@SystemParameter
	PayrollParameter param;
	
	@ConfigProperty(name = "server.ip")
	String server;
	
	@PostConstruct
	private void postConstruct() {
		GsonBuilder builder = new GsonBuilder();

		gson = builder.create();

	}
	
	List<StatusMessage> ml = new ArrayList<>();
	StatusMessage m = new StatusMessage();
	
	@POST
	@Transactional
	@Path("/reset") //first phase of the workflow
	@SuppressWarnings("unchecked")
	public Response confirmEmailAddress(String email) {
		// echeck if the user exissts
		
		JsonObject jo = new JsonObject(email);
		email = jo.getString("email");
		
		String sql = "SELECT a FROM User a WHERE lower(a.email) =:email";
		Query query = em.createQuery(sql);
		query.setParameter("email", email.trim().toLowerCase());	
	    List<User> ul = query.getResultList();  
	    User user = ul.get(0);
	    
		if(ul.isEmpty()) {
			m.setCode("00");
			m.setDescription("Sorry, User does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
		}
		
		PasswordReset pa = new PasswordReset();
		pa.setConfirmationToken(UUID.randomUUID().toString());
		pa.setEmail(user.getEmail());
		User u=em.find(User.class, user.getId());
		pa.setUserId(u);
		pa.setTokenSentTime(LocalDateTime.now());
		LocalDateTime exp = LocalDateTime.now().plusHours(param.getPassworResetdExpiry());
		pa.setTokenExpiration(exp);
		pa.setIsConfirmed(false);
		em.persist(pa);
			
		mailer.send(Mail.withText(pa.getEmail(), "Click to reset password:",
							server+"/password/reset/confirm?id=" + pa.getConfirmationToken()));
		
		m.setCode("00");
		m.setDescription("Success, click on the link sent to your email to reset your password");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String json = gson.toJson(response);
		return Response.status(200).entity(json).build();
	}
	
	@GET
	@Path("/reset/confirm") 
	@SuppressWarnings("unchecked")
	public Response getPendindPasswordChange(@QueryParam("id") String token) {
		// check if the password token exissts
		String sql = "select c from PasswordReset c where c.confirmationToken = :token";
		Query query = em.createQuery(sql).setParameter("token",token);
		List<PasswordReset> ul = query.getResultList();
		
		if(ul.isEmpty()) {
			m.setCode("00");
			m.setDescription("Sorry, Token does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
		}
		
		PasswordReset pr = ul.get(0);	
		LocalDateTime now =  LocalDateTime.now();
		LocalDateTime then = pr.getTokenExpiration();
		
		if(now.isAfter(then)) {
			m.setCode("00");
			m.setDescription("Sorry, Token has expired");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
		}
		
		if(pr.isIsConfirmed()==true) {
			m.setCode("00");
			m.setDescription("Sorry, Token has been used");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
		}
		
		m.setCode("00");
		m.setDescription("Success, Token exists, proceed with change of password");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		
		HashMap<String,String> map = new HashMap<>();
		map.put("token", pr.getConfirmationToken());
		map.put("resetId",pr.getId().toString());
	    response.setPayload(map);
		String json = gson.toJson(response);
		return Response.status(200).entity(json).build();
	}
	
	@PUT
	@Path("/reset/confirm")
	@Transactional
	public Response confirmPasswordReset(@QueryParam("id") String token,PasswordDTO pass) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PasswordReset pa =em.find(PasswordReset.class, pass.getResetId());

		if(null == pa) {
			m.setCode("00");
			m.setDescription("Sorry, password reset token was not sent to you");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
		}
		if(!pa.getConfirmationToken().equals(pass.getToken())) {
			m.setCode("00");
			m.setDescription("Sorry, token mismatch");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
		}
		User u = pa.getUserId(); 
		String hashpassword= PasswordHash.hash(pass.getPassword());
		u.setPassword(hashpassword);
		em.merge(u);
		
		//update the values of the password reset table to reflect conclusion of the workflow
		pa.setDateReseted(LocalDateTime.now());
		pa.setIsConfirmed(true);
		em.merge(pa);

		m.setCode("00");
		m.setDescription("Password successfully changed");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String json = gson.toJson(response);	
		return Response.status(200).entity(json).build();
	}

}
