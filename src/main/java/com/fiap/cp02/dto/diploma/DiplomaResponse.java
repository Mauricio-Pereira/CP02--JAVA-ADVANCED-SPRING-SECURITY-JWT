package com.fiap.cp02.dto.diploma;

import com.fiap.cp02.model.Diplomado;
import com.fiap.cp02.model.Sexo;

import java.util.Date;
import java.util.UUID;

public record DiplomaResponse(
        Long id,
        Diplomado diplomado,
        String curso,
        Date dataConclusao,
        Sexo sexoReitor,
        String nomeReitor
) {
}
