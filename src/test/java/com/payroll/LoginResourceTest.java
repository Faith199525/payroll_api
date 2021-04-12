package com.payroll;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import com.codetouch.payroll.dto.LoginDTO;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LoginResourceTest {

	@Test
	public void testLoginEndpoint() {
		
		LoginDTO lo = new LoginDTO();
	    lo.setEmail("aba@gmail.com");
	    lo.setPassword("aba");
		given()
		.contentType("application/json")
		.body(lo)
        .when()
        .post("/users/access")
        .then()
           .statusCode(200)
           .log()
           .all();
		
	}

}
