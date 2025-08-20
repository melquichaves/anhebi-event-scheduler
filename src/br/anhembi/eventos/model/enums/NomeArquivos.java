package br.anhembi.eventos.model.enums;

public enum NomeArquivos {
    USUARIOS("src/br/anhembi/eventos/services/db/users.data"), EVENTOS("src/br/anhembi/eventos/services/db/users.data");

    private final String nomeArquivo;

    NomeArquivos(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }
}
