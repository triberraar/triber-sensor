package be.tribersoft.integration.test.rest.device;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;

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
import be.tribersoft.sensor.domain.api.type.DeviceMessage;
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceFactory;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class DeviceResourceGetIT {

	private static final String URL = "/api/device/{uuid}";
	private static final String DESCRIPTION = "description";
	private static final String LOCATION = "location";
	private static final String NON_EXISTING_UUID = "non existing uuid";
	private static final String NAME = "name";

	@Inject
	private DeviceFactory deviceFactory;

	@Inject
	private DeviceJpaRepository deviceJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private String uuid;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void getDevice() {
		deviceJpaRepository.save(deviceFactory.create(new DeviceMessageImpl(DESCRIPTION, LOCATION)));
		DeviceEntity deviceEntity = deviceJpaRepository.findAllByOrderByCreationDateDesc().get(0);
		uuid = deviceEntity.getId();
		// @formatter:off
		given().
				pathParam("uuid", uuid).
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(6)).
				body("name", is(NAME)).
				body("description", is(DESCRIPTION)).
				body("location", is(LOCATION)).
				body("id", is(uuid)).
				body("version", is(0)).
				body("_links.self.href", is("http://localhost:" + port+"/api/device/" + uuid)).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	@Test
	public void failsWhenNotFound() {
		// @formatter:off
		given().
				pathParam("uuid", NON_EXISTING_UUID).
		when(). 
				get(URL). 
		then(). 
				statusCode(HttpStatus.NOT_FOUND.value());
		// @formatter:on
	}

	@Test
	public void getUnitWithoutDescriptionOrLocation() {
		deviceJpaRepository.save(deviceFactory.create(new DeviceMessageImpl(null, null)));
		DeviceEntity deviceEntity = deviceJpaRepository.findAllByOrderByCreationDateDesc().get(0);
		uuid = deviceEntity.getId();
		// @formatter:off
		given().
				pathParam("uuid", uuid).
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				body("size()", is(6)).
				body("name", is(NAME)).
				body("description", isEmptyOrNullString()).
				body("location", isEmptyOrNullString()).
				body("id", is(uuid)).
				body("version", is(0)).
				body("_links.self.href", is("http://localhost:" + port+"/api/device/" + uuid)).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

	private class DeviceMessageImpl implements DeviceMessage {

		private String description;
		private String location;

		public DeviceMessageImpl(String description, String location) {
			this.description = description;
			this.location = location;
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
		public Optional<String> getLocation() {
			return Optional.ofNullable(location);
		}

	}
}