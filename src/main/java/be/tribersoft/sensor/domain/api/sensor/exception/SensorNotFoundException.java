package be.tribersoft.sensor.domain.api.sensor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Sensor not found")
public class SensorNotFoundException extends RuntimeException {

}
