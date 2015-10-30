package be.tribersoft.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;

public class DateFactoryGenerateTest {

	@Test
	public void returnsFixatedDate() {
		LocalDateTime now = LocalDateTime.now();
		DateFactory.fixateDate(now);

		assertThat(DateFactory.generate()).isEqualTo(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));

		DateFactory.release();
		assertThat(DateFactory.generate()).isNotEqualTo(now);
	}
}
