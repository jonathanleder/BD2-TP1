package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.modelo.Descuento;
import ar.unrn.tp.modelo.DescuentoDeCompra;
import ar.unrn.tp.modelo.DescuentoDeProducto;
import ar.unrn.tp.modelo.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JPADescuentoService extends JPAGenericService implements DescuentoService {



    @Autowired
    public JPADescuentoService(EntityManagerFactory emf) {
       super(emf);
    }

    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        inTransactionExecute((em) -> {
            try {
                em.persist(new DescuentoDeCompra(fechaDesde,fechaHasta,porcentaje,marcaTarjeta));
            } catch (FechaInvalidaExcepcion e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        inTransactionExecute((em) -> {
            try {
                em.persist(new DescuentoDeProducto(fechaDesde,fechaHasta,porcentaje,marcaProducto));
            } catch (FechaInvalidaExcepcion e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Descuento> recuperarDescuentos() {
        List<Descuento> descuentos = new ArrayList<>();
        inTransactionExecute((em) -> {
            List<Descuento> promocionesDeCompra = em.createQuery("SELECT p FROM DescuentoDeCompra p", Descuento.class).getResultList();
            List<Descuento> promocionesDeProducto = em.createQuery("SELECT p FROM DescuentoDeProducto p", Descuento.class).getResultList();

            if (!promocionesDeCompra.isEmpty()) {
                descuentos.addAll(promocionesDeCompra);
            }

            if (!promocionesDeProducto.isEmpty()) {
                descuentos.addAll(promocionesDeProducto);
            }
        });
        return descuentos;

    }

    @Override
    public void eliminarTodosLosDescuentos() {
        EntityManager em = emf.createEntityManager();

        try {
            // Iniciar la transacción
            em.getTransaction().begin();

            // Consulta para obtener todos los productos
            List<Descuento> descuentos = em.createQuery("SELECT d FROM Descuento d", Descuento.class)
                    .getResultList();

            // Eliminar cada producto
            for (Descuento descuento : descuentos) {
                em.remove(descuento);
            }

            // Confirmar la transacción
            em.getTransaction().commit();
        } catch (Exception e) {
            // Si ocurre un error, revertir la transacción
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerrar el EntityManager
            em.close();
        }
    }
}

