package be.tribersoft.integration.test.rest.sensor;

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
public class SensorDeviceResourcePostIT {

	private static final String URL = "/api/device/{deviceId}/sensor";
	private static final String ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String UNIT_NAME = "unit name";
	private static final String TYPE_NAME = "type name";
	private static final String DEVICE_NAME = "device name";

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
	private String typeId;
	private String unitId;
	private String deviceId;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;

		typeJpaRepository.save(new TypeEntity(TYPE_NAME));
		typeId = typeJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();
		unitJpaRepository.save(new UnitEntity(UNIT_NAME));
		unitId = unitJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();
		deviceJpaRepository.save(new DeviceEntity(DEVICE_NAME));
		deviceId = deviceJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();
	}

	@Test
	public void createsANewSensor() {
		// @formatter:off
		given(). 
				pathParam("deviceId", deviceId).
				body(new SensorPostJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<SensorEntity> sensors = sensorJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(sensors.size()).isEqualTo(1);
		SensorEntity sensorEntity = sensors.get(0);
		assertThat(sensorEntity.getName()).isEqualTo(NAME);
		assertThat(sensorEntity.getDescription().get()).isEqualTo(DESCRIPTION);
		assertThat(sensorEntity.getType().getId()).isEqualTo(typeId);
		assertThat(sensorEntity.getUnit().getId()).isEqualTo(unitId);
		assertThat(sensorEntity.getId()).isNotNull();
		assertThat(sensorEntity.getVersion()).isEqualTo(0L);
	}

	@Test
	public void createsANewSensorWithoutDescription() {
		// @formatter:off
		given(). 
				pathParam("deviceId", deviceId).
				body(new SensorPostJsonImplWithoutDescription()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<SensorEntity> sensors = sensorJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(sensors.size()).isEqualTo(1);
		SensorEntity sensorEntity = sensors.get(0);
		assertThat(sensorEntity.getName()).isEqualTo(NAME);
		assertThat(sensorEntity.getDescription().isPresent()).isFalse();
		assertThat(sensorEntity.getType().getId()).isEqualTo(typeId);
		assertThat(sensorEntity.getUnit().getId()).isEqualTo(unitId);
		assertThat(sensorEntity.getId()).isNotNull();
		assertThat(sensorEntity.getVersion()).isEqualTo(0L);
	}

	@Test
	public void badRequestWhenSensorIsNotValid() {
		// @formatter:off
		given(). 
				pathParam("deviceId", deviceId).
				body(new SensorPostJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(ERROR_MESSAGE));
		// @formatter:on
	}

	private class SensorPostJsonImpl {
		@JsonProperty
		public String getName() {
			return NAME;
		}

		@JsonProperty
		public String getDescription() {
			return DESCRIPTION;
		}

		@JsonProperty
		public String getTypeId() {
			return typeId;
		}

		@JsonProperty
		public String getUnitId() {
			return unitId;
		}

	}

	private class SensorPostJsonImplWithoutDescription {
		@JsonProperty
		public String getName() {
			return NAME;
		}

		@JsonProperty
		public String getTypeId() {
			return typeId;
		}

		@JsonProperty
		public String getUnitId() {
			return unitId;
		}
	}

	private class SensorPostJsonImplInvalid {
		@JsonProperty
		public String getName() {
			return null;
		}

		@JsonProperty
		public String getTypeId() {
			return typeId;
		}

		@JsonProperty
		public String getUnitId() {
			return unitId;
		}

	}

}
