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

import com.codetouch.payroll.GsonResponse;
import com.codetouch.payroll.StatusMessage;
import com.codetouch.payroll.dto.PayrollDTO;
import com.codetouch.payroll.entity.Organization;
import com.codetouch.payroll.entity.Payroll;
import com.codetouch.payroll.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/payroll")  
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class PayrollResource {

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
	@SuppressWarnings("unchecked")
	@GET
	public Response viewAllPayroll() {
		
        List<StatusMessage> ml = new ArrayList<>();
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);
		List<Payroll> el = em.createQuery(" select c from  Payroll c ").getResultList();
		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(el);
		String j = gson.toJson(response);

		return Response.status(200).entity(j).build();
	}
	@GET
	@Path("/view/{id}")
	public Response viewPayroll(@PathParam("id")Long payId) {
		
        List<StatusMessage> ml = new ArrayList<>();
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);
		Payroll el= em.find(Payroll.class, payId);
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
	public Response registerPayroll(PayrollDTO pay, @Context SecurityContext ctx) {
		
        List<StatusMessage> ml = new ArrayList<>();
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		m.setDescription("Success");

		Long convert=Long.valueOf(jwt.getSubject());
		User us = em.find(User.class, convert);
		if(us==null) {
			
			m.setDescription("User does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}
		
		Organization org = em.find(Organization.class, pay.getOrganization());
		if (org==null) {
			
			m.setDescription("Sorry, organization does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
		   return Response.status(200).entity(json).build();
		}
		
		Payroll pa = new Payroll();
		pa.setAnnualDeduction(pay.getAnnualDeduction());
		pa.setAnnualGrossAmount(pay.getAnnualGrossAmount());
		pa.setApprovalLevel(pay.getApprovalLevel());
		pa.setCreatedBy(us);
		pa.setCreatedDate(LocalDate.now());
		pa.setCreateTime(LocalTime.now());
		pa.setCycle(pay.getCycle());
		pa.setOrganization(org);
		pa.setPaymentDate(pay.getPaymentDate());
		//pa.setPaymentTime(pay.getPaymentTime());
		
		em.persist(pa);
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String j = gson.toJson(response);

		return Response.status(200).entity(j).build();
	}
	
	@PUT
	@Transactional
	@Path("/update/{id}")
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response updatePayroll(@PathParam("id") Long payId, PayrollDTO pay, @Context SecurityContext ctx) {
		
		List<StatusMessage> ml = new ArrayList<>();
		StatusMessage m = new StatusMessage();
		m.setCode("00");
        
        Payroll pa = em.find(Payroll.class, payId);
        if (pa==null) {
			
			m.setDescription("Sorry, Payroll does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
		   return Response.status(200).entity(json).build();
		}
        pa.setAnnualDeduction(pay.getAnnualDeduction());
		pa.setAnnualGrossAmount(pay.getAnnualGrossAmount());
		pa.setApprovalLevel(pay.getApprovalLevel());
		pa.setCycle(pay.getCycle());
		pa.setPaymentDate(pay.getPaymentDate());
		//pa.setPaymentTime(pay.getPaymentTime());

		em.merge(pa);
		
		m.setDescription("Payroll update successful");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String json = gson.toJson(response);
	   return Response.status(200).entity(json).build();
	}
	@PUT
	@Transactional
	@Path("/remove/{id}")
	@SecurityRequirement(name = "jwt", scopes = {})
	public Response deletePayroll(@PathParam("id") Long payId, @Context SecurityContext ctx) {
		
		List<StatusMessage> ml = new ArrayList<>();
		StatusMessage m = new StatusMessage();
		m.setCode("00");
		
		Long convert=Long.valueOf(jwt.getSubject());
		User us = em.find(User.class, convert);
		if(us==null) {
			
			m.setDescription("User does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String j = gson.toJson(response);
			return Response.status(200).entity(j).build();
		}
		Payroll pa = em.find(Payroll.class, payId);
        if (pa==null) {
			
			m.setDescription("Sorry, Employee does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
		   return Response.status(200).entity(json).build();
		}
        
         pa.setDeleted(true);
         pa.setDeletedBy(us);
         pa.setDeletedDate(LocalDate.now());
         pa.setDeletedTime(LocalTime.now());
        em.merge(pa);
        
		m.setDescription("Payroll successfully removed");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String json = gson.toJson(response);
	   return Response.status(200).entity(json).build();
	}
}
