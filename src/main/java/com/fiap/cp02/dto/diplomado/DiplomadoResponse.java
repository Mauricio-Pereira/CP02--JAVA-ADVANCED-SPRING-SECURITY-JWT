package com.fiap.cp02.dto.diplomado;

import java.util.UUID;

public record DiplomadoResponse(
        Long id,
        String nome,
        String nacionalidade,
        String naturalidade,
        String rg
) {
}
