package ar.unrn.tp.jpa.servicios;


import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import ar.unrn.tp.jpa.servicios.JPACategoriaService;
import ar.unrn.tp.jpa.servicios.JPADescuentoService;
import ar.unrn.tp.jpa.servicios.JPAMarcaService;
import ar.unrn.tp.jpa.servicios.JPAProductoService;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Descuento;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProductoServiceTest extends GenericoServiceTest{

    @Test
    public void crearProducto(){
        ProductoService productoService = new JPAProductoService(this.emf);
        CategoriaService categoriaService = new JPACategoriaService(this.emf);
        MarcaService marcaService = new JPAMarcaService(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152","Es dinamita",650F,1L,2L);
        inTransactionExecute(
                (em) -> {
                    Marca marca = em.getReference(Marca.class,2L);
                    Categoria categoria = em.getReference(Categoria.class,1L);
                    try {
                        Producto productoResultado = new Producto("152","Es dinamita",categoria,marca,650F);
                        Producto producto = em.find(Producto.class, 3L);
                        assertTrue(producto.esIgualA(productoResultado));
                    } catch (ProductoInvalidoExcepcion e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Test
    public void modificarProducto(){
        ProductoService productoService = new JPAProductoService(this.emf);
        CategoriaService categoriaService = new JPACategoriaService(this.emf);
        MarcaService marcaService = new JPAMarcaService(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152","explosivo C4",650F,1L,2L);
        productoService.modificarProducto(3L,"Es dinamita",650F);
        inTransactionExecute(
                (em) -> {
                    Marca marca = em.getReference(Marca.class,2L);
                    Categoria categoria = em.getReference(Categoria.class,1L);
                    try {
                        Producto productoResultado = new Producto("152","Es dinamita",categoria,marca,650F);
                        Producto producto = em.find(Producto.class, 3L);
                        assertTrue(producto.esIgualA(productoResultado));
                    } catch (ProductoInvalidoExcepcion e) {
                        throw new RuntimeException(e);
                    }
                }
        );

    }

    @Test
    public void ListarProductos() {
        ProductoService productoService = new JPAProductoService(this.emf);
        CategoriaService categoriaService = new JPACategoriaService(this.emf);
        MarcaService marcaService = new JPAMarcaService(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152", "explosivo C4", 650F, 1L, 2L);
        productoService.crearProducto("159", "Ford Focus", 900F, 1L, 2L);

        List<Producto> productos = productoService.listarProductos();


        assertEquals(2, productos.size());


    }

}
