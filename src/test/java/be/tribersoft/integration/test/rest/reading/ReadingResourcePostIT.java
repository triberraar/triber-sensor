package be.tribersoft.integration.test.rest.reading;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;
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
import be.tribersoft.common.testData.DeviceBuilder;
import be.tribersoft.common.testData.SensorBuilder;
import be.tribersoft.common.testData.TypeBuilder;
import be.tribersoft.common.testData.UnitBuilder;
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
public class ReadingResourcePostIT {

	private static final String URL = "/api/{apiVersion}/device/{deviceId}/sensor/{sensorId}/reading";
	private static final String ERROR_MESSAGE = "Value can't be null";
	private static final BigDecimal VALUE = BigDecimal.valueOf(23.0);

	@Inject
	private SensorJpaRepository sensorJpaRepository;
	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private DeviceJpaRepository deviceJpaRepository;
	@Inject
	private ReadingJpaRepository ReadingJpaRepository;

	@Value("${local.server.port}")
	private int serverPort;
	private TypeEntity typeEntity;
	private UnitEntity unitEntity;
	private DeviceEntity deviceEntity;
	private SensorEntity sensorEntity;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;

		typeEntity = TypeBuilder.aType().buildPersistent(typeJpaRepository);
		unitEntity = UnitBuilder.aUnit().buildPersistent(unitJpaRepository);
		deviceEntity = DeviceBuilder.aDevice().buildPersistent(deviceJpaRepository);
		sensorEntity = SensorBuilder.aSensor().withDevice(deviceEntity).withType(typeEntity).withUnit(unitEntity).buildPersistent(sensorJpaRepository);
	}

	@Test
	public void createsANewReading() {
		// @formatter:off
		given(). 
				pathParam("apiVersion", apiVersion).
				pathParam("deviceId", deviceEntity.getId()).
				pathParam("sensorId", sensorEntity.getId()).
				body(new ReadingPostJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<ReadingEntity> readings = ReadingJpaRepository.findAllBySensorIdOrderByCreationDateDesc(sensorEntity.getId());
		assertThat(readings.size()).isEqualTo(1);
		ReadingEntity readingEntity = readings.get(0);
		assertThat(readingEntity.getValue()).isEqualTo(VALUE);
		assertThat(readingEntity.getSensor().getId()).isEqualTo(sensorEntity.getId());
		assertThat(readingEntity.getId()).isNotNull();
		assertThat(readingEntity.getVersion()).isEqualTo(0L);
	}

	@Test
	public void badRequestWhenSensorIsNotValid() {
		// @formatter:off
		given(). 
				pathParam("apiVersion", apiVersion).
				pathParam("deviceId", deviceEntity.getId()).
				pathParam("sensorId", sensorEntity.getId()).
				body(new ReadingPostJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(ERROR_MESSAGE));
		// @formatter:on
	}

	private class ReadingPostJsonImpl {
		@JsonProperty
		public BigDecimal getValue() {
			return VALUE;
		}

	}

	private class ReadingPostJsonImplInvalid {
		@JsonProperty
		public String getDummy() {
			return "dummy";
		}

	}

}
