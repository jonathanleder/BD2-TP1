package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.modelo.Marca;

import javax.persistence.EntityManagerFactory;

public class JPAMarcaService extends JPAGenericService implements MarcaService {

    public JPAMarcaService(EntityManagerFactory emf) {
        super(emf);
    }


    @Override
    public void crearMarca(String nombre) {
        inTransactionExecute((em) -> {
            try {
                em.persist(new Marca(nombre));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
