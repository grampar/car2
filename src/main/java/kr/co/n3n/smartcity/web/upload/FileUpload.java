package kr.co.n3n.smartcity.web.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kr.co.n3n.smartcity.common.util.TimeUtils;
import kr.co.n3n.smartcity.config.FileConfig;


public class FileUpload {
	
	
	
	MultipartFile mFile=null;
	public FileUpload(MultipartFile mFile){
		this.mFile=mFile;
	}
	
	public String uploadProcess(String path) throws Exception {
		
		
		String nowdate=TimeUtils.getInstance().formatDate(new Date(), "yyyyMMddHHmmss");
		
		
		String[] aryFile=mFile.getOriginalFilename().split("\\.");
		String filenm=aryFile[0]+nowdate+"."+aryFile[1];
		
		String filepath=path+filenm;
		File targetFile=new File(filepath);
		
		InputStream fileStream = null;
		
		try {
			
			fileStream = mFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			FileUtils.deleteQuietly(targetFile);
			e.printStackTrace();			
		}finally {
			if(fileStream!=null) fileStream.close();
		}
		
		
		return filepath;
	}

}
