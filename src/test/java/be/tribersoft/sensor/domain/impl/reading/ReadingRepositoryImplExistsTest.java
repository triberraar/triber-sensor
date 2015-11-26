package be.tribersoft.sensor.domain.impl.reading;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReadingRepositoryImplExistsTest {
	private static final String ID = "id";
	@InjectMocks
	private ReadingRepositoryImpl readingRepositoryImpl;
	@Mock
	private ReadingJpaRepository readingJpaRepository;

	@Before
	public void setup() {
		when(readingJpaRepository.exists(ID)).thenReturn(true);
	}

	@Test
	public void delegatesToJpaRepository() {
		assertThat(readingRepositoryImpl.exists(ID)).isTrue();
	}
}
