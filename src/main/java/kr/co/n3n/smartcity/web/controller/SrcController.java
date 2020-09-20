package kr.co.n3n.smartcity.web.controller;

import java.util.List;

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

import kr.co.n3n.smartcity.common.model.CommMap;
import kr.co.n3n.smartcity.common.util.RestResponse;
import kr.co.n3n.smartcity.config.FileConfig;
import kr.co.n3n.smartcity.web.excel.CarExcelReader;
import kr.co.n3n.smartcity.web.excel.ItemExcelReader;
import kr.co.n3n.smartcity.web.excel.SrcSumExcelReader;
import kr.co.n3n.smartcity.web.service.SrcService;
import kr.co.n3n.smartcity.web.upload.FileUpload;

@Controller
@RequestMapping(value="/src")
public class SrcController {
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public SrcService srcService;
	
	@Autowired
	public FileConfig fileConfig;
	
	@RequestMapping(value="/getSrcList")	
	public ResponseEntity<RestResponse>   getSrcList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = srcService.getSrcList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertSrc")	
	public ResponseEntity<RestResponse>   insertSrc(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret= srcService.insertSrc(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getSrcItemList")	
	public ResponseEntity<RestResponse>   getSrcItemList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = srcService.getSrcItemList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertSrcItem")	
	public ResponseEntity<RestResponse>   insertSrcItem(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret= srcService.insertSrcItem(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getSrcSumList")	
	public ResponseEntity<RestResponse>   getSrcSumList(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		List<CommMap> retMap = srcService.getSrcSumList(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(retMap), HttpStatus.OK);
	}
	
	@RequestMapping(value="/insertSrcSum")	
	public ResponseEntity<RestResponse>   insertSrcSum(@RequestBody(required=true) CommMap reqParam) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		int ret= srcService.insertSrcSum(reqParam);
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/updateSrcSum", method=RequestMethod.POST)	
	public ResponseEntity<RestResponse>   updateSrcSum(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("mon") String mon) throws Exception{
		
		RestResponse restResponse = new RestResponse();		
		
		FileUpload fileUpload=new FileUpload(multipartFile);
		String path=fileUpload.uploadProcess(fileConfig.getTargetpath());
		SrcSumExcelReader carReader=new SrcSumExcelReader();
		int startRow=2;
		List<CommMap> list=carReader.readExcel(path, startRow);
		
		int ret=srcService.updateSrcSum(list, mon);
		
		
		
		return new ResponseEntity<RestResponse >(restResponse .setSuccess(ret), HttpStatus.OK);
	}
	
}
