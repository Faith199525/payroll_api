package com.codetouch.payroll.qualifier;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.codetouch.payroll.entity.PayrollParameter;


@ApplicationScoped
public class PayrollResource {
	

	@Inject
	EntityManager em;
	
	
	@Produces
	@SystemParameter
	public PayrollParameter intparameter() {
		PayrollParameter param = em.find(PayrollParameter.class,PayrollParameter.SYSTEM);
		if(null ==param ) {
			// shutdown the app with an error message
			//quarkus startup and exit
		}
		return param;
	}
	
}
