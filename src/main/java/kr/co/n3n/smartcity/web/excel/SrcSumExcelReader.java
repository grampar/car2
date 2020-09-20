package kr.co.n3n.smartcity.web.excel;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import kr.co.n3n.smartcity.common.model.CommMap;

public class SrcSumExcelReader extends AbstractExcelReader {

	@Override
	protected CommMap readCell(XSSFRow row) throws Exception{
		
		int columnindex=0;
		String value="";
		CommMap map=new CommMap();
		
		//셀의 수
        int cells=row.getPhysicalNumberOfCells();
        for(columnindex=0; columnindex<cells; columnindex++){
            //셀값을 읽는다
            XSSFCell cell=row.getCell(columnindex);
            
            //셀이 빈값일경우를 위한 널체크
            value=this.getCellValue(cell);
            if(columnindex==2) {            	
            	map.put("srcNo", value);
            }else if(columnindex==4) {            	
            	map.put("deadlinePurchase", value);            	
            }      
        }
        
		// TODO Auto-generated method stub
		return map;
	}

	@Override
	public List<CommMap> readExcel(String path, int startRow) throws Exception {
		
		// TODO Auto-generated method stub
		return this.getRow(path, startRow);
	}
	
	
	
}
