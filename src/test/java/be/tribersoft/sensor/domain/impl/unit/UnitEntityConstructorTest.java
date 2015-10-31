package be.tribersoft.sensor.domain.impl.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import be.tribersoft.common.DateFactory;

public class UnitEntityConstructorTest {
	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String NAME = "name";

	@Before
	public void setUp() {
		DateFactory.fixateDate(DATE);
	}

	@Test
	public void constructsSuccessfully() {
		UnitEntity unit = new UnitEntity(NAME);

		assertThat(unit).isNotNull();
		assertThat(unit.getName()).isEqualTo(NAME);
		assertThat(unit.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
	}
}
