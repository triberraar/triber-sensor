package be.tribersoft.integration.test.rest.device;

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
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;
import be.tribersoft.util.builder.DeviceBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class DeviceResourceDeleteIT {

	private static final String URL = "/api/{apiVersion}/device/{uuid}";
	private static final String DESCRIPTION = "description";
	private static final String LOCATION = "location";
	private static final String NAME = "name";
	private static final String NON_EXISTING_UUID = "non existing uuid";

	@Inject
	private DeviceJpaRepository deviceJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private String uuid;
	private Long version;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = port;

		DeviceEntity deviceEntity = DeviceBuilder.aDevice().withName(NAME).withDescription(Optional.of(DESCRIPTION)).withLocation(Optional.of(LOCATION)).buildPersistent(deviceJpaRepository);
		uuid = deviceEntity.getId();
		version = deviceEntity.getVersion();
	}

	@Test
	public void deletesDevice() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
				pathParam("uuid", uuid).
				body(new DeviceDeleteJsonImpl()).
				contentType(ContentType.JSON).
		when(). 
				delete(URL). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on
		assertThat(deviceJpaRepository.findAllByOrderByCreationDateDesc().isEmpty()).isTrue();
	}

	@Test
	public void failsWhenDeviceNotFound() {
		// @formatter:off
		given().
			pathParam("apiVersion", apiVersion).
			pathParam("uuid", NON_EXISTING_UUID).
			body(new DeviceDeleteJsonImpl()).
		contentType(ContentType.JSON).
		when(). 
			delete(URL). 
		then(). 
			statusCode(HttpStatus.NOT_FOUND.value()).
			body("message", equalTo("Device not found"));
		// @formatter:on

		assertThat(deviceJpaRepository.findAllByOrderByCreationDateDesc().isEmpty()).isFalse();
	}

	private class DeviceDeleteJsonImpl {
		@JsonProperty
		public Long getVersion() {
			return version;
		}
	}
}
