package ar.unrn.tp.main;

import ar.unrn.tp.api.*;
import ar.unrn.tp.jpa.servicios.*;
import ar.unrn.tp.modelo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
/*
         try {
            MarcaService marcaService = new JPAMarcaService();
            marcaService.crearMarca("ACME");

            ClienteService clienteService = new JPAClienteService();
            clienteService.crearCliente("Jonathan", "Leder", "39584452", "jaleder@gmail.com");
            clienteService.crearCliente("Jonathan2", "Leder", "39584452", "jaleder@gmail.com");
           clienteService.agregarTarjeta((long) 2,"2920-5121-2524","MemeCard");
            CategoriaService categoriaService = new JPACategoriaService();

            categoriaService.crearCategoria("DEPORTIVA");
            categoriaService.crearCategoria("EXPLOSIVOS");

            ProductoService productoService = new JPAProductoService();

            productoService.crearProducto("1251", "Es dinamita", 250.0F, (long)5, (long)1);
            productoService.crearProducto("1252", "Son minas terrestres antipersonales", 500.0F, (long)5, (long)1);

            DescuentoService descuentoService = new JPADescuentoService();

            descuentoService.crearDescuentoSobreTotal("MemeCard", LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5);

            descuentoService.crearDescuento("ACME",LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5);

            VentaService ventaService = new JPAVentaService(new ServicioPago(),descuentoService);
            ventaService.realizarVenta((long) 2, List.of((long) 7,(long) 7), (long)4);

            Marca comarca = new Marca("Comarca");
            Marca acme = new Marca("Acme");

            Categoria EXPLOSIVOS = new Categoria("Explosivos");
            Categoria DEPORTIVA = new Categoria("Desportes");

            Producto dinamita = new Producto("1251", "Es dinamita", EXPLOSIVOS, acme, 250F);
            Producto ropa = new Producto("1350", "Es ropa deportiva", DEPORTIVA, comarca, 500F);

            ArrayList<Producto> productos = new ArrayList<Producto>();
            productos.add(dinamita);
            productos.add(ropa);

            DescuentoDeProducto promocionActivaAcme = new DescuentoDeProducto(LocalDate.now().minusDays(5),LocalDate.now().plusDays(5),5,acme.marca());

            ArrayList<Descuento> promociones = new ArrayList<Descuento>();
            promociones.add(promocionActivaAcme);

            ServicioPago tarjetaServicio = new ServicioPago();

            Carrito miCarrito = new Carrito(productos,promociones,tarjetaServicio);

            System.out.println(miCarrito.calcularMontoConPromos());

            Venta miCompra = miCarrito.realizarPago();

        }catch (Exception e){
            e.printStackTrace();
        }

*/
    }
}