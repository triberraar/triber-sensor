package be.tribersoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class TriberSensorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriberSensorApplication.class, args);
	}

	@Bean(name = "validationMessageSource")
	public ReloadableResourceBundleMessageSource validationMessageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:messages/ValidationMessages");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:messages/messages");
		messageBundle.setDefaultEncoding("UTF-8");
		return messageBundle;
	}
}
