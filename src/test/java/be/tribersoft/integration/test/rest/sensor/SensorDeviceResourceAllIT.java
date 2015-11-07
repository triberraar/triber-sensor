package be.tribersoft.integration.test.rest.sensor;

import static com.jayway.restassured.RestAssured.given;
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
import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorFactory;
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
public class SensorDeviceResourceAllIT {

	private static final String DEVICE_NAME = "device name";
	private static final String URL = "/api/device/{deviceId}/sensor";
	private static final String UNIT_NAME = "unit name";
	private static final String TYPE_NAME = "type name";
	private static final String NAME_1 = "NAME 1";
	private static final String NAME_2 = "NAME 2";
	private static final String DESCRIPTION = "description";

	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private SensorJpaRepository sensorJpaRepository;
	@Inject
	private SensorFactory sensorFactory;
	@Inject
	private DeviceJpaRepository deviceJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private List<SensorEntity> sensors;
	private String typeId;
	private String unitId;
	private String deviceId;

	@Before
	public void setUp() {
		RestAssured.port = port;
		LocalDateTime now = LocalDateTime.now();
		typeJpaRepository.save(new TypeEntity(TYPE_NAME));
		typeId = typeJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();
		unitJpaRepository.save(new UnitEntity(UNIT_NAME));
		unitId = unitJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();
		deviceJpaRepository.save(new DeviceEntity(DEVICE_NAME));
		deviceId = deviceJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();
		DateFactory.fixateDate(now);
		sensorJpaRepository.save(sensorFactory.create(deviceId, new SensorMessageImpl(NAME_1, DESCRIPTION)));
		DateFactory.fixateDate(now.plusDays(1));
		sensorJpaRepository.save(sensorFactory.create(deviceId, new SensorMessageImpl(NAME_2, null)));
		sensors = sensorJpaRepository.findAllByOrderByCreationDateDesc();
	}

	@Test
	public void getsAllSensors() {
		// @formatter:off
		given().
			pathParam("deviceId", deviceId).
		when(). 
				
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				statusCode(HttpStatus.OK.value()).
				body("_links.self.href", is("http://localhost:" + port + "/api/device/" + deviceId+ "/sensor")).
				body("_embedded.sensors.size()", is(2)).
				body("_embedded.sensors[0].size()", is(5)).
				body("_embedded.sensors[0].name", is(NAME_2)).
				body("_embedded.sensors[0].description", isEmptyOrNullString()).
				body("_embedded.sensors[0].version", is(0)).
				body("_embedded.sensors[0].id", is(sensors.get(0).getId())).
				body("_embedded.sensors[0]._links.self.href", is("http://localhost:" + port + "/api/device/" + sensors.get(0).getDevice().getId() + "/sensor/" + sensors.get(0).getId())).
				body("_embedded.sensors[0]._links.type.href", is("http://localhost:" + port + "/api/admin/type/" + sensors.get(0).getType().getId())).
				body("_embedded.sensors[0]._links.unit.href", is("http://localhost:" + port + "/api/admin/unit/" + sensors.get(0).getUnit().getId())).
				body("_embedded.sensors[0]._links.device.href", is("http://localhost:" + port + "/api/device/" + sensors.get(0).getDevice().getId())).
				body("_embedded.sensors[1].size()", is(5)).
				body("_embedded.sensors[1].name", is(NAME_1)).
				body("_embedded.sensors[1].description", is(DESCRIPTION)).
				body("_embedded.sensors[1].version", is(0)).
				body("_embedded.sensors[1].id", is(sensors.get(1).getId())).
				body("_embedded.sensors[1]._links.self.href", is("http://localhost:" + port + "/api/device/" + sensors.get(1).getDevice().getId() + "/sensor/" + sensors.get(1).getId())).
				body("_embedded.sensors[1]._links.type.href", is("http://localhost:" + port + "/api/admin/type/" + sensors.get(1).getType().getId())).
				body("_embedded.sensors[1]._links.unit.href", is("http://localhost:" + port + "/api/admin/unit/" + sensors.get(1).getUnit().getId())).
				body("_embedded.sensors[1]._links.device.href", is("http://localhost:" + port + "/api/device/" + sensors.get(1).getDevice().getId())).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	private class SensorMessageImpl implements SensorMessage {

		private String name;
		private String description;

		public SensorMessageImpl(String name, String description) {
			this.name = name;
			this.description = description;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Optional<String> getDescription() {
			return Optional.ofNullable(description);
		}

		@Override
		public String getTypeId() {
			return typeId;
		}

		@Override
		public String getUnitId() {
			return unitId;
		}
	}
}
