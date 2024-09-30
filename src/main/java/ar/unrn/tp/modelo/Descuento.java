package ar.unrn.tp.modelo;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor

public abstract class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected LocalDate fechaInicio;
    protected LocalDate fechaFin;
    protected float descuento;

    public static String FECHA_INVALIDA="Las fechas no se pueden superponer o la fecha inicio no puede ser superior a la fecha de fin";

    public Descuento(LocalDate fechaInicio, LocalDate fechaFin, float descuentoEnPorcentaje){
        if(fechaInicio.isAfter(fechaFin)){
            throw new IllegalArgumentException(FECHA_INVALIDA);
        }
        if (fechaInicio.isEqual(fechaFin))
            throw new IllegalArgumentException(FECHA_INVALIDA);
        this.fechaInicio= Objects.requireNonNull(fechaInicio);
        this.fechaFin=Objects.requireNonNull(fechaFin);
        if(descuentoEnPorcentaje < 0 || descuentoEnPorcentaje > 100)
            throw new IllegalArgumentException("El valor del descuento no puede superar el 100% o ser menor a 1");
        this.descuento= (descuentoEnPorcentaje /100);
    }


    public abstract boolean tienePromo(String dato);

    public float aplicarDescuento(float precio){
        return precio-(precio*this.descuento);
    }


    public boolean estaVigente(){
        return LocalDate.now().isAfter(this.fechaInicio) && LocalDate.now().isBefore(this.fechaFin);
    }

    protected abstract String marca();

    protected LocalDate fechaDeInicio(){
        return fechaInicio;
    }
    protected LocalDate fechaDeFin(){
        return fechaFin;
    }

    public float descuento(){
        return descuento;
    }

    public boolean sosIgual(Descuento promocion){
        return this.marca().equals(promocion.marca()) &&
                this.fechaInicio.equals(promocion.fechaDeInicio()) &&
                this.fechaFin.equals((promocion.fechaDeFin())) &&
                this.descuento == promocion.descuento();
    }

    public Long id(){
        return this.id;
    }


}
