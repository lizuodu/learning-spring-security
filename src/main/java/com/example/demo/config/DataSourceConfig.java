package com.example.demo.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 数据源配置
 * @author lizuodu
 * @date   2018年10月29日
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
	

	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(this.dataSource());
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory(ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource((DataSource) applicationContext.getBean("dataSource"));
		sessionFactory.setTypeAliasesPackage("com.example.demo.model");
		sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml"));

		// MyBatis配置
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(false);
		sessionFactory.setConfiguration(configuration);

		return sessionFactory;
	}

}
