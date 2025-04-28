package com.application.rest.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_producto",nullable = false,unique = true)
    private String name;
    @Column(name = "precio")
    private BigDecimal price;//CUANDO QUEREMOS TRABAJAR CON UN ATRIBUTO QUE REPRESENTE DINERO CONVIENE USAR BIGDECIMAL POR SU PRECISION
    @ManyToOne
    @JoinColumn(name = "id_fabricante", nullable = false)
    private Maker maker;


}
