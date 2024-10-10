package com.fiap.cp02.dto.diploma;

import com.fiap.cp02.model.Curso;
import com.fiap.cp02.model.Sexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;

public record DiplomaRequest(
        @NotNull(message = "O ID do diplomado é obrigatório")
        Long diplomadoId,
        @NotNull(message = "O ID do curso é obrigatório")
        Long cursoId,
        @NotNull(message = "A data de conclusão é obrigatória")
        @PastOrPresent(message = "A data de conclusão deve ser no passado ou presente")
        Date dataConclusao,
        @NotNull(message = "O sexo do reitor é obrigatório")
        Sexo sexoReitor,
        @NotBlank(message = "O nome do reitor é obrigatório")
        String nomeReitor
) {
}