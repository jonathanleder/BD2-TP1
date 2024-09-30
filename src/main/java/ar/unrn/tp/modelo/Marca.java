package ar.unrn.tp.modelo;

import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Marca {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    public Marca(String nombre) {
        this.nombre = nombre;
    }
    public String marca(){
        return this.nombre;
    }

}