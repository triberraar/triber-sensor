package be.tribersoft.sensor.domain.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Concurrent modification")
public class ConcurrentModificationException extends RuntimeException {

}
