package kr.co.n3n.smartcity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.datasource.hikari")
public class mybatisSettings {
	
	private String driverClassName;
	private String jdbcUrl;
	private String username;
	private String password;
	private String autoCommit;
	private String connectionTestQuery;
	private String minimumIdle;
	private String maximumPoolSize;
	private String transactionIsolation;
	    
	private String mapperLocations;
    private String typeHandlersPackage;
    private String executorType;
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAutoCommit() {
		return autoCommit;
	}
	public void setAutoCommit(String autoCommit) {
		this.autoCommit = autoCommit;
	}
	public String getConnectionTestQuery() {
		return connectionTestQuery;
	}
	public void setConnectionTestQuery(String connectionTestQuery) {
		this.connectionTestQuery = connectionTestQuery;
	}
	public String getMinimumIdle() {
		return minimumIdle;
	}
	public void setMinimumIdle(String minimumIdle) {
		this.minimumIdle = minimumIdle;
	}
	public String getMaximumPoolSize() {
		return maximumPoolSize;
	}
	public void setMaximumPoolSize(String maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
	public String getTransactionIsolation() {
		return transactionIsolation;
	}
	public void setTransactionIsolation(String transactionIsolation) {
		this.transactionIsolation = transactionIsolation;
	}
	public String getMapperLocations() {
		return mapperLocations;
	}
	public void setMapperLocations(String mapperLocations) {
		this.mapperLocations = mapperLocations;
	}
	public String getTypeHandlersPackage() {
		return typeHandlersPackage;
	}
	public void setTypeHandlersPackage(String typeHandlersPackage) {
		this.typeHandlersPackage = typeHandlersPackage;
	}
	public String getExecutorType() {
		return executorType;
	}
	public void setExecutorType(String executorType) {
		this.executorType = executorType;
	}
    
    
}
