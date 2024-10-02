package ar.unrn.tp.jpa.servicios;
import ar.unrn.tp.api.ClienteService;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;



import org.junit.jupiter.api.Test;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClienteServiceTest extends GenericoServiceTest {

    @Test
    public void crearCliente(){
        ClienteService clienteService = new JPAClienteService(this.emf);
        clienteService.crearCliente("Jonathan", "Leder", "39584452", "jleder@gmail.com");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.sos(cliente));
                }
        );
    }

    @Test
    public void modificarCliente(){
        ClienteService clienteService = new JPAClienteService(this.emf);
        clienteService.crearCliente("Jonathan", "Leder", "43217700", "jleder@gmail.com");
        clienteService.modificarCliente(1L, "Jonathan Adrian");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertEquals("Jonathan Adrian",cliente.nombre());

                }
        );
    }


    @Test
    public void agregarTarjeta(){
        ClienteService clienteService = new JPAClienteService(this.emf);
        clienteService.crearCliente("Jonathan", "Leder", "39584452", "jleder@gmail.com");
        clienteService.agregarTarjeta(1L, "2965 5154 1562","Patagonia");
        Tarjeta tarjetaPatagonia = new Tarjeta("Patagonia","2965 5154 1562");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.buscarTarjeta(tarjetaPatagonia));

                }
        );
    }

    @Test
    public void listarTarjetas(){
        this.emf = Persistence.createEntityManagerFactory("objectdb:TestingFile.tmp;drop");
        ClienteService clienteService = new JPAClienteService(this.emf);
        clienteService.crearCliente("Jonathan", "Leder", "39584452", "jleder@gmail.com");
        clienteService.agregarTarjeta(1L, "2965 5154 1562","Patagonia");
        List<Tarjeta> tarjetas = clienteService.listarTarjetas(1L);
        inTransactionExecute(
                (em) -> {
                    assertEquals(true,!tarjetas.isEmpty());
                }
        );
    }

}