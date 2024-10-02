package ar.unrn.tp.modelo;

import org.springframework.stereotype.Service;

@Service
public class ServicioPago {
    public boolean validarTarjeta(Tarjeta tarjeta) {
        return tarjeta.esValida();
    }
}