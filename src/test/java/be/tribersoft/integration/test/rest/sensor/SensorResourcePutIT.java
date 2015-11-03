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
public class SensorResourcePutIT {

	private static final String URL = "/api/sensor/{uuid}";
	private static final String CONCURRENT_ERROR_MESSAGE = "Somebody else might have changed the resource, please reload";
	private static final String INVALID_ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String UPDATED_NAME = "updated name";
	private static final String UPDATED_DESCRIPTION = "updated description";
	private static final String UNIT_NAME = "unit name";
	private static final String TYPE_NAME = "type name";

	@Inject
	private SensorJpaRepository sensorJpaRepository;
	@Inject
	private SensorFactory sensorFactory;
	@Inject
	private UnitJpaRepository unitJpaRepository;
	@Inject
	private TypeJpaRepository typeJpaRepository;

	@Value("${local.server.port}")
	private int serverPort;
	private String uuid;
	private Long version;
	private String typeId;
	private String unitId;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;

		typeJpaRepository.save(new TypeEntity(TYPE_NAME));
		typeId = typeJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();
		unitJpaRepository.save(new UnitEntity(UNIT_NAME));
		unitId = unitJpaRepository.findAllByOrderByCreationDateDesc().get(0).getId();

		sensorJpaRepository.save(sensorFactory.create(new SensorMessageImpl()));
		SensorEntity sensorEntity = sensorJpaRepository.findAllByOrderByCreationDateDesc().get(0);
		uuid = sensorEntity.getId();
		version = sensorEntity.getVersion();
	}

	@Test
	public void updatesSensor() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
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
		assertThat(sensorEntity.getType().getId()).isEqualTo(typeId);
		assertThat(sensorEntity.getUnit().getId()).isEqualTo(unitId);
		assertThat(sensorEntity.getId()).isEqualTo(uuid);
		assertThat(sensorEntity.getVersion()).isEqualTo(version + 1);
	}

	@Test
	public void updatesSensorWithoutSymbol() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
				body(new SensorPutJsonImplWithoutSymbol()). 
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
		assertThat(sensorEntity.getType().getId()).isEqualTo(typeId);
		assertThat(sensorEntity.getUnit().getId()).isEqualTo(unitId);
		assertThat(sensorEntity.getId()).isEqualTo(uuid);
		assertThat(sensorEntity.getVersion()).isEqualTo(version + 1);
	}

	@Test
	public void badRequestWhenSensorIsNotValid() {
		// @formatter:off
		given(). 
				pathParam("uuid", uuid).
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
	public void badRequestWhenSensorHasConcurrentChanges() {
		// @formatter:off
		given(). 
				pathParam("uuid", uuid).
				body(new SensorPutJsonImplConcurrent()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(CONCURRENT_ERROR_MESSAGE));
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
			return typeId;
		}

		@JsonProperty
		public String getUnitId() {
			return unitId;
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
			return typeId;
		}

		@JsonProperty
		public String getUnitId() {
			return unitId;
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
			return typeId;
		}

		@JsonProperty
		public String getUnitId() {
			return unitId;
		}
	}

	private class SensorPutJsonImplWithoutSymbol {
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
			return typeId;
		}

		@JsonProperty
		public String getUnitId() {
			return unitId;
		}
	}

	private class SensorMessageImpl implements SensorMessage {

		@Override
		public String getName() {
			return NAME;
		}

		@Override
		public Optional<String> getDescription() {
			return Optional.of(DESCRIPTION);
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
