
package kr.co.n3n.smartcity.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
/* @MapperScan(basePackages = "kr.co.n3n") */
@EnableTransactionManagement
public class DatabaseConfig {
	
	@Autowired
    private mybatisSettings setting;
	
	
	
	@Bean(name="dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @Primary
    public DataSource dataSource() {
		
//    	HikariConfig config = new HikariConfig();
//    	config.setJdbcUrl(setting.getJdbcUrl());
//    	config.setDriverClassName(setting.getDriverClassName());
//    	config.setUsername(setting.getUsername());
//    	config.setPassword(setting.getPassword());
//    	
//    	HikariDataSource ds=new HikariDataSource( config );
//    	return ds;
    	
    	
    			
		DataSource dataSource = DataSourceBuilder.create().build();		
		return dataSource;
		
        //return DataSourceBuilder.create().build();
    }
	
	@Bean(name="motPlatformTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
	
	@Bean(name="motSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
    //public SqlSessionFactory sqlSessionFactory(@Qualifier("motDataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(setting.getMapperLocations()));
        sqlSessionFactoryBean.setTypeHandlersPackage(setting.getTypeHandlersPackage());
                
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="motSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("motSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    @Bean(name = "batchSqlSessionTemplate")
    public SqlSessionTemplate batchSqlSessionTemplate(@Qualifier("motSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
       return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

 

}
