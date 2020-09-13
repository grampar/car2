package kr.co.n3n.smartcity.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import kr.co.n3n.smartcity.common.model.CommMap;



@Repository
public class CommonDaoImpl extends DaoSupport  implements CommonDao {
	
	
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<CommMap> selectList(String queryId, CommMap daoParam) {
	    return sqlSession.selectList(queryId, daoParam);
	}
	
	@Override
    public CommMap selectOne(String queryId, CommMap daoParam) {
        return sqlSession.selectOne(queryId, daoParam);
    }
    
    @Override
    public void insert(String queryId, CommMap daoParam) {
        sqlSession.insert(queryId, daoParam);
    }
    
    
    
    @Override
    public void update(String queryId, CommMap daoParam) {
        sqlSession.update(queryId, daoParam);
    }
    
    @Override
    public void delete(String queryId, CommMap daoParam) {
        sqlSession.delete(queryId, daoParam);
    }
    
    
    @Override
    public void batchInsert(String queryId, CommMap daoParam) {
        batchSession.insert(queryId, daoParam);
    }

	@Override
	public SqlSession getSession() {
		// TODO Auto-generated method stub
		return batchSession;
	}

	@Override
	public CommMap batchSelectOne(String queryId, CommMap daoParam) {
		// TODO Auto-generated method stub
		return batchSession.selectOne(queryId, daoParam);
	}

	
    
    
    
    
}
