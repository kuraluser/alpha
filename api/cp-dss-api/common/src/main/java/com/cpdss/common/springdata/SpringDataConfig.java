/* Licensed at AlphaOri Technologies */
package com.cpdss.common.springdata;

import com.cpdss.common.exception.SpringDataInitException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA configuration params
 *
 * @author r.krishnakumar
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@DependsOn({"log", "cache"})
public class SpringDataConfig {

  @Value("${ro.db.host}")
  private String host;

  @Value("${ro.db.database}")
  private String databaseName;

  @Value("${ro.db.username}")
  private String userName;

  @Value("${ro.db.password}")
  private String password;

  @Value("${ro.db.entity.package}")
  private String entityPackageName;

  @Value("${ro.db.showSQL:false}")
  private boolean showSQL;

  @Value("${ro.db.auto.generate:false}")
  private boolean autoGenerate;

  private static final String STAGE_ENTITY_PACKAGE_NAME = "com.cpdss.common.communication.entity";

  @Autowired
  @Qualifier("multitenancy")
  private boolean isMultenant;

  @Autowired private String dataBaseType;

  /** Spring data initialisation validations */
  @PostConstruct
  public void initialise() {
    if (dataBaseType.equals("mysql") && isMultenant) {
      throw new SpringDataInitException("Multitenancy is not supported with MySQL");
    }
  }

  /**
   * Datasource with connection pooling
   *
   * @return
   */
  @Bean
  public DataSource dataSource() {

    if (propertyHasTrailingSpaces(host)) {
      throw new RuntimeException("DB Host value has trailing white space");
    }
    if (propertyHasTrailingSpaces(userName)) {
      throw new RuntimeException("DB Username value has trailing white space");
    }
    if (propertyHasTrailingSpaces(password)) {
      throw new RuntimeException("DB password value has trailing white space");
    }
    if (propertyHasTrailingSpaces(entityPackageName)) {
      throw new RuntimeException("Entity Package Name value has trailing white space");
    }
    try {
      HikariConfig config = new HikariConfig();
      if (dataBaseType.equals("mysql")) {
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://" + host + ":3306/" + databaseName);
      } else if (dataBaseType.equals("postgres")) {
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://" + host + ":5432/" + databaseName);
      }
      config.addDataSourceProperty("useSSL", false);
      config.setMaximumPoolSize(30);
      config.setUsername(userName);
      config.setPassword(password);
      config.addDataSourceProperty("cachePrepStmts", true);
      config.addDataSourceProperty("prepStmtCacheSize", 250);
      config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
      config.addDataSourceProperty("useServerPrepStmts", true);
      config.addDataSourceProperty("maxLifetime", 30000);
      return new HikariDataSource(config);
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialise datasource");
    }
  }

  private boolean propertyHasTrailingSpaces(String property) {
    Character lastCharacter = property.charAt(property.length() - 1);
    return Character.isWhitespace(lastCharacter);
  }

  /**
   * Multitenant connection provider
   *
   * @return
   */
  @Bean
  @ConditionalOnBean(name = "multitenancy")
  public MultiTenantConnectionProvider multiTenantConnectionProvider(DataSource dataSource) {
    return new TenantConnectionProvider(dataSource);
  }

  /**
   * Multitenant schema resolver
   *
   * @return
   */
  @Bean
  @ConditionalOnBean(name = "multitenancy")
  public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
    return new TenantSchemaResolver();
  }

  /**
   * Entity manager factory bean creation
   *
   * @throws SQLException
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      DataSource dataSource,
      @Autowired(required = false) MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
      @Autowired(required = false)
          CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl)
      throws SQLException {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    if (this.autoGenerate) {
      vendorAdapter.setGenerateDdl(true);
    }
    if (this.isMultenant && dataBaseType.equals("postgres")) {
      Map<String, Object> jpaPropertiesMap = new HashMap<>();
      jpaPropertiesMap.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
      jpaPropertiesMap.put(
          Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
      jpaPropertiesMap.put(
          Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);
      factory.setJpaPropertyMap(jpaPropertiesMap);
    }
    Properties jpaProperties = new Properties();
    if (this.showSQL) {
      vendorAdapter.setShowSql(true);
      jpaProperties.put("hibernate.format_sql", true);
    }
    factory.setJpaProperties(jpaProperties);
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setDataSource(dataSource);
    factory.setPackagesToScan(entityPackageName, STAGE_ENTITY_PACKAGE_NAME);
    factory.setPersistenceUnitName("ricoPersistence");
    return factory;
  }

  /** JPA transaction manager bean creation */
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }

  /**
   * Auditor Provider bean.
   *
   * <p>Do not modify this method
   *
   * @return
   */
  @Bean
  public AuditorAware<String> auditorProvider() {
    return new AuditorAwareImpl();
  }
}
