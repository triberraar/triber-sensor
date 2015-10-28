package be.tribersoft.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

public class DateFactoryGenerateTest {

	@Test
	public void returnsFixatedDate() {
		Date fixationDate = new Date(2);
		DateFactory.fixateDate(fixationDate);

		assertThat(DateFactory.generate()).isEqualTo(fixationDate);

		DateFactory.release();
		assertThat(DateFactory.generate()).isNotEqualTo(fixationDate);
	}
}
