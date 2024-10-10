package com.fiap.cp02.dto.curso;

import com.fiap.cp02.model.TipoCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CursoRequest(

        @NotBlank(message = "O nome do curso é obrigatório")
        String nome,
        @NotNull(message = "O tipo do curso é obrigatório")
        TipoCurso tipo
) {
}
