package be.tribersoft.integration.test.rest.sensor;

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
import be.tribersoft.common.test_data.DeviceBuilder;
import be.tribersoft.common.test_data.SensorBuilder;
import be.tribersoft.common.test_data.TypeBuilder;
import be.tribersoft.common.test_data.UnitBuilder;
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorJpaRepository;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class SensorResourcePutIT {

	private static final String SENSOR_NOT_FOUND_EXCEPTION = "Sensor not found";
	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String URL = "/api/{apiVersion}/device/{deviceId}/sensor/{uuid}";
	private static final String CONCURRENT_ERROR_MESSAGE = "Somebody else might have changed the resource, please reload";
	private static final String INVALID_ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String UPDATED_NAME = "updated name";
	private static final String UPDATED_DESCRIPTION = "updated description";

	@Inject
	private SensorJpaRepository sensorJpaRepository;
	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private DeviceJpaRepository deviceJpaRepository;

	@Value("${local.server.port}")
	private int serverPort;
	private String uuid;
	private Long version;

	private TypeEntity typeEntity;
	private UnitEntity unitEntity;
	private DeviceEntity deviceEntity;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;

		typeEntity = TypeBuilder.aType().buildPersistent(typeJpaRepository);
		unitEntity = UnitBuilder.aUnit().buildPersistent(unitJpaRepository);
		deviceEntity = DeviceBuilder.aDevice().buildPersistent(deviceJpaRepository);
		SensorEntity sensorEntity = SensorBuilder.aSensor().withName(NAME).withDescription(Optional.of(DESCRIPTION)).withDevice(deviceEntity).withUnit(unitEntity).withType(typeEntity).buildPersistent(sensorJpaRepository);
		uuid = sensorEntity.getId();
		version = sensorEntity.getVersion();
	}

	@Test
	public void updatesSensor() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				pathParam("deviceId", deviceEntity.getId()).
				body(new SensorPutJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<SensorEntity> sensors = sensorJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(sensors.size()).isEqualTo(1);
		SensorEntity sensorEntity = sensors.get(0);
		assertThat(sensorEntity.getName()).isEqualTo(UPDATED_NAME);
		assertThat(sensorEntity.getDescription().get()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(sensorEntity.getType().getId()).isEqualTo(typeEntity.getId());
		assertThat(sensorEntity.getUnit().getId()).isEqualTo(unitEntity.getId());
		assertThat(sensorEntity.getDevice().getId()).isEqualTo(deviceEntity.getId());
		assertThat(sensorEntity.getId()).isEqualTo(uuid);
		assertThat(sensorEntity.getVersion()).isEqualTo(version + 1);
	}

	@Test
	public void updatesSensorWithoutDescription() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				pathParam("deviceId", deviceEntity.getId()).
				body(new SensorPutJsonImplWithoutDescription()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<SensorEntity> sensors = sensorJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(sensors.size()).isEqualTo(1);
		SensorEntity sensorEntity = sensors.get(0);
		assertThat(sensorEntity.getName()).isEqualTo(UPDATED_NAME);
		assertThat(sensorEntity.getDescription().isPresent()).isFalse();
		assertThat(sensorEntity.getType().getId()).isEqualTo(typeEntity.getId());
		assertThat(sensorEntity.getUnit().getId()).isEqualTo(unitEntity.getId());
		assertThat(sensorEntity.getDevice().getId()).isEqualTo(deviceEntity.getId());
		assertThat(sensorEntity.getId()).isEqualTo(uuid);
		assertThat(sensorEntity.getVersion()).isEqualTo(version + 1);
	}

	@Test
	public void badRequestWhenSensorIsNotValid() {
		// @formatter:off
		given(). 
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				pathParam("deviceId", deviceEntity.getId()).
				body(new SensorPutJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(INVALID_ERROR_MESSAGE));
		// @formatter:on
	}

	@Test
	public void conflictWhenSensorHasConcurrentChanges() {
		// @formatter:off
		given(). 
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				pathParam("deviceId", deviceEntity.getId()).
				body(new SensorPutJsonImplConcurrent()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.CONFLICT.value()).
				body("message", equalTo(CONCURRENT_ERROR_MESSAGE));
		// @formatter:on
	}

	@Test
	public void notFoundWhenSensorDoesntExist() {
		// @formatter:off
		given().
			pathParam("apiVersion", apiVersion).
			pathParam("uuid", NON_EXISTING_UUID).
			pathParam("deviceId", deviceEntity.getId()).
			body(new SensorPutJsonImpl()). 
			contentType(ContentType.JSON).
		when(). 
			put(URL). 
		then(). 
			statusCode(HttpStatus.NOT_FOUND.value()).
			body("message", equalTo(SENSOR_NOT_FOUND_EXCEPTION));
		// @formatter:on
	}

	private class SensorPutJsonImpl {

		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public String getDescription() {
			return UPDATED_DESCRIPTION;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}

		@JsonProperty
		public String getTypeId() {
			return deviceEntity.getId();
		}

		@JsonProperty
		public String getUnitId() {
			return unitEntity.getId();
		}
	}

	private class SensorPutJsonImplInvalid {
		@JsonProperty
		public String getName() {
			return null;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}

		@JsonProperty
		public String getTypeId() {
			return deviceEntity.getId();
		}

		@JsonProperty
		public String getUnitId() {
			return unitEntity.getId();
		}
	}

	private class SensorPutJsonImplConcurrent {
		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public Long getVersion() {
			return version + 1;
		}

		@JsonProperty
		public String getTypeId() {
			return deviceEntity.getId();
		}

		@JsonProperty
		public String getUnitId() {
			return unitEntity.getId();
		}
	}

	private class SensorPutJsonImplWithoutDescription {
		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}

		@JsonProperty
		public String getTypeId() {
			return deviceEntity.getId();
		}

		@JsonProperty
		public String getUnitId() {
			return unitEntity.getId();
		}
	}

}
