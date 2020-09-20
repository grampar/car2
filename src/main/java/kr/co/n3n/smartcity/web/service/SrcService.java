package kr.co.n3n.smartcity.web.service;

import java.util.List;

import kr.co.n3n.smartcity.common.model.CommMap;

public interface SrcService {

	
	public List<CommMap> getSrcList(CommMap reqParam) throws Exception;
	public int insertSrc(CommMap reqParam) throws Exception;
	
	
	public List<CommMap> getSrcItemList(CommMap reqParam) throws Exception;
	public int insertSrcItem(CommMap reqParam) throws Exception;
	
	
	public List<CommMap> getSrcSumList(CommMap reqParam) throws Exception;	
	public int insertSrcSum(CommMap reqParam) throws Exception;
	public int updateSrcSum(List<CommMap> reqParam, String mon) throws Exception;
	
	
}
