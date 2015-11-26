package be.tribersoft.integration.test.rest.type;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

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

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TypeResourceDeleteIT {

	private static final String TYPE_NOT_FOUND_EXCEPTION = "Type not found";
	private static final String URL = "/api/{apiVersion}/admin/type/{uuid}";
	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String NAME = "name";

	@Inject
	private TypeJpaRepository typeJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private String uuid;
	private Long version;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = port;
		TypeEntity typeEntity = TypeBuilder.aType().withName(NAME).buildPersistent(typeJpaRepository);
		uuid = typeEntity.getId();
		version = typeEntity.getVersion();
	}

	@Test
	public void deletesType() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				body(new TypeDeleteJsonImpl()).
				contentType(ContentType.JSON).
		when(). 
				delete(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		assertThat(typeJpaRepository.findAllByOrderByCreationDateDesc().isEmpty()).isTrue();
	}

	@Test
	public void failsWhenTypeNotFound() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", NON_EXISTING_UUID).
				body(new TypeDeleteJsonImpl()).
				contentType(ContentType.JSON).
		when(). 
				delete(URL). 
		then(). 
				statusCode(HttpStatus.NOT_FOUND.value()).
				body("message", equalTo(TYPE_NOT_FOUND_EXCEPTION));
		// @formatter:on

		assertThat(typeJpaRepository.findAllByOrderByCreationDateDesc().isEmpty()).isFalse();
	}

	private class TypeDeleteJsonImpl {
		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}
}
