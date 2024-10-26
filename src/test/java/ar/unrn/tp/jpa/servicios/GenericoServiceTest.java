package ar.unrn.tp.jpa.servicios;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.function.Consumer;

public class GenericoServiceTest {

    protected EntityManagerFactory emf;

    @BeforeEach
    protected void setUpEmf(){
        emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
    }

    public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            bloqueDeCodigo.accept(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen())
                em.close();
            if (emf != null)
                emf.close();
        }
    }

    @AfterEach
    public void tearDown() {
        emf.close();
    }

}