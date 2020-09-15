package kr.co.n3n.smartcity.web.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.n3n.smartcity.common.model.CommMap;

public abstract class AbstractExcelReader {
	
	
	protected abstract  CommMap readCell(XSSFRow row) throws Exception;
	public abstract  List<CommMap> readExcel(String path, int startRow) throws Exception;
	
	private  XSSFSheet getSheet(String path) throws Exception  {
		
		FileInputStream file=null;
		XSSFWorkbook workbook=null ;
		XSSFSheet sheet=null;
		try {
			file = new FileInputStream(path);
			workbook = new XSSFWorkbook(file);
			sheet=workbook.getSheetAt(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(file!=null) file.close();
			if(workbook!=null) workbook.close();			
		}
                
        
		
		return sheet;
	}
	
	protected String getCellValue(XSSFCell cell) {
		String value="";
		if(cell!=null) {			
			switch(cell.getCellType()) {
	    	case FORMULA:
	    		value=cell.getCellFormula();
	    		break;
	    	case STRING:
	    		value=cell.getStringCellValue();
	    		break;
	    	case NUMERIC:
	    		value=cell.getNumericCellValue()+"";
	    		break;
	    	case BLANK:
	    		value="";
	    		break; 
	    	case ERROR:
	    		value=cell.getErrorCellString();
	    		break; 
	    	}
		}
		
		return value;
	}
	
	
	protected List<CommMap> getRow(String path, int startRow) throws Exception{
		
		 int rowindex;
         XSSFSheet sheet=getSheet(path);
         
         //행의 수
         int rows=sheet.getPhysicalNumberOfRows();
         CommMap retMap=null;
         List<CommMap> retList=new ArrayList<CommMap>();
         for(rowindex=startRow;rowindex<rows;rowindex++){
             //행을읽는다
             XSSFRow row=sheet.getRow(rowindex);
             
             if(row !=null){
            	 retMap=readCell(row);
             }
             
             retList.add(retMap);
         }
         
         return retList;
	}
}
