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
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class TypeResourcePostIT {

	private static final String ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";

	@Inject
	private TypeJpaRepository typeJpaRepository;

	@Value("${local.server.port}")
	private int serverPort;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;
	}

	@Test
	public void createsANewType() {
		// @formatter:off
		given(). 
				body(new TypePostJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				post("/type"). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<TypeEntity> types = typeJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(types.size()).isEqualTo(1);
		TypeEntity typeEntity = types.get(0);
		assertThat(typeEntity.getName()).isEqualTo(NAME);
		assertThat(typeEntity.getId()).isNotNull();
		assertThat(typeEntity.getVersion()).isEqualTo(0L);
	}

	@Test
	public void badRequestWhenTypeIsNotValid() {
		// @formatter:off
		given(). 
				body(new TypePostJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				post("/type"). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(ERROR_MESSAGE));
		// @formatter:on
	}

	private class TypePostJsonImpl {
		@JsonProperty
		public String getName() {
			return NAME;
		}
	}

	private class TypePostJsonImplInvalid {
		@JsonProperty
		public String getName() {
			return null;
		}
	}

}
