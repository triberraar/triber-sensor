package be.tribersoft.sensor.domain.impl.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.common.DateFactory;
import be.tribersoft.sensor.domain.api.unit.UnitMessage;

@RunWith(MockitoJUnitRunner.class)
public class UnitFactoryCreateTest {

	private static final Optional<String> SYMBOL = Optional.of("symbol");
	private static final String NAME = "name";
	private static final LocalDateTime DATE = LocalDateTime.now();

	private UnitFactory unitFactory = new UnitFactory();

	@Mock
	private UnitMessage unitMessage;

	@Before
	public void setUp() {
		when(unitMessage.getName()).thenReturn(NAME);
		when(unitMessage.getSymbol()).thenReturn(SYMBOL);

		DateFactory.fixateDate(DATE);
	}

	@Test
	public void createsAUnitEntity() {
		UnitEntity createdUnitEntity = unitFactory.create(unitMessage);

		assertThat(createdUnitEntity).isNotNull();
		assertThat(createdUnitEntity.getName()).isEqualTo(NAME);
		assertThat(createdUnitEntity.getSymbol()).isEqualTo(SYMBOL);
		assertThat(createdUnitEntity.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
	}

}
