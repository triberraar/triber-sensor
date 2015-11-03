package be.tribersoft.integration.test.rest.unit;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

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
import be.tribersoft.sensor.domain.api.unit.UnitMessage;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitFactory;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class UnitResourceDeleteIT {

	private static final String SYMBOL = "symbol";
	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String NAME = "name";

	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private UnitFactory unitFactory;

	@Value("${local.server.port}")
	private int port;
	private String uuid;
	private Long version;

	@Before
	public void setUp() {
		RestAssured.port = port;
		unitJpaRepository.save(unitFactory.create(new UnitMessageImpl(SYMBOL)));
		UnitEntity unitEntity = unitJpaRepository.findAllByOrderByCreationDateDesc().get(0);
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
				delete("/api/admin/unit/{uuid}"). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on
		assertThat(unitJpaRepository.findAllByOrderByCreationDateDesc().isEmpty()).isTrue();
	}

	private class UnitMessageImpl implements UnitMessage {

		private String symbol;

		public UnitMessageImpl(String symbol) {
			this.symbol = symbol;
		}

		@Override
		public String getName() {
			return NAME;
		}

		@Override
		public Optional<String> getSymbol() {
			return Optional.ofNullable(symbol);
		}
	}

	private class UnitDeleteJsonImpl {
		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}
}
