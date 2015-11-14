package be.tribersoft.integration.test.rest.sensor;

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
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorJpaRepository;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;
import be.tribersoft.util.builder.DeviceBuilder;
import be.tribersoft.util.builder.SensorBuilder;
import be.tribersoft.util.builder.TypeBuilder;
import be.tribersoft.util.builder.UnitBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class SensorResourceGetIT {

	private static final String URL = "/api/device/{deviceId}/sensor/{uuid}";
	private static final String SENSOR_NOT_FOUND_EXCEPTION = "Sensor not found";
	private static final String DESCRIPTION = "description";
	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String NAME = "name";

	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private DeviceJpaRepository deviceJpaRepository;
	@Inject
	private SensorJpaRepository sensorJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private String uuid;
	private TypeEntity typeEntity;
	private UnitEntity unitEntity;
	private DeviceEntity deviceEntity;

	@Before
	public void setUp() {
		RestAssured.port = port;

		typeEntity = TypeBuilder.aType().buildPersistent(typeJpaRepository);
		unitEntity = UnitBuilder.aUnit().buildPersistent(unitJpaRepository);
		deviceEntity = DeviceBuilder.aDevice().buildPersistent(deviceJpaRepository);
	}

	@Test
	public void getSensor() {
		SensorEntity sensorEntity = SensorBuilder.aSensor().withName(NAME).withDescription(Optional.of(DESCRIPTION)).withDevice(deviceEntity).withType(typeEntity).withUnit(unitEntity).buildPersistent(sensorJpaRepository);
		uuid = sensorEntity.getId();
		// @formatter:off
		given().
				pathParam("uuid", uuid).
				pathParam("deviceId", deviceEntity.getId()).
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(5)).
				body("name", is(NAME)).
				body("description", is(DESCRIPTION)).
				body("id", is(uuid)).
				body("version", is(0)).
				body("_links.self.href", is("http://localhost:" + port+"/api/device/" + deviceEntity.getId() + "/sensor/" + uuid)).
				body("_links.unit.href", is("http://localhost:" + port+"/api/admin/unit/" + unitEntity.getId())).
				body("_links.type.href", is("http://localhost:" + port+"/api/admin/type/" + typeEntity.getId())).
				body("_links.device.href", is("http://localhost:" + port+"/api/device/" + deviceEntity.getId())).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	@Test
	public void failsWhenNotFound() {
		// @formatter:off
		given().
				pathParam("uuid", NON_EXISTING_UUID).
				pathParam("deviceId", deviceEntity.getId()).
		when(). 
				get(URL). 
		then(). 
				statusCode(HttpStatus.NOT_FOUND.value()).
				body("message", equalTo(SENSOR_NOT_FOUND_EXCEPTION));
		// @formatter:on
	}

	@Test
	public void getUnitWithoutDescription() {
		SensorEntity sensorEntity = SensorBuilder.aSensor().withName(NAME).withDescription(Optional.empty()).withDevice(deviceEntity).withType(typeEntity).withUnit(unitEntity).buildPersistent(sensorJpaRepository);
		uuid = sensorEntity.getId();
		// @formatter:off
		given().
				pathParam("uuid", uuid).
				pathParam("deviceId", deviceEntity.getId()).
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(5)).
				body("name", is(NAME)).
				body("description", isEmptyOrNullString()).
				body("id", is(uuid)).
				body("version", is(0)).
				body("_links.self.href", is("http://localhost:" + port+"/api/device/" + deviceEntity.getId() + "/sensor/" + uuid)).
				body("_links.unit.href", is("http://localhost:" + port+"/api/admin/unit/" + unitEntity.getId())).
				body("_links.type.href", is("http://localhost:" + port+"/api/admin/type/" + typeEntity.getId())).
				body("_links.device.href", is("http://localhost:" + port+"/api/device/" + deviceEntity.getId())).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

}
