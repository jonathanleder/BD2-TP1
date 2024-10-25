package ar.unrn.tp.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Marca {


    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    public Marca(String nombre) {
        this.nombre = nombre;
    }

}