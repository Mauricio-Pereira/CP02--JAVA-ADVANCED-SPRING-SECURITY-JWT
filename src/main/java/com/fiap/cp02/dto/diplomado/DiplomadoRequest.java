package com.fiap.cp02.dto.diplomado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record DiplomadoRequest(
        @NotBlank(message = "O nome do diplomado é obrigatório")
        String nome,

        @NotBlank(message = "A nacionalidade é obrigatória")
        String nacionalidade,

        @NotBlank(message = "A naturalidade é obrigatória")
        String naturalidade,

        @NotBlank(message = "O RG é obrigatório")
        @Pattern(regexp = "^\\d{9}$", message = "O RG deve ter 9 dígitos")
        String rg
){

}
