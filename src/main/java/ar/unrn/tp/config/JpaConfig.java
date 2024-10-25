package ar.unrn.tp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@Slf4j
@EnableTransactionManagement
public class JpaConfig {

    @Value("${app.db}")
    private String dbType;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        String persistenceUnitName = dbType.equals("objectdb") ? "jpa-objectdb" : "jpa-postgresql";
        log.info("CREATE Entity Manager Factory using persistence unit: " + persistenceUnitName);
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        log.info("CREATE Entity Manager");
        return entityManagerFactory.createEntityManager();
    }
}
