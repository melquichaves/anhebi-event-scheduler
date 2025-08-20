package br.anhembi.eventos.controller;

import br.anhembi.eventos.model.Evento;
import br.anhembi.eventos.services.EventoService;
import br.anhembi.eventos.view.Menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventosController {
    public static List<Evento> eventos = EventoService.carregarEventos();

    public static void cadastrarEvento(Menu menu) {
        String nome = menu.lerLinha("Nome do evento: ");
        String endereco = menu.lerLinha("Endereço: ");
        String categoria = menu.lerLinha("Categoria: ");
        LocalDateTime horarioInicio = dateTimePrompt(menu, "Data de Início (dd/MM/yyyy HH:mm): ");
        LocalDateTime horarioFim = dateTimePrompt(menu, "Data de Fim (dd/MM/yyyy HH:mm): ");
        String descricao = menu.lerLinha("Descrição: ");

        eventos.add(new Evento(EventoService.gerarIdUnico(), nome, endereco, categoria,
                horarioInicio, horarioFim, descricao));

        EventoService.salvarEventos(eventos);
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

    private static LocalDateTime dateTimePrompt(Menu menu, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        while (true) {
            String dataHora = menu.lerLinha(prompt);
            try {
                return LocalDateTime.parse(dataHora, formatter);
            } catch (Exception e) {
                System.out.println(
                        "Formato inválido! Digite no formato correto: dd/MM/yyyy HH:mm (ex: 14/08/2025 20:00)");
            }
        }
    }

}
