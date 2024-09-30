package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.excepciones.ClienteInvalidoExcepcion;
import ar.unrn.tp.excepciones.EmailInvalidoExcepcion;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class JPAClienteService extends JPAGenericService implements ClienteService {


    public JPAClienteService(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        inTransactionExecute((em) -> {
            try {
                em.persist(new Cliente(nombre,apellido,dni,email));
            } catch (ClienteInvalidoExcepcion | EmailInvalidoExcepcion e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public void modificarCliente(Long idCliente, String nombre) {
        inTransactionExecute((em) -> {
            try {
                Cliente cliente = em.getReference(Cliente.class, idCliente);
                cliente.actualizarNombre(nombre);
                em.persist(cliente);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String marca) {
        inTransactionExecute((em) -> {
            try {
                Cliente cliente = em.getReference(Cliente.class, idCliente);
                cliente.agregarTarjeta(new Tarjeta(marca, nro));
                em.persist(cliente);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Tarjeta> listarTarjetas(Long idCliente) {
        List<Tarjeta> tarjetas = new ArrayList<Tarjeta>();
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            tarjetas.addAll(c.tarjetas());
        });
        return tarjetas;
    }

    @Override
    public void datosCliente(Long idCliente) {
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            System.out.println(
                    "Nombre:"+c.nombre()+"id: "+c.id()
            );
        });
    }
}
