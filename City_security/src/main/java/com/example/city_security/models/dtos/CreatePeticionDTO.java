package com.example.city_security.models.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;


import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePeticionDTO {
    @NotBlank
    @NotNull
    private Date fecha_entrada;
    @NotBlank
    @NotNull
    private Date fecha_salida;
    private String dui_visitante;
    private String estado;
    @NotBlank
    @NotNull
    private String nombre_visitante;
    private String tipo_peticion;
    @NotBlank
    @NotNull
    private String correo;
    @Nullable
    private String razon;
}
