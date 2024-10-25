package ar.unrn.tp.modelo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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