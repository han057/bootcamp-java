package com.example.demo.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank
    private String nombre;

    @Column
    @NotNull
    private String apellido;
}
