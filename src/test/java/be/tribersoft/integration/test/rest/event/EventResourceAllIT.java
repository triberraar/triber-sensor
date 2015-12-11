package be.tribersoft.integration.test.rest.event;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import be.tribersoft.TriberSensorApplication;
import be.tribersoft.common.DateFactory;
import be.tribersoft.common.test_data.DeviceBuilder;
import be.tribersoft.common.test_data.ReadingBuilder;
import be.tribersoft.common.test_data.SensorBuilder;
import be.tribersoft.common.test_data.TypeBuilder;
import be.tribersoft.common.test_data.UnitBuilder;
import be.tribersoft.sensor.domain.api.event.EventMode;
import be.tribersoft.sensor.domain.api.event.EventSubject;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
@ActiveProfiles("test")
public class EventResourceAllIT {
	private static final BigDecimal VALUE_2 = BigDecimal.valueOf(-4.3);
	private static final BigDecimal VALUE_1 = BigDecimal.valueOf(2.3);
	private static final String URL = "/api/{apiVersion}/event";

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
		DateFactory.fixateDate(now);
		deviceEntity = DeviceBuilder.aDevice().buildPersistent(deviceJpaRepository);
		DateFactory.fixateDate(now.plusDays(1));
		sensorEntity = SensorBuilder.aSensor().withDevice(deviceEntity).withType(typeEntity).withUnit(unitEntity).buildPersistent(sensorJpaRepository);
		DateFactory.fixateDate(now.plusDays(2));
		ReadingBuilder.aReading().withValue(VALUE_1).withSensor(sensorEntity).buildPersistent(readingJpaRepository);
		DateFactory.fixateDate(now.plusDays(3));
		ReadingBuilder.aReading().withValue(VALUE_2).withSensor(sensorEntity).buildPersistent(readingJpaRepository);
		readings = readingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(sensorEntity.getId());
		DateFactory.fixateDate(now.plusDays(4));
		readingJpaRepository.delete(readings.get(0).getId());
	}

	@Test
	public void getsAllEvents() {
		// @formatter:off
		given().
			pathParam("apiVersion", apiVersion).
		when(). 
				
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				statusCode(HttpStatus.OK.value()).
				body("_links.size()", is(3)).
				body("_links.self.href", is("http://localhost:" + port + "/api/"+apiVersion+"/event?page=0")).
				body("_links.first.href", is("http://localhost:" + port + "/api/"+apiVersion+"/event?page=0")).
				body("_links.last.href", is("http://localhost:" + port + "/api/"+apiVersion+"/event?page=0")).
				body("_embedded.events.size()", is(5)).
				body("_embedded.events[0].size()", is(4)).
				body("_embedded.events[0].eventMode", is(EventMode.DELETED.getMessage())).
				body("_embedded.events[0].eventId", is(readings.get(0).getId())).
				body("_embedded.events[0].eventSubject", is(EventSubject.READING.getMessage())).
				body("_embedded.events[1].size()", is(4)).
				body("_embedded.events[1].eventMode", is(EventMode.CREATED.getMessage())).
				body("_embedded.events[1].eventId", is(readings.get(0).getId())).
				body("_embedded.events[1].eventSubject", is(EventSubject.READING.getMessage())).
				body("_embedded.events[2].size()", is(5)).
				body("_embedded.events[2].eventMode", is(EventMode.CREATED.getMessage())).
				body("_embedded.events[2].eventId", is(readings.get(1).getId())).
				body("_embedded.events[2].eventSubject", is(EventSubject.READING.getMessage())).
				body("_embedded.events[2]._links.size()", is(1)).
				body("_embedded.events[2]._links.readings.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + deviceEntity.getId()+ "/sensor/" + sensorEntity.getId()+"/reading?page=0")).
				body("_embedded.events[3].size()", is(5)).
				body("_embedded.events[3].eventMode", is(EventMode.CREATED.getMessage())).
				body("_embedded.events[3].eventId", is(sensorEntity.getId())).
				body("_embedded.events[3].eventSubject", is(EventSubject.SENSOR.getMessage())).
				body("_embedded.events[3]._links.size()", is(1)).
				body("_embedded.events[3]._links.sensor.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + deviceEntity.getId()+ "/sensor/" + sensorEntity.getId())).
				body("_embedded.events[4].size()", is(5)).
				body("_embedded.events[4].eventMode", is(EventMode.CREATED.getMessage())).
				body("_embedded.events[4].eventId", is(deviceEntity.getId())).
				body("_embedded.events[4].eventSubject", is(EventSubject.DEVICE.getMessage())).
				body("_embedded.events[4]._links.size()", is(1)).
				body("_embedded.events[4]._links.device.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + deviceEntity.getId())).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}
}
