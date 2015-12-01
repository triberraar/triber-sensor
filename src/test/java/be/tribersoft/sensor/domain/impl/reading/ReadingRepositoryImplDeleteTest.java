package be.tribersoft.sensor.domain.impl.reading;

import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReadingRepositoryImplDeleteTest {
	@InjectMocks
	private ReadingRepositoryImpl readingRepositoryImpl;
	@Mock
	private ReadingJpaRepository readingJpaRepository;
	@Mock
	private ReadingEntity readingEntity1, readingEntity2;

	@Test
	public void delegatesToJpaRepository() {
		readingRepositoryImpl.delete(Arrays.asList(readingEntity1, readingEntity2));

		verify(readingJpaRepository).delete(readingEntity1);
		verify(readingJpaRepository).delete(readingEntity2);
	}
}
