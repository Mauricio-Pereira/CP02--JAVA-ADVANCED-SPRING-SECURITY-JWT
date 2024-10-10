package com.fiap.cp02.model;

public enum TipoCurso{
    POS_GRADUACAO("Pós-Graduação"),
    GRADUACAO("Graduação");

    private String tipoCurso;

    TipoCurso(String tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

}
