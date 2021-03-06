package kr.co.n3n.smartcity.web.service;

import java.util.List;

import kr.co.n3n.smartcity.common.exception.RestException;
import kr.co.n3n.smartcity.common.model.CommMap;

public interface CarService {

	public List<CommMap> getCarList(CommMap reqParam) throws Exception;	
	public int insertCar(CommMap reqParam) throws Exception;
	
	
	public List<CommMap> getCarMonCntList(CommMap reqParam) throws Exception;
	public int insertCarMonCnt(List<CommMap> reqParam, String mon) throws Exception;
	
	public List<CommMap> getCarMonSumList(CommMap reqParam) throws Exception;
	public int insertCarMonSum(CommMap reqParam) throws Exception;
	
	
	public List<CommMap> getItemList(CommMap reqParam) throws Exception;
	public int insertItem(CommMap reqParam) throws Exception;
	public int insertBatchItem(List<CommMap> reqParam) throws Exception;
	
	
	public List<CommMap> getCarItemList(CommMap reqParam) throws Exception;
	public int insertCarItem(CommMap reqParam) throws Exception;
	
	public List<CommMap> getItemMonRcarList(CommMap reqParam) throws Exception;
	public int insertItemMonRcar(List<CommMap> reqParam, String mon) throws Exception;
	public List<CommMap> getItemRcarSumList(CommMap reqParam) throws Exception;
	public int insertItemRcarSum(CommMap reqParam) throws Exception;
	
	
	public List<CommMap> getItemMonCkdList(CommMap reqParam) throws Exception;
	public int insertItemMonCkd(List<CommMap> reqParam, String mon) throws Exception;
	public List<CommMap> getItemCkdSumList(CommMap reqParam) throws Exception;
	public int insertItemCkdSum(CommMap reqParam) throws Exception;
	
	public List<CommMap> getItemMonAsList(CommMap reqParam) throws Exception;
	public int insertItemMonAs(List<CommMap> reqParam, String mon) throws Exception;
	public List<CommMap> getItemAsSumList(CommMap reqParam) throws Exception;
	public int insertItemAsSum(CommMap reqParam) throws Exception;
	
	
	public List<CommMap> getItemMonMobisList(CommMap reqParam) throws Exception;
	public int insertItemMonMobis(List<CommMap> reqParam, String mon) throws Exception;
	public List<CommMap> getItemMobisSumList(CommMap reqParam) throws Exception;
	public int insertItemMobisSum(CommMap reqParam) throws Exception;
	
	public List<CommMap> getPriceSum(CommMap reqParam) throws Exception;
	
}
