package ar.unrn.tp.jpa.servicios;


import ar.unrn.tp.modelo.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.function.Consumer;


public abstract class JPAGenericService {

    protected EntityManagerFactory emf;


    public JPAGenericService(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {

        EntityManager em = this.emf.createEntityManager();
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
        }
    }


    public void tearDown() {
        if (this.emf.isOpen()) {
            this.emf.close();
        }
    }
}
