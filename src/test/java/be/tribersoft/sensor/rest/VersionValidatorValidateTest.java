package be.tribersoft.sensor.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import be.tribersoft.common.rest.FutureApiVersionException;
import be.tribersoft.common.rest.IncorrectApiVersionException;

@RunWith(MockitoJUnitRunner.class)
public class VersionValidatorValidateTest {

	private VersionValidator versionValidator = new VersionValidator();

	@Before
	public void setUp() {
		Whitebox.setInternalState(versionValidator, "apiVersion", "2.2.0");
		versionValidator.init();
	}

	@Test(expected = IncorrectApiVersionException.class)
	public void failsWhenProvidedVersionIsNotCompatible() {
		versionValidator.validate("1.0.0");
	}

	@Test(expected = FutureApiVersionException.class)
	public void failsWhenProvidedVersionIsInTheFuture() {
		versionValidator.validate("2.3.0");
	}

	@Test
	public void succeeds() {
		versionValidator.validate("2.1.0");
	}
}
