package be.tribersoft.sensor.domain.impl.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TypeEntityConstructorTest {

	private static final String NAME = "name";

	@Test
	public void constructsSuccessfully() {
		TypeEntity type = new TypeEntity(NAME);

		assertThat(type).isNotNull();
		assertThat(type.getName()).isEqualTo(NAME);
	}

}
