package be.tribersoft.sensor.domain.impl.reading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.api.reading.exception.ReadingNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ReadingRepositoryImplGetByIdTest {

	private static final String ID = "id";
	@InjectMocks
	private ReadingRepositoryImpl readingRepositoryImpl;
	@Mock
	private ReadingJpaRepository readingJpaRepository;
	@Mock
	private ReadingEntity reading;

	@Test
	public void returnsFoundReading() {
		when(readingJpaRepository.findById(ID)).thenReturn(Optional.of(reading));
		doReturn(Optional.of(reading)).when(readingJpaRepository).findById(ID);

		assertThat(readingRepositoryImpl.getById(ID)).isEqualTo(reading);
	}

	@Test(expected = ReadingNotFoundException.class)
	public void failsWhenReadingNotFound() {
		doReturn(Optional.empty()).when(readingJpaRepository).findById(ID);

		readingRepositoryImpl.getById(ID);
	}
}
