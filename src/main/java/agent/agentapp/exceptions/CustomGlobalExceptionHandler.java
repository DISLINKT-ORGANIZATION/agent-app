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

	@ExceptionHandler(UsernameAlreadyExists.class)
	public ResponseEntity<Object> handleUsernameAlreadyExists(HttpServletResponse response) throws IOException {
		return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmailAlreadyExists.class)
	public ResponseEntity<Object> handleEmailAlreadyExists(HttpServletResponse response) throws IOException {
		return new ResponseEntity<>("Email already exists.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidUsername.class)
	public ResponseEntity<Object> handleInvalidUsername(HttpServletResponse response) throws IOException {
		return new ResponseEntity<>("Invalid username.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFound.class)
	public ResponseEntity<Object> handleNotFound(HttpServletResponse response) {
		return new ResponseEntity<>("Entity not found.", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UsernameOrEmailAlreadyExists.class)
	public ResponseEntity<Object> handleUsernameOrEmailAlreadyExists(HttpServletResponse response) throws IOException {
		return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RequestAlreadyExists.class)
	public ResponseEntity<Object> handleRequestAlreadyExists(HttpServletResponse response) throws IOException {
		return new ResponseEntity<>("Request already exists.", HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ServerException.class)
	public ResponseEntity<Object> handleServerException(HttpServletResponse response) throws IOException {
		return new ResponseEntity<>("Service is unavailable. Please create new company registration request.", HttpStatus.SERVICE_UNAVAILABLE);
	}

}
