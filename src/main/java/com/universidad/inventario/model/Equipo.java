package com.universidad.inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipos")
@Data

public class Equipo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Integer cantidadTotal;

    @Column(nullable = false)
    private Integer cantidadDisponible;


}


