package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.modelo.Descuento;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

import java.util.Objects;


@Entity
@NoArgsConstructor
public class DescuentoDeProducto extends Descuento {


    private String marca;




    public DescuentoDeProducto(LocalDate fechaInicio, LocalDate fechaFin, float porcentajeDeDescuento, String unaMarca) throws FechaInvalidaExcepcion {
        super(fechaInicio, fechaFin, porcentajeDeDescuento);
        this.marca=Objects.requireNonNull(unaMarca);
    }


    @Override
    public boolean tienePromo(String dato) {
        return this.marca.equals(dato);
    }

    @Override
    protected String marca() {
        return this.marca;
    }




}
