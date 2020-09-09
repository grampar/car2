package kr.co.n3n.smartcity.web.service;

import java.util.List;

import kr.co.n3n.smartcity.common.exception.RestException;
import kr.co.n3n.smartcity.common.model.CommMap;

public interface CarService {

	public List<CommMap> getCarList(CommMap reqParam) throws RestException;
	public List<CommMap> getCarItemList(CommMap reqParam) throws RestException;
}
