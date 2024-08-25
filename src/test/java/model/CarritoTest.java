package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarritoTest {

//Inicializacion de variables a utilizar para los test
    Producto azucar = new Producto(321,"Azucar Local","Alimentos",1300.0,"Comarca");
    Producto yerba = new Producto(31,"Yerba Local","Alimentos",3000.0,"Comarca");
    Producto yerbaSuave = new Producto(231,"Yerba Suave","Alimentos",3200.0,"Comarca");
    Producto zapatillas = new Producto(512,"calzado importado","Ropa Deportiva",3000.0,"Gallo");

    TarjetaDeCredito comarca= new TarjetaDeCredito(54632987,"Ramon Perez","Comarca", LocalDateTime.now().plusYears(5));
    TarjetaDeCredito memeCard= new TarjetaDeCredito(54632988,"Ramon Perez","memeCard", LocalDateTime.now().plusYears(5));

    Cliente ramon= new Cliente("Ramon","Perez",54263590,"rperez@gmail.com",comarca);

    PromocionDeCompra promoCompra= new PromocionDeCompra(LocalDateTime.now().minusDays(5),LocalDateTime.now().minusDays(2),memeCard);
    PromocionDeProducto promoProducto= new PromocionDeProducto(LocalDateTime.now().minusDays(5),LocalDateTime.now().minusDays(1),"Comarca");
    Carrito unCarrito= new Carrito(ramon, comarca);

    PromocionDeCompra promoCompraVigente= new PromocionDeCompra(LocalDateTime.now(),LocalDateTime.now().plusDays(5),memeCard);
    PromocionDeProducto promoProductoVigente= new PromocionDeProducto(LocalDateTime.now(),LocalDateTime.now().plusDays(7),"Comarca");



    void setUp(){
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
        assertEquals(10500,venta.getMontoTotal());
    }

    @Test
    public void productoInvalido(){
        String excepcionEsperada="El precio debe ser un valor valido";
        Exception e= assertThrows(Exception.class, () ->{
            new Producto(
                    54632,"buen producto","una Categoria",-192.2,"Comarca"
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
            new PromocionDeCompra(
                    LocalDateTime.now(),LocalDateTime.now(),comarca
                    );
        } );
        assertEquals(Promocion.FECHA_INVALIDA, e.getMessage());
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
}