package ar.unrn.tp.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Marca")
public class Marca {


    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    public Marca(String nombre) {
        this.nombre = nombre;
    }

}