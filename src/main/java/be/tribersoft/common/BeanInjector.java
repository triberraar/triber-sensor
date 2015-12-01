package be.tribersoft.common;

import javax.inject.Named;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Named
public class BeanInjector implements ApplicationContextAware {
	private static ApplicationContext CONTEXT;

	public BeanInjector() {
	}

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		CONTEXT = context;
	}

	public static void inject(Object target, Object... beans) {
		for (Object bean : beans) {
			if (bean == null) {
				CONTEXT.getAutowireCapableBeanFactory().autowireBean(target);
			}
		}
	}
}
