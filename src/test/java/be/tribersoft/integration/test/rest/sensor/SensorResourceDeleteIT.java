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
import be.tribersoft.sensor.domain.api.sensor.SensorMessage;
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
public class SensorResourceDeleteIT {

	private static final String URL = "/api/sensor/{uuid}";
	private static final String SENSOR_NOT_FOUND_EXCEPTION = "Sensor not found";
	private static final String DESCRIPTION = "description";
	private static final String NAME = "name";
	private static final String UNIT_NAME = "unit name";
	private static final String TYPE_NAME = "type name";
	private static final String NON_EXISTING_UUID = "non existing uuid";

	@Inject
	private SensorFactory sensorFactory;

	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private SensorJpaRepository sensorJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private String uuid;
	private String typeId;
	private String unitId;
	private Long version;

	@Before
	public void setUp() {
		RestAssured.port = port;

		typeJpaRepository.save(new TypeEntity(TYPE_NAME));
		typeId = typeJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();
		unitJpaRepository.save(new UnitEntity(UNIT_NAME));
		unitId = unitJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();
		sensorJpaRepository.save(sensorFactory.create(new SensorMessageImpl(DESCRIPTION)));
		SensorEntity sensorEntity = sensorJpaRepository.findAllByOrderByCreationDateDesc().get(0);
		uuid = sensorEntity.getId();
		version = sensorEntity.getVersion();
	}

	@Test
	public void deletesSensor() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
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

	private class SensorMessageImpl implements SensorMessage {

		private String description;

		public SensorMessageImpl(String description) {
			this.description = description;
		}

		@Override
		public String getName() {
			return NAME;
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

	private class SensorDeleteJsonImpl {
		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}
}
