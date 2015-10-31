package be.tribersoft.integration.test.rest.unit;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

import java.time.LocalDateTime;
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

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import be.tribersoft.TriberSensorApplication;
import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.unit.UnitMessage;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitFactory;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class UnitResourceAllIT {

	private static final String NAME_1 = "name 1";
	private static final String SYMBOL_1 = "symbol 1";
	private static final String NAME_2 = "name 2";

	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private UnitFactory unitFactory;

	@Value("${local.server.port}")
	private int port;
	private List<UnitEntity> units;

	@Before
	public void setUp() {
		RestAssured.port = port;
		LocalDateTime now = LocalDateTime.now();
		DateFactory.fixateDate(now);
		unitJpaRepository.save(unitFactory.create(new UnitMessageImpl(NAME_1, SYMBOL_1)));
		DateFactory.fixateDate(now.plusDays(1));
		unitJpaRepository.save(unitFactory.create(new UnitMessageImpl(NAME_2, null)));
		units = unitJpaRepository.findAllByOrderByCreationDateDesc();
	}

	@Test
	public void getsAllUnits() {
		// @formatter:off
		when(). 
				get("/unit"). 
		then(). 
				contentType(ContentType.JSON).
				statusCode(HttpStatus.OK.value()).
				body("_links.self.href", is("http://localhost:" + port + "/unit")).
				body("_embedded.units.size()", is(2)).
				body("_embedded.units[0].size()", is(5)).
				body("_embedded.units[0].name", is(NAME_2)).
				body("_embedded.units[0].symbol", isEmptyOrNullString()).
				body("_embedded.units[0].version", is(0)).
				body("_embedded.units[0].id", is(units.get(0).getId())).
				body("_embedded.units[0]._links.self.href", is("http://localhost:" + port + "/unit/" + units.get(0).getId())).
				body("_embedded.units[1].size()", is(5)).
				body("_embedded.units[1].name", is(NAME_1)).
				body("_embedded.units[1].symbol", is(SYMBOL_1)).
				body("_embedded.units[1].version", is(0)).
				body("_embedded.units[1].id", is(units.get(1).getId())).
				body("_embedded.units[1]._links.self.href", is("http://localhost:" + port + "/unit/" + units.get(1).getId()));
		// @formatter:on
	}

	private class UnitMessageImpl implements UnitMessage {
		private String name;
		private String symbol;

		public UnitMessageImpl(String name, String symbol) {
			this.name = name;
			this.symbol = symbol;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Optional<String> getSymbol() {
			return Optional.ofNullable(symbol);
		}
	}
}
