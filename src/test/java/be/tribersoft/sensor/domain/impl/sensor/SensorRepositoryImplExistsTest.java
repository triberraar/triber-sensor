package be.tribersoft.sensor.domain.impl.sensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SensorRepositoryImplExistsTest {
	private static final String ID = "id";
	@InjectMocks
	private SensorRepositoryImpl sensorRepositoryImpl;
	@Mock
	private SensorJpaRepository sensorJpaRepository;

	@Before
	public void setup() {
		when(sensorJpaRepository.exists(ID)).thenReturn(true);
	}

	@Test
	public void delegatesToJpaRepository() {
		assertThat(sensorRepositoryImpl.exists(ID)).isTrue();
	}

}
