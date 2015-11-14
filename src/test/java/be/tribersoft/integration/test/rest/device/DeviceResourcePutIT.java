package be.tribersoft.integration.test.rest.device;

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
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;
import be.tribersoft.util.builder.DeviceBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class DeviceResourcePutIT {

	private static final String DEVICE_NOT_FOUND_EXCEPTION = "Device not found";
	private static final String URL = "/api/device/{uuid}";
	private static final String CONCURRENT_ERROR_MESSAGE = "Somebody else might have changed the resource, please reload";
	private static final String INVALID_ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String LOCATION = "location";
	private static final String UPDATED_NAME = "updated name";
	private static final String UPDATED_DESCRIPTION = "updated description";
	private static final String UPDATED_LOCATION = "updated location";
	private static final String NON_EXISTING_UUID = "non existing uuid";

	@Inject
	private DeviceJpaRepository deviceJpaRepository;

	@Value("${local.server.port}")
	private int serverPort;
	private String uuid;
	private Long version;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;

		DeviceEntity deviceEntity = DeviceBuilder.aDevice().withName(NAME).withDescription(Optional.of(DESCRIPTION)).withLocation(Optional.of(LOCATION)).buildPersistent(deviceJpaRepository);
		uuid = deviceEntity.getId();
		version = deviceEntity.getVersion();
	}

	@Test
	public void updatesDevice() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
				body(new DevicePutJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<DeviceEntity> devices = deviceJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(devices.size()).isEqualTo(1);
		DeviceEntity deviceEntity = devices.get(0);
		assertThat(deviceEntity.getName()).isEqualTo(UPDATED_NAME);
		assertThat(deviceEntity.getDescription().get()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(deviceEntity.getLocation().get()).isEqualTo(UPDATED_LOCATION);
		assertThat(deviceEntity.getId()).isEqualTo(uuid);
		assertThat(deviceEntity.getVersion()).isEqualTo(version + 1);
	}

	@Test
	public void updatesDeviceWithoutLocationOrDescription() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
				body(new DevicePutJsonImplWithoutLocationOrDescription()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<DeviceEntity> devices = deviceJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(devices.size()).isEqualTo(1);
		DeviceEntity deviceEntity = devices.get(0);
		assertThat(deviceEntity.getName()).isEqualTo(UPDATED_NAME);
		assertThat(deviceEntity.getDescription().isPresent()).isFalse();
		assertThat(deviceEntity.getLocation().isPresent()).isFalse();
		assertThat(deviceEntity.getId()).isEqualTo(uuid);
		assertThat(deviceEntity.getVersion()).isEqualTo(version + 1);
	}

	@Test
	public void badRequestWhenDeviceIsNotValid() {
		// @formatter:off
		given(). 
				pathParam("uuid", uuid).
				body(new DevicePutJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(INVALID_ERROR_MESSAGE));
		// @formatter:on
	}

	@Test
	public void conflictWhenDeviceHasConcurrentChanges() {
		// @formatter:off
		given(). 
				pathParam("uuid", uuid).
				body(new DevicePutJsonImplConcurrent()). 
				contentType(ContentType.JSON).
		when(). 
				put(URL). 
		then(). 
				statusCode(HttpStatus.CONFLICT.value()).
				body("message", equalTo(CONCURRENT_ERROR_MESSAGE));
		// @formatter:on
	}

	@Test
	public void notFoundWhenDeviceDoesntExist() {
		// @formatter:off
		given(). 
			pathParam("uuid", NON_EXISTING_UUID).
			body(new DevicePutJsonImpl()). 
			contentType(ContentType.JSON).
		when(). 
			put(URL). 
		then(). 
			statusCode(HttpStatus.NOT_FOUND.value()).
			body("message", equalTo(DEVICE_NOT_FOUND_EXCEPTION));
		// @formatter:on

	}

	private class DevicePutJsonImpl {

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
		public String getLocation() {
			return UPDATED_LOCATION;
		}
	}

	private class DevicePutJsonImplInvalid {
		@JsonProperty
		public String getName() {
			return null;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}

	}

	private class DevicePutJsonImplConcurrent {
		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public Long getVersion() {
			return version + 1;
		}

	}

	private class DevicePutJsonImplWithoutLocationOrDescription {
		@JsonProperty
		public String getName() {
			return UPDATED_NAME;
		}

		@JsonProperty
		public Long getVersion() {
			return version;
		}

	}

}
