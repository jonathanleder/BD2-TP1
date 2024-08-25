package model;

public class ServicioPago {
    public static boolean validarTarjeta(Tarjeta tarjeta) {
        return tarjeta.esValida() && tarjeta.tieneFondos();
    }
}