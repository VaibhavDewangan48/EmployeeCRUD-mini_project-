package net.employee_crud.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFounudException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFounudException(String message){
		super(message);
	}
	
}
