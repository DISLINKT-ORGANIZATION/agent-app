package agent.agentapp.exceptions;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(InvalidUsername.class)
	public ResponseEntity<Object> handleInvalidUsername(HttpServletResponse response) throws IOException {
		return new ResponseEntity<>("Invalid username.", HttpStatus.BAD_REQUEST);
	}

}
