package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.modelo.Descuento;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
public class DescuentoDeCompra extends Descuento {


    private String tardejaDeDescuento;



    public DescuentoDeCompra(LocalDate fechaInicio, LocalDate fechaFin, float porcentajeDeDescuento, String unaTarjeta) throws FechaInvalidaExcepcion {
        super(fechaInicio, fechaFin, porcentajeDeDescuento);
        this.tardejaDeDescuento= unaTarjeta;
    }


    @Override
    public boolean tienePromo(String dato) {
        return dato.equals(this.tardejaDeDescuento);
    }

    @Override
    protected String marca() {
        return this.tardejaDeDescuento;
    }




}
