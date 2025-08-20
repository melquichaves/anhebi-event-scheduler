package br.anhembi.eventos.model;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private int idade;
    private String telefone;

    public Usuario(String id, String nome, String email, int idade, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getIdade() {
        return idade;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return id + ";" + nome + ";" + email + ";" + idade + ";" + telefone + ";";
    }

    public static Usuario fromString(String linha) {
        String[] dados = linha.split(";");
        return new Usuario(dados[0], dados[1], dados[2], Integer.parseInt(dados[3]), dados[4]);
    }
}
