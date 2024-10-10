package com.fiap.cp02.dto.curso;

import com.fiap.cp02.model.TipoCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CursoResponse(
        Long id,
        String nome,
        TipoCurso tipo
){
}
