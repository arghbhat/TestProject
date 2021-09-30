package com.team.alert.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.team.alert.customexception.AlertException;
import com.team.alert.customexception.DevelopersMissedAlertException;
import com.team.alert.customexception.TeamCreationException;

@ControllerAdvice 
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

	
	@InitBinder
	private void activateDirectFieldAccess(DataBinder dataBinder) {
		dataBinder.initDirectFieldAccess();
	}
	
	@ExceptionHandler(value={AlertException.class,TeamCreationException.class,DevelopersMissedAlertException.class})
	public ResponseEntity<Object> handleRestException(Exception ex,WebRequest request){
		if(ex instanceof DevelopersMissedAlertException) {
			return handleExceptionInternal(ex, ((DevelopersMissedAlertException)ex).getMessages(),
					new HttpHeaders(), HttpStatus.BAD_REQUEST , request);
		}
		return handleExceptionInternal(ex, ex.getMessage(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST , request);
	}
	
}
