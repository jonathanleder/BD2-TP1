package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.excepciones.ClienteInvalidoExcepcion;
import ar.unrn.tp.excepciones.EmailInvalidoExcepcion;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class JPAClienteService extends JPAGenericService implements ClienteService {

    public JPAClienteService(EntityManagerFactory emf) {
        super(emf); // Pasar el EntityManagerFactory al constructor de la clase padre
    }

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        System.out.println("Creando Cliente");
        inTransactionExecute((em) -> {
            try {
                em.persist(new Cliente(nombre, apellido, dni, email));
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
        List<Tarjeta> tarjetas = new ArrayList<>();
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            tarjetas.addAll(c.tarjetas());
        });
        return tarjetas;
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        inTransactionExecute((em) -> {
            Query q = em.createQuery("select c from Cliente c");
            clientes.addAll(q.getResultList());
        });
        return clientes;
    }

    @Override
    public void datosCliente(Long idCliente) {
        inTransactionExecute((em) -> {
            Cliente c = em.find(Cliente.class, idCliente);
            System.out.println("Nombre:" + c.nombre() + " id: " + c.id());
        });
    }
}
