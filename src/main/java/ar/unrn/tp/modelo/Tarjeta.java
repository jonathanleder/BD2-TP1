package ar.unrn.tp.modelo;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
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



    public String numeroDeTarjeta(){
        return this.numero;
    }

    public boolean esValida() {
        return this.fechaVencimiento.isAfter(LocalDateTime.now());
    }


    public String tipoDeTarjeta() {
        return this.tipoTarjeta;
    }


}
