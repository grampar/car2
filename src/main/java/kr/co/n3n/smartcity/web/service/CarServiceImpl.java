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
	public int insertCar(List<CommMap> reqParam, String mon) throws Exception {
		// TODO Auto-generated method stub
		
		CommMap checkMap;
		for(CommMap map:reqParam) {
			
			map.put("mon", mon);
			commonDao.batchInsert("car.deleteCarMonCnt", map);
			
			
			System.out.println(map);
			if(map.get("carCode")!=null && !map.getString("carCode").trim().equals("")) {
				checkMap=commonDao.batchSelectOne("car.getCarList", map);
				if(checkMap==null) {
					commonDao.batchInsert("car.insertCar", map);
				}
				
				
				
				checkMap=commonDao.batchSelectOne("car.getCarMonCnt", map);
				if(checkMap==null) {
					commonDao.batchInsert("car.insertCarMonCnt", map);
				}else {
					commonDao.batchInsert("car.updateCarMonCnt", map);
				}
				
			}
			
			
		}
		
		commonDao.getSession().flushStatements();
		
		return 1;
	}

	@Override
	public List<CommMap> getCarMonCntList(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getCarMonCnt", reqParam);
					        
		return list;
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



	@Override
	public int insertCarItem(CommMap reqParam) throws Exception {
		// TODO Auto-generated method stub
		
		CommMap daoParam=new CommMap();
		String carCode=reqParam.getString("carCode");
		List<CommMap> list=reqParam.getListMap("list");
		
		daoParam.put("carCode", carCode);
		commonDao.delete("car.deleteCarItem", daoParam);
		for(CommMap map:list) {
			daoParam.put("itemNo", map.getString("itemNo"));
			commonDao.insert("car.insertCarItem", daoParam);
		}
		
		return 0;
	}
	
}
