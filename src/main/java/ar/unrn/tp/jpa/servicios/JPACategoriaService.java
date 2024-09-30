package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.modelo.Categoria;

import javax.persistence.EntityManagerFactory;

public class JPACategoriaService extends JPAGenericService implements CategoriaService {


    public JPACategoriaService(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    public void crearCategoria(String nombre) {
        inTransactionExecute((em) -> {
            em.persist(new Categoria(nombre));
        });
    }
}
