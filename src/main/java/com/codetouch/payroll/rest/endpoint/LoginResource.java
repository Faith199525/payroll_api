package com.codetouch.payroll.rest.endpoint;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codetouch.payroll.GsonResponse;
import com.codetouch.payroll.StatusMessage;
import com.codetouch.payroll.dto.LoginDTO;
import com.codetouch.payroll.entity.PayrollParameter;
import com.codetouch.payroll.entity.User;
import com.codetouch.payroll.hash.PasswordHash;
import com.codetouch.payroll.jwt.TokenUtils;
import com.codetouch.payroll.qualifier.SystemParameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/users/access")
public class LoginResource {
	
    @Inject
	EntityManager em;
    
    @Inject
	@SystemParameter
	PayrollParameter param;
    
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
	@POST
	@Transactional
	public Response login( LoginDTO user) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		String sql = "SELECT ua FROM User ua WHERE lower(ua.email) =:email";
		Query query = em.createQuery(sql);
		query.setParameter("email", user.getEmail().trim().toLowerCase());
	   List<User> u= query.getResultList();
	   
	   if (u.isEmpty()) {
			
			m.setCode("00");
			m.setDescription("Pls sign up with us");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
		}
	   
	   User n = u.get(0);
	   boolean pa= PasswordHash.check(user.getPassword(), n.getPassword());
   
	   if(pa==false) {	   
		   m.setCode("00");
			m.setDescription("Login Credentials wrong!!!");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
	   }
	   if (n.isActive()==false) {
			m.setCode("00");
			m.setDescription("Click on link sent to your email to complete registration");
			ml.add(m);
			response.setStatus(200);
			response.setStatusMessages(ml);
			String json = gson.toJson(response);
			return Response.status(200).entity(json).build();
		}
	   
	   String userName= user.getEmail();
	   String owner=String.valueOf(n.getId());
	   
	   Integer maxActive = null == param.getMaxUserActive() ? 900 :  param.getMaxUserActive()*60*60;
	   String token = TokenUtils.generateJWT(userName, owner, maxActive);
	   
	    m.setCode("00");
		m.setDescription("Login successful");
		ml.add(m);
		response.setStatus(200);
		response.setStatusMessages(ml);
		response.setPayload(token);
		String json = gson.toJson(response);
		
	    return Response.status(200).entity(json).build();
	   
        
	}

	
}
