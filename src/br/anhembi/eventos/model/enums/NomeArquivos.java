package br.anhembi.eventos.model.enums;

public enum NomeArquivos {
    USUARIOS("users.data"),
    EVENTOS("events.data");

    private final String nomeArquivo;

    NomeArquivos(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }
}
