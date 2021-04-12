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
import com.codetouch.payroll.dto.EmployeeDTO;
import com.codetouch.payroll.entity.Employee;
import com.codetouch.payroll.entity.Grade;
import com.codetouch.payroll.entity.Organization;
import com.codetouch.payroll.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author CODETOUCH
 *
 */
@Path("/employees")  
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class EmployeeResource {

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
	public Response viewAllEmployees() {

		ml.add(m);
		List<Employee> el = em.createQuery(" select c from  Employee c where c.deleted='false' ").getResultList();
		
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
	public Response viewEmployee(@PathParam("id") Long empId) {
		
		m.setCode("00");
		m.setDescription("Success");

		ml.add(m);
		Employee el= em.find(Employee.class, empId);
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
	public Response registerEmployee(EmployeeDTO emp, @Context SecurityContext ctx) {
		
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
		
		Organization org = em.find(Organization.class, emp.getOrganization());
		if (org==null) {
			m.setCode("00");
			m.setDescription("Sorry, organization does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
		    return Response.status(200).entity(json).build();
		}
		
		Grade gra = em.find(Grade.class, emp.getGrade());
        if (gra==null) {
        	m.setCode("00");
			m.setDescription("Sorry, Grade does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
		    return Response.status(200).entity(json).build();
		}
			
		Employee emy = new Employee();
		emy.setFirstName(emp.getFirstName());
		emy.setLastName(emp.getLastName());
		emy.setAccountName(emp.getAccountName());
		emy.setAccountNumber(emp.getAccountNumber());
		emy.setAnnualHMO(emp.getAnnualHMO());
		emy.setAnnualLoan(emp.getAnnualLoan());
		emy.setAnnualPension(emp.getAnnualPension());
		emy.setAnnualTax(emp.getAnnualTax());
		emy.setBank(emp.getBank());
		emy.setCreatedBy(us);
	    emy.setCreatedDate(LocalDate.now());
		emy.setCreateTime(LocalTime.now());
		emy.setEmail(emp.getEmail());
		emy.setGrade(gra);
		emy.setOrganization(org);
		emy.setPhoneNumber(emp.getPhoneNumber());
		emy.setMiddleName(emp.getMiddleName());	
		em.persist(emy);
		
		m.setCode("00");	
		m.setDescription("Employee registration successful");
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
	public Response updateEmployee(@PathParam("id") Long empId, EmployeeDTO emp, @Context SecurityContext ctx) {
		
		Grade gra = em.find(Grade.class, emp.getGrade());
        if (gra==null) {
        	m.setCode("00");
			m.setDescription("Sorry, Grade does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
		    return Response.status(200).entity(json).build();
		}
        
        Employee emy = em.find(Employee.class, empId);
        if (emy==null) {
        	m.setCode("00");
			m.setDescription("Sorry, Employee does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
		    return Response.status(200).entity(json).build();
		}
        emy.setFirstName(emp.getFirstName());
	    emy.setLastName(emp.getLastName());
	    emy.setAccountName(emp.getAccountName());
	    emy.setAccountNumber(emp.getAccountNumber());
	    emy.setAnnualHMO(emp.getAnnualHMO());
	    emy.setAnnualLoan(emp.getAnnualLoan());
	    emy.setAnnualPension(emp.getAnnualPension());
	    emy.setAnnualTax(emp.getAnnualTax());
	    emy.setBank(emp.getBank());
	    emy.setEmail(emp.getEmail());
	    emy.setGrade(gra);
	    emy.setPhoneNumber(emp.getPhoneNumber());
	    emy.setMiddleName(emp.getMiddleName());
		em.merge(emy);
		
		m.setCode("00");
		m.setDescription("Employee update successful");
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
	public Response deleteEmployee(@PathParam("id") Long empId, @Context SecurityContext ctx) {
		
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
		Employee emy = em.find(Employee.class, empId);
        if (emy==null) {
        	m.setCode("00");
			m.setDescription("Sorry, Employee does not exist");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
		    return Response.status(200).entity(json).build();
		}
        
        emy.setDeleted(true);
        emy.setDeletedBy(us);
        emy.setDeletedDate(LocalDate.now());
        emy.setDeletedTime(LocalTime.now());
        em.merge(emy);
        
    	m.setCode("00");
		m.setDescription("Employee successfully removed");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		String json = gson.toJson(response);
	    return Response.status(200).entity(json).build();
	}
}
