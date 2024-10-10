package com.fiap.cp02.service;

import com.fiap.cp02.dto.diplomado.DiplomadoRequest;
import com.fiap.cp02.dto.diplomado.DiplomadoResponse;
import com.fiap.cp02.model.Diplomado;
import org.springframework.stereotype.Service;

@Service
public class DiplomadoMapper {

    public Diplomado requestToDiplomado(DiplomadoRequest diplomadoRequestDto) {
        Diplomado diplomado = new Diplomado();
        diplomado.setNome(diplomadoRequestDto.nome());
        diplomado.setNacionalidade(diplomadoRequestDto.nacionalidade());
        diplomado.setNaturalidade(diplomadoRequestDto.naturalidade());
        diplomado.setRg(diplomadoRequestDto.rg());
        return diplomado;
    }

    public DiplomadoResponse diplomadoToResponse(Diplomado diplomado) {
        return new DiplomadoResponse(
                diplomado.getId(),
                diplomado.getNome(),
                diplomado.getNacionalidade(),
                diplomado.getNaturalidade(),
                diplomado.getRg()
        );
    }


}
