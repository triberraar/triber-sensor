package be.tribersoft.integration.test.rest.unit;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

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
import be.tribersoft.common.testData.UnitBuilder;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class UnitResourceGetIT {

	private static final String UNIT_NOT_FOUND_EXCEPTION = "Unit not found";
	private static final String URL = "/api/{apiVersion}/admin/unit/{uuid}";
	private static final String SYMBOL = "symbol";
	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String NAME = "name";

	@Inject
	private UnitJpaRepository unitJpaRepository;

	@Value("${local.server.port}")
	private int port;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void getUnit() {
		UnitEntity unitEntity = UnitBuilder.aUnit().withName(NAME).withSymbol(Optional.of(SYMBOL)).buildPersistent(unitJpaRepository);
		String uuid = unitEntity.getId();
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(5)).
				body("name", is(NAME)).
				body("symbol", is(SYMBOL)).
				body("id", is(uuid)).
				body("version", is(0)).
				body("_links.self.href", is("http://localhost:" + port+"/api/"+apiVersion+"/admin/unit/" + uuid)).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	@Test
	public void notFoundWhenUnitDoesntExist() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", NON_EXISTING_UUID).
		when(). 
				get(URL). 
		then(). 
				statusCode(HttpStatus.NOT_FOUND.value()).
				body("message", equalTo(UNIT_NOT_FOUND_EXCEPTION));
		// @formatter:on
	}

	@Test
	public void getUnitWithoutSymbol() {
		UnitEntity unitEntity = UnitBuilder.aUnit().withName(NAME).withSymbol(Optional.empty()).buildPersistent(unitJpaRepository);
		String uuid = unitEntity.getId();
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(5)).
				body("name", is(NAME)).
				body("symbol", isEmptyOrNullString()).
				body("id", is(uuid)).
				body("version", is(0)).
				body("_links.self.href", is("http://localhost:" + port+"/api/"+apiVersion+"/admin/unit/" + uuid)).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

}
