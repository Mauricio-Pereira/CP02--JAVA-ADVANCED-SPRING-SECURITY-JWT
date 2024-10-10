package com.fiap.cp02.controller;


import com.fiap.cp02.dto.diplomado.DiplomadoRequest;
import com.fiap.cp02.dto.diplomado.DiplomadoResponse;
import com.fiap.cp02.model.Diplomado;
import com.fiap.cp02.repository.DiplomadoRepository;
import com.fiap.cp02.service.DiplomadoMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Path;
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
@RequestMapping("/diplomados")
public class DiplomadoController {

    @Autowired
    private DiplomadoMapper diplomadoMapper;

    @Autowired
    private DiplomadoRepository diplomadoRepository;

    private Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").descending());

    @Operation(summary = "Cria um diplomado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Diplomado criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos", content = @Content(schema = @Schema()))})
    @PostMapping
    public ResponseEntity<DiplomadoResponse> createDiplomado(
            @Valid @RequestBody DiplomadoRequest diplomadoRequest) {
        Diplomado diplomadoConvertido = diplomadoMapper.requestToDiplomado(diplomadoRequest);
        Diplomado diplomadoCriado = diplomadoRepository.save(diplomadoConvertido);

        DiplomadoResponse diplomadoResponse = diplomadoMapper.diplomadoToResponse(diplomadoCriado);
        return new ResponseEntity<>(diplomadoResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca todos os diplomados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diplomados encontrados"),
            @ApiResponse(responseCode = "404", description = "Nenhum diplomado encontrado", content = @Content(schema = @Schema()))})
    @GetMapping
    public ResponseEntity<List<DiplomadoResponse>> readAllDiplomados(){
        Page<Diplomado> diplomados = diplomadoRepository.findAll(pageable);
        if (diplomados.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<DiplomadoResponse> diplomadoResponses = new ArrayList<>();

        for (Diplomado diplomado : diplomados) {
            diplomadoResponses.add(diplomadoMapper.diplomadoToResponse(diplomado));
        }
        return new ResponseEntity<>(diplomadoResponses, HttpStatus.OK);
    }

    @Operation(summary = "Busca um diplomado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diplomado encontrado"),
            @ApiResponse(responseCode = "404", description = "Diplomado não encontrado", content = @Content(schema = @Schema()))})
    @GetMapping("/{id}")
    public ResponseEntity<DiplomadoResponse> readDiplomadoById(@PathVariable Long id){
        Optional<Diplomado> diplomadoSalvo = diplomadoRepository.findById(id);
        if (diplomadoSalvo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DiplomadoResponse diplomadoResponse = diplomadoMapper.diplomadoToResponse(diplomadoSalvo.get());
        return new ResponseEntity<>(diplomadoResponse, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um diplomado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diplomado atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Diplomado não encontrado", content = @Content(schema = @Schema()))})
    @PutMapping("/{id}")
    public ResponseEntity<DiplomadoResponse> updateDiplomado(@PathVariable Long id, @Valid @RequestBody DiplomadoRequest diplomadoRequest){
        Optional<Diplomado> diplomadoSalvo = diplomadoRepository.findById(id);
        if (diplomadoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Diplomado diplomadoConvertido = diplomadoMapper.requestToDiplomado(diplomadoRequest);
        diplomadoConvertido.setId(id);
        Diplomado diplomadoAtualizado = diplomadoRepository.save(diplomadoConvertido);
        DiplomadoResponse diplomadoResponse = diplomadoMapper.diplomadoToResponse(diplomadoAtualizado);
        return new ResponseEntity<>(diplomadoResponse, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um diplomado por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diplomado deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Diplomado não encontrado", content = @Content(schema = @Schema()))})
    @DeleteMapping("/{id}")
    public ResponseEntity<DiplomadoResponse> deleteDiplomado(@PathVariable Long id){
        Optional<Diplomado> diplomado = diplomadoRepository.findById(id);
        if (diplomado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        diplomadoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
