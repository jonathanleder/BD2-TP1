package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Categoria> listarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        inTransactionExecute((em) -> {
            Query q = em.createQuery("select c from Categoria c");
            categorias.addAll(q.getResultList());
        });
        return categorias;
    }

}
