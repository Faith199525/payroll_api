package com.codetouch.payroll.rest.endpoint;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.resource.spi.work.SecurityContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.codetouch.payroll.GsonResponse;
import com.codetouch.payroll.StatusMessage;
import com.codetouch.payroll.dto.OrganizationDTO;
import com.codetouch.payroll.entity.Organization;
import com.codetouch.payroll.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author CODETOUCH
 *
 */

@Path("/organisations")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class OrganizationResource {
	
	@Inject
	EntityManager em;
    
	@Inject
    JsonWebToken jwt;
	
    private Gson gson;
	private GsonResponse response = new GsonResponse();

	@PostConstruct
	private void postConstruct() {
		GsonBuilder builder = new GsonBuilder();

		gson = builder.create();

	}
	
	List<StatusMessage> ml = new ArrayList<>();
	StatusMessage m = new StatusMessage();
	
	@SuppressWarnings("unchecked")
	@GET
	public Response viewAllOrganizations() {
		
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);
		List<Organization> el = em.createQuery(" select c from Organization c where c.deleted='false'  ").getResultList();
		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(el);
		String j = gson.toJson(response);

		return Response.status(200).entity(j).build();
	}
	@GET
	@Path("/view/{id}")
	public Response viewOrganization(@PathParam("id") Long orgId) {
		
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);
	    Organization el = em.find(Organization.class, orgId);
		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(el);
		String j = gson.toJson(response);

		return Response.status(200).entity(j).build();
	}
	@POST
	@Transactional
	@Path("/register")  
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response register(OrganizationDTO org, @Context SecurityContext ctx) {
		
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
		Organization or = new Organization();
		or.setShortName(org.getShortName());
		or.setFullName(org.getFullName());
		or.setCity(org.getCity());
		or.setRegistrationNumber(org.getRegistrationNumber());
		or.setState(org.getState());
		or.setVatNumber(org.getVatNumber());
		or.setAddress(org.getAddress());
		or.setDateOfIncoporation(org.getDateOfIncoporation());
		or.setCreatedDate(LocalDate.now());
		or.setCreatedBy(us);
		or.setTimeCreated(LocalTime.now());
		em.persist(or);
		
		m.setCode("00");
		m.setDescription("Organization registration successsful");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String json = gson.toJson(response);	
	   return Response.status(200).entity(json).build();
	}
	
	@POST
	@Path("/register/csv") 
	@Transactional
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)// multipart upload	  
	public Response csvRegistration(@PathParam("file") String files, @MultipartForm Organization orgs) {
		
	   return null;
	}
	
	@PUT
	@Transactional
	@Path("/update/{id}")
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response updateOrganization(@PathParam("id") Long orgId, @Context SecurityContext ctx, OrganizationDTO org) {
		
		Organization or = em.find(Organization.class, orgId);
		if (or==null) {
			
			m.setCode("00");
			m.setDescription("Sorry, organization does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
		   return Response.status(200).entity(json).build();
		}	
		or.setShortName(org.getShortName());
		or.setFullName(org.getFullName());
		or.setCity(org.getCity());
		or.setRegistrationNumber(org.getRegistrationNumber());
		or.setState(org.getState());
		or.setVatNumber(org.getVatNumber());
		or.setAddress(org.getAddress());
		or.setDateOfIncoporation(org.getDateOfIncoporation());
		em.merge(or);
		
		m.setCode("00");
		m.setDescription("Organization update successsful");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String json = gson.toJson(response);
	   return Response.status(200).entity(json).build();
	}
	
	@PUT
	@Transactional
	@Path("/delete/{id}")
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response deleteOrganization(@PathParam("id") Long orgId, @Context SecurityContext ctx ) {
	
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
		Organization org = em.find(Organization.class, orgId);
		if(org==null) {
			m.setCode("00");
			m.setDescription("Organization does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}
		org.setDeleted(true);
		org.setDeletedBy(us);
		org.setDateDeleted(LocalDate.now());
		org.setTimeDeleted(LocalTime.now());
		em.merge(org);
		
		m.setCode("00");
		m.setDescription("Organization successfully deleted");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String j = gson.toJson(response);
		return Response.status(200).entity(j).build();
	}
}
