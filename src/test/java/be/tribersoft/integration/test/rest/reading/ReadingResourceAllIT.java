package be.tribersoft.integration.test.rest.reading;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import be.tribersoft.TriberSensorApplication;
import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;
import be.tribersoft.sensor.domain.impl.reading.ReadingEntity;
import be.tribersoft.sensor.domain.impl.reading.ReadingJpaRepository;
import be.tribersoft.sensor.domain.impl.sensor.SensorEntity;
import be.tribersoft.sensor.domain.impl.sensor.SensorJpaRepository;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;
import be.tribersoft.sensor.domain.impl.unit.UnitEntity;
import be.tribersoft.sensor.domain.impl.unit.UnitJpaRepository;
import be.tribersoft.util.builder.DeviceBuilder;
import be.tribersoft.util.builder.ReadingBuilder;
import be.tribersoft.util.builder.SensorBuilder;
import be.tribersoft.util.builder.TypeBuilder;
import be.tribersoft.util.builder.UnitBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class ReadingResourceAllIT {
	private static final BigDecimal VALUE_2 = BigDecimal.valueOf(-4.3);
	private static final BigDecimal VALUE_1 = BigDecimal.valueOf(2.3);
	private static final String URL = "/api/{apiVersion}/device/{deviceId}/sensor/{sensorId}/reading";

	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private SensorJpaRepository sensorJpaRepository;
	@Inject
	private DeviceJpaRepository deviceJpaRepository;
	@Inject
	private ReadingJpaRepository readingJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private DeviceEntity deviceEntity;
	private SensorEntity sensorEntity;
	private List<ReadingEntity> readings;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = port;
		LocalDateTime now = LocalDateTime.now();
		TypeEntity typeEntity = TypeBuilder.aType().buildPersistent(typeJpaRepository);
		UnitEntity unitEntity = UnitBuilder.aUnit().buildPersistent(unitJpaRepository);
		deviceEntity = DeviceBuilder.aDevice().buildPersistent(deviceJpaRepository);
		sensorEntity = SensorBuilder.aSensor().withDevice(deviceEntity).withType(typeEntity).withUnit(unitEntity).buildPersistent(sensorJpaRepository);
		DateFactory.fixateDate(now);
		ReadingBuilder.aReading().withValue(VALUE_1).withSensor(sensorEntity).buildPersistent(readingJpaRepository);
		DateFactory.fixateDate(now.plusDays(1));
		ReadingBuilder.aReading().withValue(VALUE_2).withSensor(sensorEntity).buildPersistent(readingJpaRepository);
		readings = readingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(sensorEntity.getId());
	}

	@Test
	public void getsAllReadings() {
		// @formatter:off
		given().
			pathParam("apiVersion", apiVersion).
			pathParam("deviceId", deviceEntity.getId()).
			pathParam("sensorId", sensorEntity.getId()).
		when(). 
				
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				statusCode(HttpStatus.OK.value()).
				body("_links.size()", is(1)).
				body("_links.self.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + deviceEntity.getId()+ "/sensor/" + sensorEntity.getId()+"/reading?page=0")).
				body("_embedded.readings.size()", is(2)).
				body("_embedded.readings[0].size()", is(4)).
				body("_embedded.readings[0].value", is(VALUE_2.floatValue())).
				body("_embedded.readings[0].version", is(0)).
				body("_embedded.readings[0].id", is(readings.get(0).getId())).
				body("_embedded.readings[0]._links.size()", is(1)).
				body("_embedded.readings[0]._links.sensor.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + deviceEntity.getId()+ "/sensor/" + sensorEntity.getId())).
				body("_embedded.readings[1].size()", is(4)).
				body("_embedded.readings[1].value", is(VALUE_1.floatValue())).
				body("_embedded.readings[1].version", is(0)).
				body("_embedded.readings[1].id", is(readings.get(1).getId())).
				body("_embedded.readings[1]._links.size()", is(1)).
				body("_embedded.readings[1]._links.sensor.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + deviceEntity.getId()+ "/sensor/" + sensorEntity.getId())).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}
}
