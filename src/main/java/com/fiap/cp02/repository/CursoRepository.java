package com.fiap.cp02.repository;

import com.fiap.cp02.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);
}
