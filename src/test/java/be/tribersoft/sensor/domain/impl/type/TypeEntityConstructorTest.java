package be.tribersoft.sensor.domain.impl.type;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import be.tribersoft.common.DateFactory;

public class TypeEntityConstructorTest {

	private static final Date DATE = new Date();
	private static final String NAME = "name";

	@Before
	public void setUp() {
		DateFactory.fixateDate(DATE);
	}

	@Test
	public void constructsSuccessfully() {
		TypeEntity type = new TypeEntity(NAME);

		assertThat(type).isNotNull();
		assertThat(type.getName()).isEqualTo(NAME);
		assertThat(type.getCreationDate()).isEqualTo(DATE);
	}

}
