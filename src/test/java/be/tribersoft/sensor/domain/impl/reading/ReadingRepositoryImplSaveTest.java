package be.tribersoft.sensor.domain.impl.reading;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.sensor.domain.impl.reading.ReadingEntity;
import be.tribersoft.sensor.domain.impl.reading.ReadingJpaRepository;
import be.tribersoft.sensor.domain.impl.reading.ReadingRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReadingRepositoryImplSaveTest {

	@InjectMocks
	private ReadingRepositoryImpl readingRepositoryImpl;

	@Mock
	private ReadingJpaRepository readingJpaRepository;
	@Mock
	private ReadingEntity readingEntity;

	@Test
	public void delegatesToSpringDataRepository() {
		readingRepositoryImpl.save(readingEntity);

		verify(readingJpaRepository).save(readingEntity);
	}

}
