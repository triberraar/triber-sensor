package be.tribersoft.integration.test.rest.api;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import be.tribersoft.TriberSensorApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class APIResourceGetIT {

	private static final String INCORRECT_VERSION_MESSAGE = "You tried to use version 'wrong' while we provide version '1'";
	private static final String URL = "/api/{apiVersion}";
	private static final String WRONG_VERSION = "wrong";

	@Value("${local.server.port}")
	private int port;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void getApi() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(2)).
				body("version", is(apiVersion)).
				body("_links.size()", is(4)).
				body("_links.self.href", is("http://localhost:" + port+"/api/" + apiVersion)).
				body("_links.types.href", is("http://localhost:" + port+"/api/admin/type" )).
				body("_links.units.href", is("http://localhost:" + port+"/api/admin/unit")).
				body("_links.devices.href", is("http://localhost:" + port+"/api/device")).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	@Test
	public void failsWhenWrongVersion() {
		// @formatter:off
		given().
				pathParam("apiVersion", WRONG_VERSION).
		when(). 
				get(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(INCORRECT_VERSION_MESSAGE));
		// @formatter:on
	}

}
