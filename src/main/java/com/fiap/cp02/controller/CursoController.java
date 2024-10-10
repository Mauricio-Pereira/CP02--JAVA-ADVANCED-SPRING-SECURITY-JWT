package com.fiap.cp02.controller;

import com.fiap.cp02.dto.curso.CursoRequest;
import com.fiap.cp02.dto.curso.CursoResponse;
import com.fiap.cp02.model.Curso;
import com.fiap.cp02.repository.CursoRepository;
import com.fiap.cp02.service.CursoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {


    @Autowired
    private CursoMapper cursoMapper;
    @Autowired
    private CursoRepository cursoRepository;
    private Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").descending());




    @Operation(summary = "Cria um curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos", content = @Content(schema = @Schema()))})
    @PostMapping
    public ResponseEntity<CursoResponse> createCurso(
            @Valid @RequestBody CursoRequest cursoRequest) {
        Curso cursoConvertido = cursoMapper.requestToCurso(cursoRequest);
        Curso cursoCriado = cursoRepository.save(cursoConvertido);

        CursoResponse cursoResponse = cursoMapper.cursoToResponse(cursoCriado);
        return new ResponseEntity<>(cursoResponse, HttpStatus.CREATED);
    }


    @Operation(summary = "Busca todos os cursos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cursos encontrados"),
            @ApiResponse(responseCode = "404", description = "Cursos não encontrados", content = @Content(schema = @Schema()))})
    @GetMapping
    public ResponseEntity<List<CursoResponse>> readAllCursos() {
        Page<Curso> cursos = cursoRepository.findAll(pageable);
        if (cursos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CursoResponse> cursoResponses = new ArrayList<>();
        for (Curso curso : cursos) {
            cursoResponses.add(cursoMapper.cursoToResponse(curso));
        }

        return new ResponseEntity<>(cursoResponses, HttpStatus.OK);
    }


    @Operation(summary = "Busca um curso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content(schema = @Schema()))})
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> readCursoById(@PathVariable Long id) {
        Optional<Curso> cursoSalvo = cursoRepository.findById(id);
        if (cursoSalvo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CursoResponse cursoResponse = cursoMapper.cursoToResponse(cursoSalvo.get());
        return new ResponseEntity<>(cursoResponse, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content(schema = @Schema()))})
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> updateCurso(@PathVariable Long id, @Valid @RequestBody CursoRequest cursoRequest) {
        Optional<Curso> cursoSalvo = cursoRepository.findById(id);
        if (cursoSalvo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Curso cursoConvertido = cursoMapper.requestToCurso(cursoRequest);
        cursoConvertido.setId(id);
        Curso cursoAtualizado = cursoRepository.save(cursoConvertido);
        CursoResponse cursoResponse = cursoMapper.cursoToResponse(cursoAtualizado);
        return new ResponseEntity<>(cursoResponse, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado", content = @Content(schema = @Schema()))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        Optional<Curso> cursoSalvo = cursoRepository.findById(id);
        if (cursoSalvo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cursoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
