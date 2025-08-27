package vn.vdbc.wad.configuration;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "vn.vdbc.wad.repository",
        entityManagerFactoryRef = "wadEntityManagerFactory",
        transactionManagerRef = "wadTransactionManager"
)
public class WadDatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.wad")
    public DataSourceProperties wadDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "wadDataSource")
    @ConfigurationProperties("spring.datasource.wad.configuration")
    public DataSource wadDataSource(
            @Qualifier("wadDataSourceProperties") DataSourceProperties wadDataSourceProperties) {
        return wadDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "wadEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean wadEntityManagerFactory(
            @Qualifier("wadDataSource") DataSource dataSource) {

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("vn.vdbc.wad.model");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(jpaProperties);

        emf.setEntityManagerFactoryInterface(EntityManagerFactory.class);

        return emf;
    }

    @Bean(name = "wadTransactionManager")
    public PlatformTransactionManager wadTransactionManager(
            @Qualifier("wadEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}