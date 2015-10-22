package be.tribersoft.sensor.domain.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Type not found")
public class TypeNotFoundException extends RuntimeException {

}
