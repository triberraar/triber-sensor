package be.tribersoft.integration.test.rest.sensor;

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
public class SensorDeviceResourceDeleteIT {

	private static final String URL = "/api/device/{deviceId}/sensor/{uuid}";
	private static final String SENSOR_NOT_FOUND_EXCEPTION = "Sensor not found";
	private static final String DESCRIPTION = "description";
	private static final String NAME = "name";
	private static final String NON_EXISTING_UUID = "non existing uuid";

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
	private Long version;
	private DeviceEntity deviceEntity;

	@Before
	public void setUp() {
		RestAssured.port = port;

		TypeEntity typeEntity = TypeBuilder.aType().buildPersistent(typeJpaRepository);
		UnitEntity unitEntity = UnitBuilder.aUnit().buildPersistent(unitJpaRepository);
		deviceEntity = DeviceBuilder.aDevice().buildPersistent(deviceJpaRepository);
		SensorEntity sensorEntity = SensorBuilder.aSensor().withName(NAME).withDescription(Optional.of(DESCRIPTION)).withDevice(deviceEntity).withType(typeEntity).withUnit(unitEntity).buildPersistent(sensorJpaRepository);
		uuid = sensorEntity.getId();
		version = sensorEntity.getVersion();
	}

	@Test
	public void deletesSensor() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
				pathParam("deviceId", deviceEntity.getId()).
				body(new SensorDeleteJsonImpl()).
				contentType(ContentType.JSON).
		when(). 
				delete(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on
		assertThat(sensorJpaRepository.findAllByOrderByCreationDateDesc().isEmpty()).isTrue();
	}

	@Test
	public void notFoundWhenSensorDoesntExist() {
		// @formatter:off
		given().
				pathParam("uuid", NON_EXISTING_UUID).
				pathParam("deviceId", deviceEntity.getId()).
				body(new SensorDeleteJsonImpl()).
				contentType(ContentType.JSON).
		when(). 
				delete(URL). 
		then(). 
				statusCode(HttpStatus.NOT_FOUND.value()).
				body("message", equalTo(SENSOR_NOT_FOUND_EXCEPTION));
		
		// @formatter:on
		assertThat(sensorJpaRepository.findAllByOrderByCreationDateDesc().isEmpty()).isFalse();
	}

	private class SensorDeleteJsonImpl {
		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}
}
