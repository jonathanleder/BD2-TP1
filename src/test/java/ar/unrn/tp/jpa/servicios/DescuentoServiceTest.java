package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.jpa.servicios.JPADescuentoService;
import ar.unrn.tp.modelo.DescuentoDeCompra;
import ar.unrn.tp.modelo.DescuentoDeProducto;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DescuentoServiceTest extends GenericoServiceTest{

    @Test
    public void crearDescuentoSobreTotal(){
        DescuentoService descuentoService = new JPADescuentoService(this.emf);
        descuentoService.crearDescuentoSobreTotal("Patagonia", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5);
        try {
            DescuentoDeCompra promocionResultado = new DescuentoDeCompra(LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F,"Patagonia");
            inTransactionExecute(
                    (em) -> {
                        DescuentoDeCompra descuento = em.find(DescuentoDeCompra.class, 1L);
                        assertTrue(descuento.sosIgual(promocionResultado));

                    }
            );
        } catch (FechaInvalidaExcepcion e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void crearDescuento(){
        DescuentoService descuentoService = new JPADescuentoService(this.emf);
        descuentoService.crearDescuento("ACME", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5);
        try {
            DescuentoDeProducto promocionResultado = new DescuentoDeProducto(LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F,"ACME");
            inTransactionExecute(
                    (em) -> {
                        DescuentoDeProducto descuento = em.find(DescuentoDeProducto.class, 1L);
                        assertTrue(descuento.sosIgual(promocionResultado));

                    }
            );
        } catch (FechaInvalidaExcepcion e) {
            throw new RuntimeException(e);
        }
    }

}