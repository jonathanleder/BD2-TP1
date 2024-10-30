package ar.unrn.tp.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Tarjeta")
public class Tarjeta {

    @Id
    @GeneratedValue
    private Long id;

    private String numero;
    private String tipoTarjeta;
    private LocalDateTime fechaVencimiento;

    public Tarjeta(String tipoTarjeta, String numero) {
        this.numero= Objects.requireNonNull(numero);
        this.tipoTarjeta=Objects.requireNonNull(tipoTarjeta);
        this.fechaVencimiento=LocalDateTime.now().plusYears(1);

    }


    public boolean esValida() {
        return this.fechaVencimiento.isAfter(LocalDateTime.now());
    }



    @Override
    public String toString() {
        return ("Tarjeta: \n"+"Tipo/marca: "+this.tipoTarjeta +"\nNumero: "+ this.getNumero());
    }


}
