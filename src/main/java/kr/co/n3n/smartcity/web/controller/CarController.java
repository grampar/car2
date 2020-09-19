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
import kr.co.n3n.smartcity.web.excel.AsExcelReader;
import kr.co.n3n.smartcity.web.excel.CarExcelReader;
import kr.co.n3n.smartcity.web.excel.CkdrExcelReader;
import kr.co.n3n.smartcity.web.excel.ItemExcelReader;
import kr.co.n3n.smartcity.web.excel.MobisExcelReader;
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
		LOG.info("###getCarList",reqParam);
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getCarList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertCar", method=RequestMethod.POST)	
	public ResponseEntity<RestResponse>   insertCar(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		
		int ret=carService.insertCar(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	@RequestMapping(value="/getCarMonCntList")	
	public ResponseEntity<RestResponse>   getCarItemList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getCarMonCntList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/insertCarMonCnt", method=RequestMethod.POST)	
	public ResponseEntity<RestResponse>   insertCarMonCnt(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("mon") String mon) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		
		FileUpload fileUpload=new FileUpload(multipartFile);
		String path=fileUpload.uploadProcess(fileConfig.getTargetpath());
		CarExcelReader carReader=new CarExcelReader();
		int startRow=1;
		List<CommMap> list=carReader.readExcel(path, startRow);
		
		int ret=carService.insertCarMonCnt(list, mon);
		
		
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	@RequestMapping(value="/getCarMonSumList")	
	public ResponseEntity<RestResponse>   getCarMonSumList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getCarMonSumList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertCarMonSum")	
	public ResponseEntity<RestResponse>   insertCarMonSum(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret= carService.insertCarMonSum(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
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
	@RequestMapping(value="/getItemRcarSumList")	
	public ResponseEntity<RestResponse>   getItemRcarSumList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getItemRcarSumList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertItemRcarSum")	
	public ResponseEntity<RestResponse>   insertItemRcarSum(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret=carService.insertItemRcarSum(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	
	
	
	
	@RequestMapping(value="/getItemMonCkdList")	
	public ResponseEntity<RestResponse>   getItemMonCkdList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getItemMonCkdList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/itemMonCkdUpload", method=RequestMethod.POST)	
	public ResponseEntity<RestResponse>   itemMonCkdUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("mon") String mon) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		
		FileUpload fileUpload=new FileUpload(multipartFile);
		String path=fileUpload.uploadProcess(fileConfig.getTargetpath());
		CkdrExcelReader itemReader=new CkdrExcelReader();
		int startRow=1;
		List<CommMap> list=itemReader.readExcel(path, startRow);
		
		int ret=carService.insertItemMonCkd(list, mon);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	@RequestMapping(value="/getItemCkdSumList")	
	public ResponseEntity<RestResponse>   getItemCkdSumList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getItemCkdSumList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertItemCkdSum")	
	public ResponseEntity<RestResponse>   insertItemCkdSum(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret=carService.insertItemCkdSum(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	
	
	
	
	
	@RequestMapping(value="/getItemMonAsList")	
	public ResponseEntity<RestResponse>   getItemMonAsList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getItemMonAsList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/itemMonAsUpload", method=RequestMethod.POST)	
	public ResponseEntity<RestResponse>   itemMonAsUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("mon") String mon) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		
		FileUpload fileUpload=new FileUpload(multipartFile);
		String path=fileUpload.uploadProcess(fileConfig.getTargetpath());
		AsExcelReader itemReader=new AsExcelReader();
		int startRow=5;
		List<CommMap> list=itemReader.readExcel(path, startRow);
		
		int ret=carService.insertItemMonAs(list, mon);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getItemAsSumList")	
	public ResponseEntity<RestResponse>   getItemAsSumList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getItemAsSumList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertItemAsSum")	
	public ResponseEntity<RestResponse>   insertItemAsSum(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret=carService.insertItemAsSum(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getItemMonMobisList")	
	public ResponseEntity<RestResponse>   getItemMonMobisList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getItemMonMobisList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/itemMonMobisUpload", method=RequestMethod.POST)	
	public ResponseEntity<RestResponse>   itemMonMobisUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("mon") String mon) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		
		FileUpload fileUpload=new FileUpload(multipartFile);
		String path=fileUpload.uploadProcess(fileConfig.getTargetpath());
		MobisExcelReader itemReader=new MobisExcelReader();
		int startRow=1;
		List<CommMap> list=itemReader.readExcel(path, startRow);
		
		int ret=carService.insertItemMonMobis(list, mon);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getItemMobisSumList")	
	public ResponseEntity<RestResponse>   getItemMobisSumList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getItemMobisSumList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertItemMobisSum")	
	public ResponseEntity<RestResponse>   insertItemMobisSum(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret=carService.insertItemMobisSum(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getPriceSum")	
	public ResponseEntity<RestResponse>   getPriceSum(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = carService.getPriceSum(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	
}
