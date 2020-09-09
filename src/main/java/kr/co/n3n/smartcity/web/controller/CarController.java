package kr.co.n3n.smartcity.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.n3n.smartcity.common.exception.RestException;
import kr.co.n3n.smartcity.common.model.CommMap;
import kr.co.n3n.smartcity.common.util.RestResponse;
import kr.co.n3n.smartcity.web.service.CarServiceImpl;

@Controller
@RequestMapping(value="/car")
public class CarController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public CarServiceImpl carService;
	
	@RequestMapping(value="/getCarList")	
	public ResponseEntity<RestResponse>   getCarList(@RequestBody(required=true) CommMap reqParam, HttpSession session) throws RestException{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getCarList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
}
