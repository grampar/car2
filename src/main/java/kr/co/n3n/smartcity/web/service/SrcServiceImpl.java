package kr.co.n3n.smartcity.web.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n3n.smartcity.common.dao.CommonDao;
import kr.co.n3n.smartcity.common.model.CommMap;


@Service
public class SrcServiceImpl implements SrcService {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CommonDao commonDao;
	
	

	@Override
	public List<CommMap> getSrcList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("src.getSrcList", reqParam);
					        
		return list;
	}
	
	@Transactional
	@Override
	public int insertSrc(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
					
		
		if(reqParam.get("srcSeq")==null || reqParam.getString("srcSeq").equals("")) {
			commonDao.insert("src.insertSrc", reqParam);
		}else {
			commonDao.insert("src.updateSrc", reqParam);
		}
		
		
		return 1;
	};
	
	
	
	@Override
	public List<CommMap> getSrcItemList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("src.getSrcItemList", reqParam);
					        
		return list;
	}


	@Transactional
	@Override
	public int insertSrcItem(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		
		CommMap daoParam=new CommMap();
		String itemNo=reqParam.getString("itemNo");
		List<Map> list=reqParam.getListMap("list");
		
		daoParam.put("itemNo", itemNo);
		commonDao.delete("src.deleteSrcItem", daoParam);
		for(Map map:list) {			
			daoParam.put("srcNo", map.get("SRC_NO"));
			daoParam.put("srcRate", map.get("SRC_RATE"));
			commonDao.insert("src.insertSrcItem", daoParam);
		}
		
		return 0;
	}
	
	

	@Override
	public List<CommMap> getSrcSumList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("src.getSrcSumList", reqParam);
					        
		return list;
	}
	

	@Transactional
	@Override
	public int insertSrcSum(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		
		
		commonDao.insert("src.deleteSrcSum", reqParam);
		commonDao.insert("src.insertSrcSum", reqParam);
		
		
		return 0;
	}

	@Transactional
	@Override
	public int updateSrcSum(List<CommMap> reqParam, String mon) throws Exception {
		// TODO Auto-generated method stub
		
		
		CommMap daoParam=new CommMap();		
		daoParam.put("mon", mon);
				
		for(CommMap map:reqParam) {
			daoParam.put("srcNo", map.get("srcNo"));
			daoParam.put("deadlinePurchase", map.get("deadlinePurchase"));			
						
			commonDao.batchInsert("src.updateSrcSum", daoParam);
		}
		
		commonDao.getSession().flushStatements();
		
		return 1;
	}
	
	
	
}
