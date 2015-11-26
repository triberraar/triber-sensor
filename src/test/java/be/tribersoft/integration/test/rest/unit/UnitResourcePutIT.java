package be.tribersoft.integration.test.rest.unit;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;
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
import be.tribersoft.common.testData.UnitBuilder;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class UnitResourcePutIT {

	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String UNIT_NOT_FOUND_EXCEPTION = "Unit not found";
	private static final String URL = "/api/{apiVersion}/admin/unit/{uuid}";
	private static final String CONCURRENT_ERROR_MESSAGE = "Somebody else might have changed the resource, please reload";
	private static final String INVALID_ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";
	private static final String SYMBOL = "symbol";
	private static final String UPDATED_NAME = "updated name";
	private static final String UPDATED_SYMBOL = "updated symbol";

	@Inject
	private UnitJpaRepository unitJpaRepository;

	@Value("${local.server.port}")
	private int serverPort;
	private String uuid;
	private Long version;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;
		UnitEntity unitEntity = UnitBuilder.aUnit().withName(NAME).withSymbol(Optional.of(SYMBOL)).buildPersistent(unitJpaRepository);
		uuid = unitEntity.getId();
		version = unitEntity.getVersion();
	}

	@Test
	public void updatesUnit() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				body(new UnitPutJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<UnitEntity> units = unitJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(units.size()).isEqualTo(1);
		UnitEntity unitEntity = units.get(0);
		assertThat(unitEntity.getName()).isEqualTo(UPDATED_NAME);
		assertThat(unitEntity.getId()).isEqualTo(uuid);
		assertThat(unitEntity.getVersion()).isEqualTo(version + 1);
		assertThat(unitEntity.getSymbol().get()).isEqualTo(UPDATED_SYMBOL);
	}

	@Test
	public void updatesUnitWithoutSymbol() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				body(new UnitPutJsonImplWithoutSymbol()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<UnitEntity> units = unitJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(units.size()).isEqualTo(1);
		UnitEntity unitEntity = units.get(0);
		assertThat(unitEntity.getName()).isEqualTo(UPDATED_NAME);
		assertThat(unitEntity.getId()).isEqualTo(uuid);
		assertThat(unitEntity.getVersion()).isEqualTo(version + 1);
		assertThat(unitEntity.getSymbol().isPresent()).isFalse();
	}

	@Test
	public void badRequestWhenUnitIsNotValid() {
		// @formatter:off
		given(). 
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				body(new UnitPutJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(INVALID_ERROR_MESSAGE));
		// @formatter:on
	}

	@Test
	public void notFoundWhenUnitDoesntExist() {
		// @formatter:off
		given(). 
			pathParam("apiVersion", apiVersion).
			pathParam("uuid", NON_EXISTING_UUID).
			body(new UnitPutJsonImpl()). 
			contentType(ContentType.JSON).
		when(). 
			put(URL). 
		then(). 
			statusCode(HttpStatus.NOT_FOUND.value()).
			body("message", equalTo(UNIT_NOT_FOUND_EXCEPTION));
		// @formatter:on
	}

	@Test
	public void conflictWhenUnitHasConcurrentChanges() {
		// @formatter:off
		given(). 
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				body(new UnitPutJsonImplConcurrent()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.CONFLICT.value()).
				body("message", equalTo(CONCURRENT_ERROR_MESSAGE));
		// @formatter:on
	}

	private class UnitPutJsonImpl {

		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public String getSymbol() {
			return UPDATED_SYMBOL;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}

	private class UnitPutJsonImplInvalid {
		@JsonProperty
		public String getName() {
			return null;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}

	private class UnitPutJsonImplConcurrent {
		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public Long getVersion() {
			return version + 1;
		}
	}

	private class UnitPutJsonImplWithoutSymbol {
		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}

}
