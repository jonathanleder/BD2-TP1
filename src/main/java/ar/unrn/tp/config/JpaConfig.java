package ar.unrn.tp.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@EnableTransactionManagement
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.unrn.tp.modelo");
        factoryBean.setPersistenceUnitName("jpa-postgresql"); // Nombre del persistence-unit
        return factoryBean;
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        log.info("CREATE Entity Manager");
        return entityManagerFactory.createEntityManager();
    }
}
