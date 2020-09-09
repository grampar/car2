package kr.co.n3n.smartcity.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import kr.co.n3n.smartcity.common.exception.RestException;
import kr.co.n3n.smartcity.common.util.RestResponse;



@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ScExceptionHandler {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	
	
	@ExceptionHandler(RestException.class)
	@ResponseBody
	public ResponseEntity<?> restException(HttpServletRequest request, RestException e){
		
		RestResponse restResponse = new RestResponse();
		LOG.error(e.getMessage(), e);
		return new ResponseEntity<>(restResponse.setFail(e), HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(Exception.class)	
	public ResponseEntity<?> handleException(HttpServletRequest request, Exception e){
		RestResponse restResponse = new RestResponse();		
		LOG.error(e.getMessage(), e);
		return new ResponseEntity<>(restResponse.setFail(e), HttpStatus.BAD_REQUEST);
	}
		
	
	
}
