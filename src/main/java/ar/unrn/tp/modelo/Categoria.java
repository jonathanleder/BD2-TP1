package ar.unrn.tp.modelo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "Categoria")
public class Categoria{
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    public Categoria(String nombre){
        this.nombre = nombre;
    }

}