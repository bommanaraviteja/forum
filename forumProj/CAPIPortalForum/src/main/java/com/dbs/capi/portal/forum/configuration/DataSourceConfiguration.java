/**
 * 
 */
package com.dbs.capi.portal.forum.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;

/**
 * @author Harry Potter
 *
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

	@Autowired
	private Environment env;
	
	@Value("${capi.portal.dialect}")
	public String dataSourceDialect;
	
	@Value("${spring.datasource.url}")
	public String dataSourceUrl;
	
	@Value("${spring.datasource.username}")
	public String dataSourceUser;
	
	@Value("${spring.datasource.password}")
	public String dataSourcePwd;
	
	@Value("${spring.datasource.driver-class-name}")
	public String dataSourceDriver;
	
	@Bean
	public DataSource dataSource(){
		AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
		bean.setUniqueResourceName("AtomikosDataSource");
		bean.setXaDataSourceClassName(dataSourceDriver);
		Properties p = new Properties();
		p.setProperty("URL", dataSourceUrl);
		p.setProperty("password", dataSourcePwd);
		p.setProperty("user", dataSourceUser);
		bean.setXaProperties(p);
		bean.setMinPoolSize(1);
		bean.setMaxPoolSize(10);
		return bean;
		
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource ds){
		
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		Properties p = new Properties();
		
		entityManager.setDataSource(ds);
		entityManager.setPackagesToScan("com.dbs.capi.portal.forum.dao.entity");
		p.setProperty("hibernate.dialect", dataSourceDialect);
		p.setProperty("hibernate.show_sql", "true");
		
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		entityManager.setJpaVendorAdapter(adapter);
		entityManager.setJpaProperties(p);
		
		return entityManager;
		
	}
	
}
