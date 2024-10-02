package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.*;
import ar.unrn.tp.modelo.ServicioPago;
import ar.unrn.tp.modelo.Venta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class VentaServiceTest extends GenericoServiceTest{



    @Test
    public void realizarVenta() {
        VentaService ventaService = new JPAVentaService(new ServicioPago(), new JPADescuentoService(this.emf), this.emf);
        ProductoService productoService = new JPAProductoService(this.emf);
        CategoriaService categoriaService = new JPACategoriaService(this.emf);
        MarcaService marcaService = new JPAMarcaService(this.emf);
        DescuentoService descuentoService = new JPADescuentoService(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");


        productoService.crearProducto("152", "Es dinamita", 650F, 1L, 2L);
        productoService.crearProducto("155", "Minas submarinas", 950F, 1L, 2L);


        ClienteService clienteService = new JPAClienteService(this.emf);
        clienteService.crearCliente("Jonathan", "Leder", "39584452", "jaleder@gmail.com");
        clienteService.agregarTarjeta(5L, "1542 4561 2514", "Patagonia");
        descuentoService.crearDescuentoSobreTotal("Patagonia", LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 5F);
        descuentoService.crearDescuento("ACME", LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 5F);

        Long ventaId = ventaService.realizarVentaId(5L, List.of(1L, 2L), 6L);

        // Add a small delay to ensure persistence
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inTransactionExecute(
                (em) -> {

                    //Venta venta = em.find(Venta.class, ventaId);
                   List<Venta> ventas= new ArrayList<>();
                   ventas.addAll(em.createQuery("SELECT v from Venta v", Venta.class).getResultList());
                    System.out.println("cantidad de ventas: "+ventas.size());
                    Assertions.assertEquals(2, ventas.get(0).cantidadDeProductos());
                    Assertions.assertEquals(1368, ventas.get(0).montoTotal());
                }
        );
    }

    @Test
    public void calcularMonto(){
        VentaService ventaService = new JPAVentaService(new ServicioPago(), new JPADescuentoService(this.emf),this.emf);
        ProductoService productoService = new JPAProductoService(this.emf);
        CategoriaService categoriaService = new JPACategoriaService(this.emf);
        MarcaService marcaService = new JPAMarcaService(this.emf);
        DescuentoService descuentoService = new JPADescuentoService(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152","Es dinamita",650F,1L,2L);
        productoService.crearProducto("155","Minas antipersonales",950F,1L,2L);
        ClienteService clienteService = new JPAClienteService(this.emf);
        clienteService.crearCliente("Jonathan","Leder","42157849","jleder@gmail.com");
        clienteService.agregarTarjeta(5L,"1542 4561 2514","Patagonia");
        descuentoService.crearDescuentoSobreTotal("Patagonia", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        descuentoService.crearDescuento("ACME", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        inTransactionExecute(
                (em) -> {
                    Assertions.assertEquals(1368.0, ventaService.calcularMonto(List.of(3L,4L),6L));
                }
        );
    }

    @Test
    public void obtenerVentas(){
        VentaService ventaService = new JPAVentaService(new ServicioPago(), new JPADescuentoService(this.emf),this.emf);
        ProductoService productoService = new JPAProductoService(this.emf);
        CategoriaService categoriaService = new JPACategoriaService(this.emf);
        MarcaService marcaService = new JPAMarcaService(this.emf);
        DescuentoService descuentoService = new JPADescuentoService(this.emf);
        categoriaService.crearCategoria("Explosivos");
        marcaService.crearMarca("ACME");
        productoService.crearProducto("152","Es dinamita",650F,1L,2L);
        productoService.crearProducto("155","Minas antipersonales",950F,1L,2L);
        ClienteService clienteService = new JPAClienteService(this.emf);
        clienteService.crearCliente("Jonathan","Leder","39584452","jaleder@gmail.com");
        clienteService.agregarTarjeta(5L,"1542 4561 2514","Patagonia");
        descuentoService.crearDescuentoSobreTotal("Patagonia", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        descuentoService.crearDescuento("ACME", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5F);
        ventaService.realizarVenta(5L, List.of(3L,4L), 6L);
        inTransactionExecute(
                (em) -> {
                    Assertions.assertEquals(1, ventaService.ventas().size());
                }
        );
    }



}