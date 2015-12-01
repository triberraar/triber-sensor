package be.tribersoft.sensor.domain.impl.device;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import be.tribersoft.common.DateFactory;

public class DeviceEntityConstructorTest {

	private static final LocalDateTime DATE = LocalDateTime.now();
	private static final String NAME = "name";

	@Before
	public void setUp() {
		DateFactory.fixateDate(DATE);
	}

	@Test
	public void constructsCorrectly() {
		DateFactory.fixateDate(DATE);
		DeviceEntity deviceEntity = new DeviceEntity(NAME);

		assertThat(deviceEntity.getName()).isEqualTo(NAME);
		assertThat(deviceEntity.getCreationDate()).isEqualTo(Date.from(DATE.atZone(ZoneId.systemDefault()).toInstant()));
	}

}
