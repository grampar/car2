package kr.co.n3n.smartcity.common.dao;

import java.util.List;
import java.util.Map;


import kr.co.n3n.smartcity.common.model.CommMap;





public interface CommonDao {
    List<CommMap> selectList(String queryId, CommMap daoParam) ;
    CommMap selectOne(String queryId, CommMap daoParam) ;
    void insert(String queryId, CommMap daoParam) ;
    void update(String queryId, CommMap daoParam) ;
    void delete(String queryId, CommMap daoParam) ;
    
    
}
