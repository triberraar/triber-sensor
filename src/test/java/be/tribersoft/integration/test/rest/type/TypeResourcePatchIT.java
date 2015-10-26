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
import be.tribersoft.sensor.domain.api.type.TypeCreate;
import be.tribersoft.sensor.domain.impl.type.TypeEntity;
import be.tribersoft.sensor.domain.impl.type.TypeFactory;
import be.tribersoft.sensor.domain.impl.type.TypeJpaRepository;

@RunWith(SpringJUnit4ClassRunner.class) // 1
@SpringApplicationConfiguration(classes = TriberSensorApplication.class) // 2
@WebAppConfiguration // 3
@IntegrationTest("server.port:0") // 4
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TypeResourcePatchIT {

	private static final String CONCURRENT_ERROR_MESSAGE = "Somebody else might have changed the resource, please reload";
	private static final String NAME = "name";
	private static final String UPDATED_NAME = "updated name";

	@Inject
	private TypeJpaRepository typeJpaRepository;
	@Inject
	private TypeFactory typeFactory;

	@Value("${local.server.port}")
	private int serverPort;
	private String uuid;
	private Long version;

	@Before
	public void setUp() {
		RestAssured.port = serverPort;
		typeJpaRepository.save(typeFactory.create(new TypeCreateImpl()));
		TypeEntity typeEntity = typeJpaRepository.findAll().get(0);
		uuid = typeEntity.getId();
		version = typeEntity.getVersion();
	}

	@Test
	public void patchesType() {
		// @formatter:off
		given().
				pathParam("uuid", uuid).
				body(new TypePatchJsonImpl()). 
				contentType(ContentType.JSON).
		when(). 
				patch("/type/{uuid}"). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<TypeEntity> types = typeJpaRepository.findAll();
		assertThat(types.size()).isEqualTo(1);
		TypeEntity typeEntity = types.get(0);
		assertThat(typeEntity.getName()).isEqualTo(UPDATED_NAME);
		assertThat(typeEntity.getId()).isEqualTo(uuid);
		assertThat(typeEntity.getVersion()).isEqualTo(version + 1);
	}

	@Test
	public void succeedsWithOnlyVersionBody() {
		// @formatter:off
		given(). 
				pathParam("uuid", uuid).
				body(new EmptyPatchJson()). 
				contentType(ContentType.JSON).
		when(). 
				patch("/type/{uuid}"). 
		then(). 
				statusCode(HttpStatus.OK.value());
		// @formatter:on

		List<TypeEntity> types = typeJpaRepository.findAll();
		assertThat(types.size()).isEqualTo(1);
		TypeEntity typeEntity = types.get(0);
		assertThat(typeEntity.getName()).isEqualTo(NAME);
		assertThat(typeEntity.getId()).isEqualTo(uuid);
		assertThat(typeEntity.getVersion()).isEqualTo(version);
	}

	@Test
	public void badRequestWhenTypeHasConcurrentChanges() {
		// @formatter:off
		given(). 
				pathParam("uuid", uuid).
				body(new TypePatchJsonImplConcurrent()). 
				contentType(ContentType.JSON).
		when(). 
				patch("/type/{uuid}"). 
		then(). 
				statusCode(HttpStatus.BAD_REQUEST.value()).
				body("message", equalTo(CONCURRENT_ERROR_MESSAGE));
		// @formatter:on
	}

	private class TypePatchJsonImpl {

		public String getName() {
			return UPDATED_NAME;
		}

		public Long getVersion() {
			return version;
		}
	}

	private class EmptyPatchJson {
		public String getName() {
			return null;
		}

		public Long getVersion() {
			return version;
		}
	}

	private class TypePatchJsonImplConcurrent {
		public String getName() {
			return UPDATED_NAME;
		}

		public Long getVersion() {
			return version + 1;
		}
	}

	private class TypeCreateImpl implements TypeCreate {

		@Override
		public String getName() {
			return NAME;
		}

	}

}
