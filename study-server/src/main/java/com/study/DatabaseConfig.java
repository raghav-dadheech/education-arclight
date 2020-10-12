package com.study;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Value("${db.driver}")
	private String driverClassName;

	@Value("${db.url}")
	private String dbUrl;

	@Value("${db.username}")
	private String dbUsername;

	@Value("${db.password}")
	private String dbPassword;

	@Value("${hibernate.dialect}")
	private String hibernateDialect;

	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;

	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateHbm2ddlAuto;

	@Value("${entitymanager.packagesToScan}")
	private String packageToScan;

	@Value("${hibernate.format_sql}")
	private String hibernateFormatsql;

	  @Bean
	  public DataSource dataSource() {
	    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(driverClassName);
	    dataSource.setUrl(dbUrl);
	    dataSource.setUsername(dbUsername);
	    dataSource.setPassword(dbPassword);

	    return dataSource;
	  }

	  @Bean
	  public Properties getHibernateProperties(){
	      final Properties properties = new Properties();
	      properties.put("hibernate.connection.driver_class", driverClassName);

	      properties.put("hibernate.dialect",  hibernateDialect);
	      properties.put("hibernate.show_sql", hibernateShowSql);
	      properties.put("hibernate.format_sql", hibernateFormatsql);
	      properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
	      properties.put("hibernate.jdbc.lob.non_contextual_creation", true);
	      properties.put("hibernate.connection.pool_size", 100);

	      return properties;
	  }

	  @Bean(name = "sessionFactory")
	  public LocalSessionFactoryBean sessionFactory() {
	    final LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	    sessionFactoryBean.setDataSource(dataSource());
	    sessionFactoryBean.setPackagesToScan(packageToScan);
	    sessionFactoryBean.setHibernateProperties(getHibernateProperties());

	    return sessionFactoryBean;
	  }

	  @Bean
	  public HibernateTransactionManager transactionManager() {
	    final HibernateTransactionManager transactionManager =
	        new HibernateTransactionManager();
	    transactionManager.setSessionFactory(sessionFactory().getObject());
	    return transactionManager;
	  }

}
