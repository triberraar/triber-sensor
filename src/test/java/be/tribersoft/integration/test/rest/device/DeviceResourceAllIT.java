package be.tribersoft.integration.test.rest.device;

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
import be.tribersoft.common.testData.DeviceBuilder;
import be.tribersoft.sensor.domain.impl.device.DeviceEntity;
import be.tribersoft.sensor.domain.impl.device.DeviceJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class DeviceResourceAllIT {

	private static final String URL = "/api/{apiVersion}/device";
	private static final String DESCRIPTION = "description";
	private static final String LOCATION = "location";
	private static final String NAME_1 = "name 1";
	private static final String NAME_2 = "name 2";

	@Inject
	private DeviceJpaRepository deviceJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private List<DeviceEntity> devices;
	@Value("${api.version}")
	private String apiVersion;

	@Before
	public void setUp() {
		RestAssured.port = port;
		LocalDateTime now = LocalDateTime.now();
		DateFactory.fixateDate(now);
		DeviceBuilder.aDevice().withName(NAME_1).withDescription(Optional.of(DESCRIPTION)).withLocation(Optional.of(LOCATION)).buildPersistent(deviceJpaRepository);
		DateFactory.fixateDate(now.plusDays(1));
		DeviceBuilder.aDevice().withName(NAME_2).withDescription(Optional.empty()).withLocation(Optional.empty()).buildPersistent(deviceJpaRepository);
		devices = deviceJpaRepository.findAllByOrderByCreationDateDesc();
	}

	@Test
	public void getsAllDevices() {
		// @formatter:off
		given().
				pathParam("apiVersion", apiVersion).
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				statusCode(HttpStatus.OK.value()).
				body("_links.self.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device")).
				body("_embedded.devices.size()", is(2)).
				body("_embedded.devices[0].size()", is(6)).
				body("_embedded.devices[0].name", is(NAME_2)).
				body("_embedded.devices[0].description", isEmptyOrNullString()).
				body("_embedded.devices[0].location", isEmptyOrNullString()).
				body("_embedded.devices[0].version", is(0)).
				body("_embedded.devices[0].id", is(devices.get(0).getId())).
				body("_embedded.devices[0]._links.size()", is(2)).
				body("_embedded.devices[0]._links.self.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + devices.get(0).getId())).
				body("_embedded.devices[0]._links.sensors.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + devices.get(0).getId()+"/sensor")).
				body("_embedded.devices[1].size()", is(6)).
				body("_embedded.devices[1].name", is(NAME_1)).
				body("_embedded.devices[1].description", is(DESCRIPTION)).
				body("_embedded.devices[1].location", is(LOCATION)).
				body("_embedded.devices[1].version", is(0)).
				body("_embedded.devices[1].id", is(devices.get(1).getId())).
				body("_embedded.devices[1]._links.size()", is(2)).
				body("_embedded.devices[1]._links.self.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + devices	.get(1).getId())).
				body("_embedded.devices[1]._links.sensors.href", is("http://localhost:" + port + "/api/"+apiVersion+"/device/" + devices	.get(1).getId()+"/sensor")).
				statusCode(HttpStatus.OK.value());
		// @formatter:on
	}

}
