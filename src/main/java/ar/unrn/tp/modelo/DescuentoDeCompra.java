package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.modelo.Descuento;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Table(name = "descuentodecompra")
@NoArgsConstructor
public class DescuentoDeCompra extends Descuento {




    public DescuentoDeCompra(LocalDate fechaInicio, LocalDate fechaFin, float porcentajeDeDescuento, String unaTarjeta) throws FechaInvalidaExcepcion {
        super(fechaInicio, fechaFin, porcentajeDeDescuento,unaTarjeta);

    }


    @Override
    public boolean tienePromo(String dato) {
        return this.descripcion.equals(dato);
    }

    @Override
    protected String marca() {
        return this.descripcion;
    }




}
