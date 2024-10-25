package ar.unrn.tp.dto;


import lombok.Getter;

import java.util.List;

@Getter
public class VentaDTO {
    private Long cliente;
    private List<Long> productos;
    private Long tarjeta;


}
