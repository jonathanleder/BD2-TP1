package ar.unrn.tp.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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