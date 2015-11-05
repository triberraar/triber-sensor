package be.tribersoft.integration.test.rest.type;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;

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
import be.tribersoft.sensor.domain.api.type.TypeMessage;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeFactory;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class TypeResourcePutIT {

	private static final String TYPE_NOT_FOUND_EXCEPTION = "Type not found";
	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String URL = "/api/admin/type/{uuid}";
	private static final String CONCURRENT_ERROR_MESSAGE = "Somebody else might have changed the resource, please reload";
	private static final String INVALID_ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";
	private static final String UPDATED_NAME = "updated name";

	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private TypeFactory typeFactory;

	@Value("${local.server.port}")
	private int serverPort;
	private String uuid;
	private Long version;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;
		typeJpaRepository.save(typeFactory.create(new TypeMessageImpl()));
		TypeEntity typeEntity = typeJpaRepository.findAllByOrderByCreationDateDesc().get(0);
		uuid = typeEntity.getId();
		version = typeEntity.getVersion();
	}

	@Test
	public void updatesType() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
				body(new TypePutJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<TypeEntity> types = typeJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(types.size()).isEqualTo(1);
		TypeEntity typeEntity = types.get(0);
		assertThat(typeEntity.getName()).isEqualTo(UPDATED_NAME);
		assertThat(typeEntity.getId()).isEqualTo(uuid);
		assertThat(typeEntity.getVersion()).isEqualTo(version + 1);
	}

	@Test
	public void badRequestWhenTypeIsNotValid() {
		// @formatter:off
		given(). 
				pathParam("uuid", uuid).
				body(new TypePutJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(INVALID_ERROR_MESSAGE));
		// @formatter:on
	}

	@Test
	public void badRequestWhenTypeHasConcurrentChanges() {
		// @formatter:off
		given(). 
				pathParam("uuid", uuid).
				body(new TypePutJsonImplConcurrent()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(CONCURRENT_ERROR_MESSAGE));
		// @formatter:on
	}

	@Test
	public void notFoundWhenTypeDoesntExist() {
		// @formatter:off
		given(). 
			pathParam("uuid", NON_EXISTING_UUID).
			body(new TypePutJsonImpl()). 
			contentType(ContentType.JSON).
		when(). 
			put(URL). 
		then(). 
			statusCode(HttpStatus.NOT_FOUND.value()).
			body("message", equalTo(TYPE_NOT_FOUND_EXCEPTION));
		// @formatter:on
	}

	private class TypePutJsonImpl {

		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}

	private class TypePutJsonImplInvalid {
		@JsonProperty
		public String getName() {
			return null;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}

	private class TypePutJsonImplConcurrent {
		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public Long getVersion() {
			return version + 1;
		}
	}

	private class TypeMessageImpl implements TypeMessage {

		@Override
		public String getName() {
			return NAME;
		}

	}

}
