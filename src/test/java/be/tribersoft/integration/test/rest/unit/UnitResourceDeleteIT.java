package be.tribersoft.integration.test.rest.unit;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Optional;

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
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;
import be.tribersoft.util.builder.UnitBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class UnitResourceDeleteIT {

	private static final String UNIT_NOT_FOUND_EXCEPTION = "Unit not found";
	private static final String API_ADMIN_UNIT_UUID = "/api/admin/unit/{uuid}";
	private static final String SYMBOL = "symbol";
	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String NAME = "name";

	@Inject
	private UnitJpaRepository unitJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private String uuid;
	private Long version;

	@Before
	public void setUp() {
		RestAssured.port = port;
		UnitEntity unitEntity = UnitBuilder.aUnit().withName(NAME).withSymbol(Optional.of(SYMBOL)).buildPersistent(unitJpaRepository);
		uuid = unitEntity.getId();
		version = unitEntity.getVersion();
	}

	@Test
	public void deletesUnit() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
				contentType(ContentType.JSON).
				body(new UnitDeleteJsonImpl()).
		when(). 
				delete(API_ADMIN_UNIT_UUID). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on
		assertThat(unitJpaRepository.findAllByOrderByCreationDateDesc().isEmpty()).isTrue();
	}

	@Test
	public void notFoundWhenDeviceDoesntExist() {
		// @formatter:off
		given().
			pathParam("uuid", NON_EXISTING_UUID).
			contentType(ContentType.JSON).
			body(new UnitDeleteJsonImpl()).
		when(). 
			delete(API_ADMIN_UNIT_UUID). 
		then(). 
			statusCode(HttpStatus.NOT_FOUND.value()).
			body("message", equalTo(UNIT_NOT_FOUND_EXCEPTION));
		// @formatter:on
		assertThat(unitJpaRepository.findAllByOrderByCreationDateDesc().isEmpty()).isFalse();
	}

	private class UnitDeleteJsonImpl {
		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}
}
