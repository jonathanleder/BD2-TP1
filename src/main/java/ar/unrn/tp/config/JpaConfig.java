package ar.unrn.tp.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@EnableTransactionManagement
public class JpaConfig {

    @Value("${app.db}")
    String nameProvider;

    @Bean

    public EntityManagerFactory entityManagerFactory() {
        log.info("CREATE Entity Manager Factory");
        return Persistence.createEntityManagerFactory("jpa-objectdb");
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        log.info("CREATE Entity Manager");
        return entityManagerFactory.createEntityManager();
    }

//    @Bean
//    public LocalEntityManagerFactoryBean createEntityManagerFactory() {
//        log.info("CREATE Local Entity Manager");
//        LocalEntityManagerFactoryBean factory = new LocalEntityManagerFactoryBean();
//        factory.setPersistenceUnitName(nameProvider);
//        return factory;
//    }
}