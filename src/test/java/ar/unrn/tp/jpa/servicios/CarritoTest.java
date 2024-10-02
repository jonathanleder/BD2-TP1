package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.modelo.*;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarritoTest {
/*
//Inicializacion de variables a utilizar para los test
    Producto azucar = new Producto("Azucar Local","Alimentos",1300.0,"Comarca");
    Producto yerba = new Producto("Yerba Local","Alimentos",3000.0,"Comarca");
    Producto yerbaSuave = new Producto("Yerba Suave","Alimentos",3200.0,"Comarca");
    Producto zapatillas = new Producto("calzado importado","Ropa Deportiva",3000.0,"Gallo");

    Tarjeta comarca= new Tarjeta("54632987","Ramon Perez","Comarca", LocalDateTime.now().plusYears(5));
    Tarjeta memeCard= new Tarjeta("54632988","Ramon Perez","memeCard", LocalDateTime.now().plusYears(5));

    Cliente ramon= new Cliente("Ramon","Perez","54263590","rperez@gmail.com");


    DescuentoDeCompra promoCompra= new DescuentoDeCompra(LocalDateTime.now().minusDays(5),LocalDateTime.now().minusDays(2),8,"memeCard");
    DescuentoDeProducto promoProducto= new DescuentoDeProducto(LocalDateTime.now().minusDays(5),LocalDateTime.now().minusDays(1),5,"Comarca");


    DescuentoDeCompra promoCompraVigente= new DescuentoDeCompra(LocalDateTime.now(),LocalDateTime.now().plusDays(5),8,"memeCard");
    DescuentoDeProducto promoProductoVigente= new DescuentoDeProducto(LocalDateTime.now(),LocalDateTime.now().plusDays(7),5,"Comarca");



    void setUp(){

        ramon.agregarTarjeta(comarca);
        Carrito unCarrito= new Carrito(ramon, comarca);
        unCarrito.agregarPromoDeProducto(promoProducto);
        unCarrito.agregarPromoDeCompra(promoCompra);
        unCarrito.agregarAlCarrito(azucar);
        unCarrito.agregarAlCarrito(yerba);
        unCarrito.agregarAlCarrito(yerbaSuave);
        unCarrito.agregarAlCarrito(zapatillas);
    }
    void setUpConPromoVigente(){
        unCarrito.agregarPromoDeProducto(promoProductoVigente);
        unCarrito.agregarPromoDeCompra(promoCompraVigente);
        unCarrito.agregarAlCarrito(azucar);
        unCarrito.agregarAlCarrito(yerba);
        unCarrito.agregarAlCarrito(yerbaSuave);
        unCarrito.agregarAlCarrito(zapatillas);
    }

    @Test
    void calcularMontoConPromosCaducadas() {
        setUp();
        var resultado=unCarrito.calcularMontoConPromos();
        assertEquals(10500,resultado);
        //Modificar para que sea sin promos pero con descuentos caducados
    }

    @Test
    void calcularMontoConPromos() {
        setUpConPromoVigente();
        var resultado=unCarrito.calcularMontoConPromos();
        assertEquals(9315,resultado);
    }
    @Test
    void calcularMontoConPromoDeCompra(){
        setUpConPromoVigente();
        var resultado=unCarrito.calcularMontoConPromoDeCompra();
        assertEquals(9660,resultado);
    }
    @Test
    void calcularMontoConPromoDeProducto(){
        setUpConPromoVigente();
        var resultadoEsperado= unCarrito.calcularMontoConPromoDeProducto();
        assertEquals(10125,resultadoEsperado);
    }

    @Test
    void realizarPago() {
        setUpConPromoVigente();

        var venta = unCarrito.realizarPago();


        assertEquals(10125,venta.getMontoTotal());
    }

    @Test
    public void productoInvalido(){
        String excepcionEsperada="El precio debe ser un valor valido";
        Exception e= assertThrows(Exception.class, () ->{
            new Producto(
                    "buen producto","una Categoria",-192.2,"Comarca"
            );
        } );
        assertEquals(excepcionEsperada, e.getMessage());
    }
    //Verificar que no sea posible crear un Producto sin categoría,descripción y precio.
    @Test
    public void clienteInvalido(){
        Exception e= assertThrows(Exception.class, () ->{
            new Cliente(
                    "Juan","Perez",-123,"jperez@gmail.com",memeCard
                    );
        } );
        assertEquals(Cliente.INVALID_DNI, e.getMessage());
    }
    //Verificar que no sea posible crear un Cliente sin dni, nombre y apellido. Y que el email sea válido.

    @Test
    public void promoInvalida(){
        Exception e= assertThrows(Exception.class, () ->{
            new DescuentoDeCompra(
                    LocalDateTime.now(),LocalDateTime.now(),5,"comarca"
                    );
        } );
        assertEquals(Descuento.FECHA_INVALIDA, e.getMessage());
    }

    //Verificar que no sea posible crear un descuento con fechas validez superpuestas.
    //Modelo para testear excepciones
   /* @Test
    public void userNameIsInvalid() {
        Exception e = assertThrows(UsersException.class, () -> {
            new User(
                    "Enrique", "Molinari",
                    "enrique.molinari@gmail.com",
                    "", "Ab138RtoUjkL", "Ab138RtoUjkL");
        });
        assertEquals(User.INVALID_USERNAME, e.getMessage());
    }*/
    */
}