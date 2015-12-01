package be.tribersoft.integration.test.rest.device;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class DeviceResourcePostIT {

	private static final String URL = "/api/{apiVersion}/device";
	private static final String ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String LOCATION = "location";

	@Inject
	private DeviceJpaRepository deviceJpaRepository;

	@Value("${local.server.port}")
	private int serverPort;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;
	}

	@Test
	public void createsANewDevice() {
		// @formatter:off
		given(). 
				pathParam("apiVersion", apiVersion).
				body(new DevicePostJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<DeviceEntity> devices = deviceJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(devices.size()).isEqualTo(1);
		DeviceEntity deviceEntity = devices.get(0);
		assertThat(deviceEntity.getName()).isEqualTo(NAME);
		assertThat(deviceEntity.getDescription().get()).isEqualTo(DESCRIPTION);
		assertThat(deviceEntity.getLocation().get()).isEqualTo(LOCATION);
		assertThat(deviceEntity.getId()).isNotNull();
		assertThat(deviceEntity.getVersion()).isEqualTo(0L);
	}

	@Test
	public void createsANewDeviceWithoutDescriptionOrLocation() {
		// @formatter:off
		given(). 
				pathParam("apiVersion", apiVersion).
				body(new DevicePostJsonImplWithoutDescriptionOrLocation()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<DeviceEntity> devices = deviceJpaRepository.findAllByOrderByCreationDateDesc();
		assertThat(devices.size()).isEqualTo(1);
		DeviceEntity deviceEntity = devices.get(0);
		assertThat(deviceEntity.getName()).isEqualTo(NAME);
		assertThat(deviceEntity.getDescription().isPresent()).isFalse();
		assertThat(deviceEntity.getLocation().isPresent()).isFalse();
		assertThat(deviceEntity.getId()).isNotNull();
		assertThat(deviceEntity.getVersion()).isEqualTo(0L);
	}

	@Test
	public void badRequestWhenDeviceIsNotValid() {
		// @formatter:off
		given(). 
				pathParam("apiVersion", apiVersion).
				body(new DevicePostJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				post(URL). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(ERROR_MESSAGE));
		// @formatter:on
	}

	private class DevicePostJsonImpl {
		@JsonProperty
		public String getName() {
			return NAME;
		}

		@JsonProperty
		public String getDescription() {
			return DESCRIPTION;
		}

		@JsonProperty
		public String getLocation() {
			return LOCATION;
		}
	}

	private class DevicePostJsonImplWithoutDescriptionOrLocation {
		@JsonProperty
		public String getName() {
			return NAME;
		}

	}

	private class DevicePostJsonImplInvalid {
		@JsonProperty
		public String getName() {
			return null;
		}

	}

}
