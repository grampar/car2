package kr.co.n3n.smartcity.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.n3n.smartcity.common.dao.CommonDao;
import kr.co.n3n.smartcity.common.exception.RestException;
import kr.co.n3n.smartcity.common.model.CommMap;


@Service
public class CarServiceImpl implements CarService {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CommonDao commonDao;
	
	

	@Override
	public List<CommMap> getCarList(CommMap reqParam) throws RestException {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getCarList", reqParam);
					        
		return list;
	}



	@Override
	public List<CommMap> getCarItemList(CommMap reqParam) throws RestException {
		// TODO Auto-generated method stub
		List<CommMap> list=null;			
		list=commonDao.selectList("car.getCarList", reqParam);
					        
		return list;
	};
	
}
