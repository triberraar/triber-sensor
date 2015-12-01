package be.tribersoft.sensor.rest;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;

import be.tribersoft.common.rest.FutureApiVersionException;
import be.tribersoft.common.rest.IncorrectApiVersionException;
import be.tribersoft.semver.Version;

@Named
public class VersionValidator {

	@Value("${api.version}")
	private String apiVersion;

	private Version semver;

	@PostConstruct
	public void init() {
		semver = new Version(apiVersion);
	}

	public void validate(String apiVersion) {
		if (!semver.isCompatbile(new Version(apiVersion))) {
			throw new IncorrectApiVersionException(this.apiVersion, apiVersion);
		}
		if (semver.isBefore(new Version(apiVersion))) {
			throw new FutureApiVersionException(this.apiVersion, apiVersion);
		}
	}

}
