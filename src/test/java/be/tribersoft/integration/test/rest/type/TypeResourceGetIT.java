package be.tribersoft.integration.test.rest.type;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import be.tribersoft.TriberSensorApplication;
import be.tribersoft.sensor.domain.api.type.TypeCreate;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeFactory;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TypeResourceGetIT {

	private static final String NON_EXISTING_UUID = "non existing uuid";

	private static final String NAME = "name";

	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private TypeFactory typeFactory;

	@Value("${local.server.port}")
	private int port;
	private String uuid;

	@Before
	public void setUp() {
		RestAssured.port = port;
		typeJpaRepository.save(typeFactory.create(new TypeCreateImpl()));
		TypeEntity typeEntity = typeJpaRepository.findAllByOrderByCreationDateDesc().get(0);
		uuid = typeEntity.getId();
	}

	@Test
	public void getType() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
		when(). 
				get("/type/{uuid}"). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(4)).
				body("name", is(NAME)).
				body("id", is(uuid)).
				body("version", is(0)).
				body("_links.self.href", is("http://localhost:" + port+"/type/" + uuid)).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	@Test
	public void failsWhenNotFound() {
		// @formatter:off
		given().
				pathParam("uuid", NON_EXISTING_UUID).
		when(). 
				get("/type/{uuid}"). 
		then(). 
				statusCode(HttpStatus.NOT_FOUND.value());
		// @formatter:on
	}

	private class TypeCreateImpl implements TypeCreate {

		@Override
		public String getName() {
			return NAME;
		}
	}
}
