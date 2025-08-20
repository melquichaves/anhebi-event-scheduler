package br.anhembi.eventos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento {
    private String id;
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horarioInicio;
    private LocalDateTime horarioFim;
    private String descricao;
    private String[] idParticipantes;

    public Evento(String id, String nome, String endereco, String categoria, LocalDateTime horarioInicio,
            LocalDateTime horarioFim, String descricao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalDateTime getHorarioFim() {
        return horarioFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public String[] getIdParticipantes() {
        return idParticipantes;
    }

    public void setIdParticipantes(String[] idParticipantes) {
        this.idParticipantes = idParticipantes;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return id + ";" + nome + ";" + endereco + ";" + categoria + ";" + horarioInicio.format(formatter) + ";"
                + horarioFim.format(formatter) + ";" + descricao;
    }
}
