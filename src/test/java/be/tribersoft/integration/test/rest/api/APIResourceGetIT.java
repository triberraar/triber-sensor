package be.tribersoft.integration.test.rest.api;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
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

	private static final String WRONG_VERSION = "125.0.1";
	private static final String INCORRECT_VERSION_MESSAGE = "You tried to use version '" + WRONG_VERSION + "' while we provide version 'CURRENT_VERSION'";
	private static final String URL_WITH_VERSION = "/api/{apiVersion}";
	private static final String URL = "/api";
	private static final String NOT_SEMVER = "not semver";
	private static final String NOT_SEMVER_EXCEPTION = "This version is not a semver";

	@Value("${local.server.port}")
	private int port;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void getApiWithVersion() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
		when(). 
				get(URL_WITH_VERSION). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(2)).
				body("version", is(apiVersion)).
				body("_links.size()", is(4)).
				body("_links.self.href", is("http://localhost:" + port+"/api/" + apiVersion)).
				body("_links.types.href", is("http://localhost:" + port+"/api/"+apiVersion+"/admin/type" )).
				body("_links.units.href", is("http://localhost:" + port+"/api/"+apiVersion+"/admin/unit")).
				body("_links.devices.href", is("http://localhost:" + port+"/api/"+apiVersion+"/device")).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	@Test
	public void getApi() {
		// @formatter:off
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(2)).
				body("version", is(apiVersion)).
				body("_links.size()", is(4)).
				body("_links.self.href", is("http://localhost:" + port+"/api/" + apiVersion)).
				body("_links.types.href", is("http://localhost:" + port+"/api/"+apiVersion+"/admin/type" )).
				body("_links.units.href", is("http://localhost:" + port+"/api/"+apiVersion+"/admin/unit")).
				body("_links.devices.href", is("http://localhost:" + port+"/api/"+apiVersion+"/device")).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	@Test
	public void failsWhenWrongVersion() {
		// @formatter:off
		given().
				pathParam("apiVersion", WRONG_VERSION).
		when(). 
				get(URL_WITH_VERSION). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(INCORRECT_VERSION_MESSAGE.replace("CURRENT_VERSION", apiVersion)));
		// @formatter:on
	}

	@Test
	public void failsWhenNotSemver() {
		// @formatter:off
		given().
		pathParam("apiVersion", NOT_SEMVER).
		when(). 
		get(URL_WITH_VERSION). 
		then(). 
		statusCode(HttpStatus.BAD_REQUEST.value()).
		body("message", equalTo(NOT_SEMVER_EXCEPTION));
		// @formatter:on
	}

}
