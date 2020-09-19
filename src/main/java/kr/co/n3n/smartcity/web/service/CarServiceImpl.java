package kr.co.n3n.smartcity.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.n3n.smartcity.common.dao.CommonDao;
import kr.co.n3n.smartcity.common.exception.RestException;
import kr.co.n3n.smartcity.common.model.CommMap;


@Service
public class CarServiceImpl implements CarService {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CommonDao commonDao;
	
	

	@Override
	public List<CommMap> getCarList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getCarList", reqParam);
					        
		return list;
	}
	
	@Transactional
	@Override
	public int insertCar(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		
		CommMap checkMap=commonDao.selectOne("car.getCarList", reqParam);
		if(checkMap==null) {
			commonDao.insert("car.insertCar", reqParam);
		}else {
			commonDao.insert("car.updateCar", reqParam);
		}
		
		return 1;
	}

	@Override
	public List<CommMap> getCarMonCntList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getCarMonCnt", reqParam);
					        
		return list;
	}

	@Transactional
	@Override
	public int insertCarMonCnt(List<CommMap> reqParam, String mon) throws Exception {
		// TODO Auto-generated method stub
		
		CommMap checkMap;
		CommMap daoParam=new CommMap();
		
		daoParam.put("mon", mon);
		commonDao.batchInsert("car.deleteCarMonCnt", daoParam);
		
		for(CommMap map:reqParam) {
						
			if(map.getString("gubun").equals("")) { // 공백이면 자동차코드가 3자리만 사용함
				daoParam.put("carCode", map.getString("carCode").substring(0, 3));
			}else { // 공백이 아니면  들어온값 그대로 자동차코드를 사용 
				daoParam.put("carCode", map.get("carCode"));
			}
								
			daoParam.put("cnt", map.get("cnt"));
			checkMap=commonDao.batchSelectOne("car.getCarMonCnt", daoParam);
			if(checkMap==null) {
				commonDao.batchInsert("car.insertCarMonCnt", daoParam);
			}else {
				commonDao.batchInsert("car.updateCarMonCnt", daoParam);
			}
			
		}
		
		commonDao.getSession().flushStatements();
		
		return 1;
	}

	@Override
	public List<CommMap> getCarMonSumList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getCarMonSum", reqParam);
					        
		return list;
	}

	@Transactional
	@Override
	public int insertCarMonSum(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		
		commonDao.delete("car.deleteCarMonSum", reqParam);
		commonDao.insert("car.insertCarMonSum", reqParam);
				
		return 1;
	}
	

	@Override
	public List<CommMap> getItemList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getItemList", reqParam);
					        
		return list;
	}
	
	@Transactional
	@Override
	public int insertItem(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
					
			
		CommMap checkMap=commonDao.selectOne("car.getItemList", reqParam);
		if(checkMap==null) {
			commonDao.insert("car.insertItem", reqParam);
		}else {
			commonDao.insert("car.updateItem", reqParam);
		}
		
		
		return 1;
	};
	
	@Transactional
	@Override
	public int insertBatchItem(List<CommMap> reqParam) throws Exception {
		// TODO Auto-generated method stub
		CommMap checkMap;
		for(CommMap map:reqParam) {
			
			
			checkMap=commonDao.batchSelectOne("car.getItemList", map);
			if(checkMap==null) {
				commonDao.batchInsert("car.insertItem", map);
			}else {
				commonDao.batchInsert("car.updateItem", map);
			}
			
		}
		
		commonDao.getSession().flushStatements();
		
		return 1;
	};
	
	
	@Override
	public List<CommMap> getCarItemList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getCarItemList", reqParam);
					        
		return list;
	}


	@Transactional
	@Override
	public int insertCarItem(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		
		CommMap daoParam=new CommMap();
		String itemNo=reqParam.getString("itemNo");
		List<Map> list=reqParam.getListMap("list");
		
		daoParam.put("itemNo", itemNo);
		commonDao.delete("car.deleteCarItem", daoParam);
		for(Map map:list) {			
			daoParam.put("carCode", map.get("CAR_CODE"));
			commonDao.insert("car.insertCarItem", daoParam);
		}
		
		return 0;
	}



	@Override
	public List<CommMap> getItemMonRcarList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getItemMonRcarList", reqParam);
					        
		return list;
	}


	@Transactional
	@Override
	public int insertItemMonRcar(List<CommMap> reqParam, String mon) throws Exception {
		// TODO Auto-generated method stub
		CommMap checkMap;
		
		CommMap daoParam=new CommMap();
		daoParam.put("mon", mon);
		commonDao.batchInsert("car.deleteItemMonRcar", daoParam);
		
		for(CommMap map:reqParam) {
			daoParam.put("itemNo", map.get("itemNo"));
			daoParam.put("cnt", map.get("cnt"));
			
			commonDao.batchInsert("car.insertItemMonRcar", daoParam);						
		}
		
		commonDao.getSession().flushStatements();
		
		return 1;
	}
	

	@Override
	public List<CommMap> getItemRcarSumList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getItemRcarSumList", reqParam);
					        
		return list;
	}


	@Transactional
	@Override
	public int insertItemRcarSum(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		
		commonDao.delete("car.deleteItemRcarSum", reqParam);
		commonDao.delete("car.insertItemRcarSum", reqParam);		
		
		return 0;
	}
	
	@Override
	public List<CommMap> getItemMonCkdList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getItemMonCkdList", reqParam);
					        
		return list;
	}


	@Transactional
	@Override
	public int insertItemMonCkd(List<CommMap> reqParam, String mon) throws Exception {
		// TODO Auto-generated method stub
		CommMap checkMap;
		
		CommMap daoParam=new CommMap();
		daoParam.put("mon", mon);
		commonDao.batchInsert("car.deleteItemMonCkd", daoParam);
		
		for(CommMap map:reqParam) {
			daoParam.put("itemNo", map.get("itemNo"));
			daoParam.put("cnt", map.get("cnt"));
			
			commonDao.batchInsert("car.insertItemMonCkd", daoParam);						
		}		
		commonDao.getSession().flushStatements();		
		return 1;
	}

	@Override
	public List<CommMap> getItemCkdSumList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getItemCkdSumList", reqParam);
					        
		return list;
	}


	@Transactional
	@Override
	public int insertItemCkdSum(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub		
		commonDao.delete("car.deleteItemCkdSum", reqParam);
		commonDao.delete("car.insertItemCkdSum", reqParam);		
		
		return 0;
	}
	
	@Override
	public List<CommMap> getItemMonAsList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getItemMonAsList", reqParam);
					        
		return list;
	}


	@Transactional
	@Override
	public int insertItemMonAs(List<CommMap> reqParam, String mon) throws Exception {
		// TODO Auto-generated method stub
		CommMap checkMap;
		
		CommMap daoParam=new CommMap();
		daoParam.put("mon", mon);
		commonDao.batchInsert("car.deleteItemMonAs", daoParam);
		
		for(CommMap map:reqParam) {
			daoParam.put("itemNo", map.get("itemNo"));
			daoParam.put("cnt", map.get("cnt"));
			
			commonDao.batchInsert("car.insertItemMonAs", daoParam);						
		}
		
		commonDao.getSession().flushStatements();
		
		return 1;
	}	
	
	@Override
	public List<CommMap> getItemAsSumList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getItemAsSumList", reqParam);
					        
		return list;
	}

	@Transactional
	@Override
	public int insertItemAsSum(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		
		commonDao.delete("car.deleteItemAsSum", reqParam);
		commonDao.delete("car.insertItemAsSum", reqParam);		
		
		return 0;
	}
	
	
	@Override
	public List<CommMap> getItemMonMobisList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getItemMonMobisList", reqParam);
					        
		return list;
	}


	@Transactional
	@Override
	public int insertItemMonMobis(List<CommMap> reqParam, String mon) throws Exception {
		// TODO Auto-generated method stub
		CommMap checkMap;
		
		CommMap daoParam=new CommMap();
		daoParam.put("mon", mon);
		commonDao.batchInsert("car.deleteItemMonMobis", daoParam);
		
		for(CommMap map:reqParam) {
			daoParam.put("itemNo", map.get("itemNo"));
			daoParam.put("cnt", map.get("cnt"));
			
			commonDao.batchInsert("car.insertItemMonMobis", daoParam);						
		}
		
		commonDao.getSession().flushStatements();
		
		return 1;
	}	
	
	@Override
	public List<CommMap> getItemMobisSumList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getItemMobisSumList", reqParam);
					        
		return list;
	}

	@Transactional
	@Override
	public int insertItemMobisSum(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		
		commonDao.delete("car.deleteItemMobisSum", reqParam);
		commonDao.delete("car.insertItemMobisSum", reqParam);		
		
		return 0;
	}
	
	@Override
	public List<CommMap> getPriceSum(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getPriceSum", reqParam);
					        
		return list;
	}
	
}
