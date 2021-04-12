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
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import com.codetouch.payroll.GsonResponse;
import com.codetouch.payroll.StatusMessage;
import com.codetouch.payroll.dto.GradeDTO;
import com.codetouch.payroll.entity.Grade;
import com.codetouch.payroll.entity.Organization;
import com.codetouch.payroll.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author CODETOUCH
 *
 */

@Path("/grades")  
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class GradeResource {
	
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
	public Response viewAllGrades() {
			
		ml.add(m);
		List<Grade> el = em.createQuery(" select c from Grade c where c.deleted='false' ").getResultList();
		
		m.setCode("00");
		m.setDescription("Success");

		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(el);
		String j = gson.toJson(response);

		return Response.status(200).entity(j).build();
	}
	@GET
	@Path("/view/{id}")
	public Response viewGrade(@PathParam("id") Long gradeId) {
		
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);
		Grade el = em.find(Grade.class, gradeId);
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
	public Response registerGrade(GradeDTO gra, @Context SecurityContext ctx) {
			
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
		
		Organization or= em.find(Organization.class, gra.getOrganization());
		if(or==null) {
			m.setCode("00");
			m.setDescription("Organization does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
			}
			
		Grade ga = new Grade();
		ga.setName(gra.getName());
		ga.setOrganization(or);
		ga.setNumber(gra.getNumber());
		ga.setAnnualGrossEarning(gra.getAnnualGrossEarning());
		ga.setAnnualHMO(gra.getAnnualHMO());
		ga.setAnnualPension(gra.getAnnualPension());
		ga.setAnnualTax(gra.getAnnualTax());
		ga.setCreatedDate(LocalDate.now());
		ga.setTimeCreated(LocalTime.now());
		ga.setCreatedBy(us);
		em.persist(ga);
		
		m.setCode("00");	
		m.setDescription("Grade registration Succesful");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String json = gson.toJson(response);
	    return Response.status(200).entity(json).build();
	}
	
	@PUT
	@Transactional
	@Path("/update/{id}")
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response updateGrade(@PathParam("id") Long gradeId, GradeDTO gra,  @Context SecurityContext ctx) {
	
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
		Grade ga = em.find(Grade.class, gradeId);
		ga.setName(gra.getName());
		ga.setNumber(gra.getNumber());
		ga.setAnnualGrossEarning(gra.getAnnualGrossEarning());
		ga.setAnnualHMO(gra.getAnnualHMO());
		ga.setAnnualPension(gra.getAnnualPension());
		ga.setAnnualTax(gra.getAnnualTax());    
		em.merge(ga);
		
		m.setCode("00");
		m.setDescription("Grade successfully updated");
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
	public Response deleteGrade(@PathParam("id") Long gradeId, @Context SecurityContext ctx) {
	
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
		Grade ga = em.find(Grade.class, gradeId);
        if(ga==null) {
        	m.setCode("00");
			m.setDescription("Grade does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}
		ga.setDeleted(true);
		ga.setDeletedBy(us);
		ga.setDateDeleted(LocalDate.now());
		ga.setTimeDeleted(LocalTime.now());    
		em.merge(ga);
		
		m.setCode("00");
		m.setDescription("Grade successfully deleted");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String j = gson.toJson(response);
		return Response.status(200).entity(j).build();
	}
}
