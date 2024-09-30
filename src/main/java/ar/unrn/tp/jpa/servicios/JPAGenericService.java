package ar.unrn.tp.jpa.servicios;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

public abstract class JPAGenericService {
    protected EntityManagerFactory emf;


    public JPAGenericService(EntityManagerFactory emf){
        this.emf = emf;
    }
    private void setUp(){
        //this.emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp");
    }

    public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
        this.setUp();
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
        this.emf.close();
    }
}
