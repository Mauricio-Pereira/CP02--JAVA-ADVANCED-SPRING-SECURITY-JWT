package com.fiap.cp02.service;

import com.fiap.cp02.dto.curso.CursoResponse;
import com.fiap.cp02.dto.curso.CursoRequest;
import com.fiap.cp02.model.Curso;
import org.springframework.stereotype.Service;

@Service
public class CursoMapper {

    public Curso requestToCurso(CursoRequest request) {
        Curso curso = new Curso();
        curso.setNome(request.nome());
        curso.setTipo(request.tipo());
        return curso;
    }

    public CursoResponse cursoToResponse(Curso curso) {
        return new CursoResponse(curso.getId(), curso.getNome(), curso.getTipo());
    }
}
