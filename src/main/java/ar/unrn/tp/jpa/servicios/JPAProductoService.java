package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Service
public class JPAProductoService extends JPAGenericService implements ProductoService {



    public JPAProductoService(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    public void crearProducto(String codigo, String descripcion, float precio, Long idCategoría, Long idMarca) {
        inTransactionExecute((em) -> {
            try {
                em.persist(new Producto(codigo, descripcion
                        , em.getReference(Categoria.class, idCategoría)
                        , em.getReference(Marca.class, idMarca), precio));
            } catch (ProductoInvalidoExcepcion e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void modificarProducto(Long idProducto, String descripcion, float precio) {
        inTransactionExecute((em) -> {
            try {
                Producto producto = em.getReference(Producto.class, idProducto);
                producto.actualizarDescripcion(descripcion);
                producto.actualizarPrecio(precio);
                em.persist(producto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        inTransactionExecute((em) -> {
            productos.addAll(em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList());
        });
        return productos;
    }
}
