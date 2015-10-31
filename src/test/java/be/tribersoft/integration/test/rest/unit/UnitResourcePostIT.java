package be.tribersoft.integration.test.rest.unit;

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
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class UnitResourcePostIT {

	private static final String URL = "/api/admin/unit";
	private static final String ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";
	private static final String SYMBOL = "symbol";

	@Inject
	private UnitJpaRepository unitJpaRepository;

	@Value("${local.server.port}")
	private int serverPort;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;
	}

	@Test
	public void createsANewUnit() {
		// @formatter:off
		given(). 
				body(new UnitPostJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<UnitEntity> units = unitJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(units.size()).isEqualTo(1);
		UnitEntity unitEntity = units.get(0);
		assertThat(unitEntity.getName()).isEqualTo(NAME);
		assertThat(unitEntity.getSymbol().get()).isEqualTo(SYMBOL);
		assertThat(unitEntity.getId()).isNotNull();
		assertThat(unitEntity.getVersion()).isEqualTo(0L);
	}

	@Test
	public void createsANewUnitWithoutSymbol() {
		// @formatter:off
		given(). 
				body(new UnitPostJsonImplWithoutSymbol()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<UnitEntity> units = unitJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(units.size()).isEqualTo(1);
		UnitEntity unitEntity = units.get(0);
		assertThat(unitEntity.getName()).isEqualTo(NAME);
		assertThat(unitEntity.getSymbol().isPresent()).isFalse();
		assertThat(unitEntity.getId()).isNotNull();
		assertThat(unitEntity.getVersion()).isEqualTo(0L);
	}

	@Test
	public void badRequestWhenUnitIsNotValid() {
		// @formatter:off
		given(). 
				body(new UnitPostJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(ERROR_MESSAGE));
		// @formatter:on
	}

	private class UnitPostJsonImpl {
		@JsonProperty
		public String getName() {
			return NAME;
		}

		@JsonProperty
		public String getSymbol() {
			return SYMBOL;
		}
	}

	private class UnitPostJsonImplWithoutSymbol {
		@JsonProperty
		public String getName() {
			return NAME;
		}

	}

	private class UnitPostJsonImplInvalid {
		@JsonProperty
		public String getName() {
			return null;
		}
	}

}
