package be.tribersoft.common;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public class BeanInjectorTest {

	private BeanInjector beanInjector = new BeanInjector();

	@Mock
	private ApplicationContext applicationContext;

	@Mock
	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	@Mock
	private Object target;

	@Before
	public void setUp() {
		beanInjector.setApplicationContext(applicationContext);
		when(applicationContext.getAutowireCapableBeanFactory()).thenReturn(autowireCapableBeanFactory);
	}

	@Test
	public void injectsTheBeanInTheTarget() {
		BeanInjector.inject(target, (Object) null);

		verify(autowireCapableBeanFactory).autowireBean(target);
	}

}
