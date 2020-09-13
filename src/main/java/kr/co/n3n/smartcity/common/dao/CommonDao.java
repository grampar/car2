package kr.co.n3n.smartcity.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import kr.co.n3n.smartcity.common.model.CommMap;



public interface CommonDao {
    
	SqlSession getSession();
	List<CommMap> selectList(String queryId, CommMap daoParam) ;
    CommMap selectOne(String queryId, CommMap daoParam) ;
    void insert(String queryId, CommMap daoParam) ;    
    void update(String queryId, CommMap daoParam) ;
    void delete(String queryId, CommMap daoParam) ;
    
    CommMap batchSelectOne(String queryId, CommMap daoParam) ;
    void batchInsert(String queryId, CommMap daoParam) ;
    
    
}
