package be.tribersoft.sensor.domain.api.unit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Unit not found")
public class UnitNotFoundException extends RuntimeException {

}
