package com.fiap.cp02.controller;
import com.fiap.cp02.dto.diploma.DiplomaRequest;
import com.fiap.cp02.dto.diploma.DiplomaResponse;
import com.fiap.cp02.dto.diploma.DiplomaTextResponse;
import com.fiap.cp02.model.Diploma;
import com.fiap.cp02.repository.DiplomaRepository;
import com.fiap.cp02.service.DiplomaMapper;
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
@RequestMapping("/diplomas")
public class DiplomaController {

    @Autowired
    private DiplomaMapper diplomaMapper;
    @Autowired
    private DiplomaRepository diplomaRepository;

    private Pageable pageable = PageRequest.of(0, 10, Sort.by("nomeReitor").descending());

    @Operation(summary = "Cria um diploma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Diploma criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos inválidos", content = @Content(schema = @Schema()))})
    @PostMapping
    public ResponseEntity<DiplomaResponse> createDiploma(
            @Valid @RequestBody DiplomaRequest diplomaRequest) {
        Diploma diplomaConvertido = diplomaMapper.requestToDiploma(diplomaRequest);
        Diploma diplomaCriado = diplomaRepository.save(diplomaConvertido);

        DiplomaResponse diplomaResponse = diplomaMapper.diplomaToResponse(diplomaCriado);
        return new ResponseEntity<>(diplomaResponse, HttpStatus.CREATED);
    }



    @Operation(summary = "Busca todos os diplomas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diplomas encontrados"),
            @ApiResponse(responseCode = "404", description = "Diplomas não encontrados", content = @Content(schema = @Schema()))})
    @GetMapping
    public ResponseEntity<List<DiplomaResponse>> readAllDiplomas() {
        Page<Diploma> diplomas = diplomaRepository.findAll(pageable);
        if (diplomas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<DiplomaResponse> diplomaResponses = new ArrayList<>();
        for (Diploma diploma : diplomas) {
            diplomaResponses.add(diplomaMapper.diplomaToResponse(diploma));
        }
        return new ResponseEntity<>(diplomaResponses, HttpStatus.OK);
    }


    @Operation(summary = "Busca um diploma pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diploma encontrado"),
            @ApiResponse(responseCode = "404", description = "Diploma não encontrado", content = @Content(schema = @Schema()))})
    @GetMapping("/{id}")
    public ResponseEntity<DiplomaResponse> readDiplomaById(@PathVariable Long id) {
        Optional<Diploma> diplomaSalvo = diplomaRepository.findById(id);
        if (diplomaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DiplomaResponse diplomaResponse = diplomaMapper.diplomaToResponse(diplomaSalvo.get());
        return new ResponseEntity<>(diplomaResponse, HttpStatus.OK);
    }


    @Operation(summary = "Atualiza um diploma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diploma atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Diploma não encontrado", content = @Content(schema = @Schema()))})
    @PutMapping("/{id}")
    public ResponseEntity<DiplomaResponse> updateDiploma(@PathVariable Long id, @Valid @RequestBody DiplomaRequest diplomaRequest) {
        Optional<Diploma> diplomaSalvo = diplomaRepository.findById(id);
        if (diplomaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Diploma diplomaConvertido = diplomaMapper.requestToDiploma(diplomaRequest);
        diplomaConvertido.setId(id);
        Diploma diplomaAtualizado = diplomaRepository.save(diplomaConvertido);
        DiplomaResponse diplomaResponse = diplomaMapper.diplomaToResponse(diplomaAtualizado);
        return new ResponseEntity<>(diplomaResponse, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um diploma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diploma deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Diploma não encontrado", content = @Content(schema = @Schema()))})
    @DeleteMapping("/{id}")
    public ResponseEntity<DiplomaResponse> deleteDiploma(@PathVariable Long id) {
        Optional<Diploma> diploma = diplomaRepository.findById(id);
        if (diploma.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        diplomaRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Busca diploma por ID. Metodo disponivel para todos os usuarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diploma encontrado"),
            @ApiResponse(responseCode = "404", description = "Diploma com ID não encontrado", content = @Content(schema = @Schema()))    })
    @GetMapping("/all/{id}")
    public ResponseEntity<DiplomaTextResponse> readDiplomaByIdForAll(@PathVariable Long id){
        Optional<Diploma> diplomaSalvo = diplomaRepository.findById(id);
        if (diplomaSalvo.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DiplomaTextResponse diplomaResponse = diplomaMapper.diplomaTextResponse(diplomaSalvo.get());
        return new ResponseEntity<>(diplomaResponse, HttpStatus.OK);
    }

}
