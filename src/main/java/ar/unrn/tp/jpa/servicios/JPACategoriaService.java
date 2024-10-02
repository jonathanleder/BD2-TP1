package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.modelo.Categoria;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
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
