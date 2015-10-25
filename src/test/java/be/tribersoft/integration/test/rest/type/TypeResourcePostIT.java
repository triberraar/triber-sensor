package be.tribersoft.integration.test.rest.type;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import be.tribersoft.TriberSensorApplication;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;
import be.tribersoft.sensor.rest.type.TypePostJson;

@RunWith(SpringJUnit4ClassRunner.class) // 1
@SpringApplicationConfiguration(classes = TriberSensorApplication.class) // 2
@WebAppConfiguration // 3
@IntegrationTest("server.port:0") // 4
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TypeResourcePostIT {

	private static final String ERROR_MESSAGE = "Name can't be null";
	private static final String NAME = "name";

	@Inject
	private TypeJpaRepository typeJpaRepository;

	@Value("${local.server.port}")
	private int serverPort;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;
	}

	@Test
	public void createsANewType() {
		// @formatter:off
		given(). 
				body(new TypePostJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				post("/type"). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<TypeEntity> types = typeJpaRepository.findAll();
		assertThat(types.size()).isEqualTo(1);
		TypeEntity typeEntity = types.get(0);
		assertThat(typeEntity.getName()).isEqualTo(NAME);
		assertThat(typeEntity.getId()).isNotNull();
		assertThat(typeEntity.getVersion()).isEqualTo(0L);
	}

	@Test
	public void badRequestWhenTypeIsNotValid() {
		// @formatter:off
		given(). 
				body(new TypePostJsonImplInvalid()). 
				contentType(ContentType.JSON).
		when(). 
				post("/type"). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(ERROR_MESSAGE));
		// @formatter:on
	}

	private class TypePostJsonImpl extends TypePostJson {
		@Override
		public String getName() {
			return NAME;
		}
	}

	private class TypePostJsonImplInvalid extends TypePostJson {
		@Override
		public String getName() {
			return null;
		}
	}

}
