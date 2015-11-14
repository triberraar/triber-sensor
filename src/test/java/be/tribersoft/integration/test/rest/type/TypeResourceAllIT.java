package be.tribersoft.integration.test.rest.type;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

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
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;
import be.tribersoft.util.builder.TypeBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TriberSensorApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clean.sql")
public class TypeResourceAllIT {

	private static final String URL = "/api/admin/type";
	private static final String NAME_1 = "name 1";
	private static final String NAME_2 = "name 2";

	@Inject
	private TypeJpaRepository typeJpaRepository;

	@Value("${local.server.port}")
	private int port;
	private List<TypeEntity> types;

	@Before
	public void setUp() {
		RestAssured.port = port;
		LocalDateTime now = LocalDateTime.now();
		DateFactory.fixateDate(now);
		TypeBuilder.aType().withName(NAME_1).buildPersistent(typeJpaRepository);
		DateFactory.fixateDate(now.plusDays(1));
		TypeBuilder.aType().withName(NAME_2).buildPersistent(typeJpaRepository);
		types = typeJpaRepository.findAllByOrderByCreationDateDesc();
	}

	@Test
	public void getsAllTypes() {
		// @formatter:off
		when(). 
				get(URL). 
		then(). 
				contentType(ContentType.JSON).
				statusCode(HttpStatus.OK.value()).
				body("_links.self.href", is("http://localhost:" + port + "/api/admin/type")).
				body("_embedded.types.size()", is(2)).
				body("_embedded.types[0].size()", is(4)).
				body("_embedded.types[0].name", is(NAME_2)).
				body("_embedded.types[0].version", is(0)).
				body("_embedded.types[0].id", is(types.get(0).getId())).
				body("_embedded.types[0]._links.self.href", is("http://localhost:" + port + "/api/admin/type/" + types.get(0).getId())).
				body("_embedded.types[1].size()", is(4)).
				body("_embedded.types[1].name", is(NAME_1)).
				body("_embedded.types[1].version", is(0)).
				body("_embedded.types[1].id", is(types.get(1).getId())).
				body("_embedded.types[1]._links.self.href", is("http://localhost:" + port + "/api/admin/type/" + types.get(1).getId()));
		// @formatter:on
	}

}
