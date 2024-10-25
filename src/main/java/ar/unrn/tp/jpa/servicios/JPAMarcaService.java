package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
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

    @Override
    public List<Marca> listarMarcas() {
        List<Marca> marcas = new ArrayList<>();
        inTransactionExecute((em) -> {
            Query q = em.createQuery("select m from Marca m");
            marcas.addAll(q.getResultList());
        });
        return marcas;
    }
}
