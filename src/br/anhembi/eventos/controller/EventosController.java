package br.anhembi.eventos.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import br.anhembi.eventos.model.Evento;
import br.anhembi.eventos.utils.ArquivoEventos;

public class EventosController {
    public static List<Evento> eventos = ArquivoEventos.carregarEventos();

    public static void cadastrarEvento(Scanner sc) {
        System.out.print("Nome do evento: ");
        String nome = sc.nextLine();

        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        System.out.print("Categoria: ");
        String categoria = sc.nextLine();

        LocalDateTime horario = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (horario == null) {
            System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
            String dataHora = sc.nextLine();
            try {
                horario = LocalDateTime.parse(dataHora, formatter);
            } catch (Exception e) {
                System.out.println("Formato inválido! Digite no formato correto: dd/MM/yyyy HH:mm (ex: 14/08/2025 20:00)");
            }
        }

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        eventos.add(new Evento(nome, endereco, categoria, horario, descricao));
        System.out.println("Evento cadastrado!\n");
    }

    public static void listarEventos() {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.\n");
        } else {
            System.out.println("==== Eventos Cadastrados ====");
            for (Evento e : eventos) {
                System.out.println(e);
            }
            System.out.println();
        }
    }

    
}
