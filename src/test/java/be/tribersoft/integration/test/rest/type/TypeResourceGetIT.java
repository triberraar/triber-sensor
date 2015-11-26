package be.tribersoft.integration.test.rest.type;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

import javax.inject.Inject;

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
import be.tribersoft.common.testData.TypeBuilder;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class TypeResourceGetIT {

	private static final String TYPE_NOT_FOUND_EXCPETION = "Type not found";
	private static final String URL = "/api/{apiVersion}/admin/type/{uuid}";
	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String NAME = "name";

	@Inject
	private TypeJpaRepository typeJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private String uuid;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = port;

		TypeEntity typeEntity = TypeBuilder.aType().withName(NAME).buildPersistent(typeJpaRepository);
		uuid = typeEntity.getId();
	}

	@Test
	public void getType() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(4)).
				body("name", is(NAME)).
				body("id", is(uuid)).
				body("version", is(0)).
				body("_links.self.href", is("http://localhost:" + port+"/api/"+apiVersion+"/admin/type/" + uuid)).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	@Test
	public void failsWhenNotFound() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", NON_EXISTING_UUID).
		when(). 
				get(URL). 
		then(). 
				statusCode(HttpStatus.NOT_FOUND.value()).
				body("message", equalTo(TYPE_NOT_FOUND_EXCPETION));
		// @formatter:on
	}

}
