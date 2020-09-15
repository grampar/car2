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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.n3n.smartcity.common.exception.RestException;
import kr.co.n3n.smartcity.common.model.CommMap;
import kr.co.n3n.smartcity.common.util.RestResponse;
import kr.co.n3n.smartcity.config.FileConfig;
import kr.co.n3n.smartcity.web.excel.CarExcelReader;
import kr.co.n3n.smartcity.web.excel.ItemExcelReader;
import kr.co.n3n.smartcity.web.excel.RcarExcelReader;
import kr.co.n3n.smartcity.web.service.CarService;
import kr.co.n3n.smartcity.web.service.CarServiceImpl;
import kr.co.n3n.smartcity.web.upload.FileUpload;

@Controller
@RequestMapping(value="/car")
public class CarController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public CarService carService;
	
	@Autowired
	public FileConfig fileConfig;
	
	@RequestMapping(value="/getCarList")	
	public ResponseEntity<RestResponse>   getCarList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getCarList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/carUpload", method=RequestMethod.POST)	
	public ResponseEntity<RestResponse>   excelInsertCar(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("mon") String mon) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		
		FileUpload fileUpload=new FileUpload(multipartFile);
		String path=fileUpload.uploadProcess(fileConfig.getTargetpath());
		CarExcelReader carReader=new CarExcelReader();
		int startRow=0;
		List<CommMap> list=carReader.readExcel(path, startRow);
		
		int ret=carService.insertCar(list, mon);
		
		
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getCarMonCntList")	
	public ResponseEntity<RestResponse>   getCarItemList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getCarMonCntList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getItemList")	
	public ResponseEntity<RestResponse>   getIemList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getItemList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/ItemInsert")	
	public ResponseEntity<RestResponse>   itemInsert(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret= carService.insertItem(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	@RequestMapping(value="/itemUpload", method=RequestMethod.POST)	
	public ResponseEntity<RestResponse>   excelInsertItem(@RequestParam("file") MultipartFile multipartFile) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		
		FileUpload fileUpload=new FileUpload(multipartFile);
		String path=fileUpload.uploadProcess(fileConfig.getTargetpath());
		ItemExcelReader itemReader=new ItemExcelReader();
		int startRow=2;
		List<CommMap> list=itemReader.readExcel(path, startRow);
		
		int ret=carService.insertBatchItem(list);
		
		
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getCarItemList")	
	public ResponseEntity<RestResponse>   getCarIemList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getCarItemList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertCarItem")	
	public ResponseEntity<RestResponse>   insertCarItem(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret= carService.insertCarItem(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getItemMonRcarList")	
	public ResponseEntity<RestResponse>   getItemMonRcarList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getItemMonRcarList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/itemMonRcarUpload", method=RequestMethod.POST)	
	public ResponseEntity<RestResponse>   itemMonRcarUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("mon") String mon) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		
		FileUpload fileUpload=new FileUpload(multipartFile);
		String path=fileUpload.uploadProcess(fileConfig.getTargetpath());
		RcarExcelReader itemReader=new RcarExcelReader();
		int startRow=0;
		List<CommMap> list=itemReader.readExcel(path, startRow);
		
		int ret=carService.insertItemMonRcar(list, mon);
		
		
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
}
