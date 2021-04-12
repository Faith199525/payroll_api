package com.payroll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import com.codetouch.payroll.entity.User;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UserResourceTest {

	@Test
	public void testUserResource() {
		
		User u =new User();
		u.setFirstName("test");
		u.setLastName("testrun");
		u.setEmail("test@gmail.com");
		u.setPassword("test");
		u.setConfirmationToken(UUID.randomUUID().toString());
		u.setCountry("Nigeria");
		u.setPhoneNumber("09089786756");
		u.setCity("Lagos");
		u.setState("Lagos");
		u.setSex("male");
		u.setCreatedDate(LocalDate.now());
		u.setTimeCreated(LocalTime.now());
		
		given().contentType("application/json")
		.body(u)
		.when()
		.post("/users/signup")
		.then().statusCode(200)
		.log()
		.all();
	
		
	}
}
