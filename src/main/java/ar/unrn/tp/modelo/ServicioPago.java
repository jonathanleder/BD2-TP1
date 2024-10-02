package ar.unrn.tp.modelo;


public class ServicioPago {
    public boolean validarTarjeta(Tarjeta tarjeta) {
        return tarjeta.esValida();
    }
}