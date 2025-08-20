package br.anhembi.eventos.controller;

import br.anhembi.eventos.model.Evento;
import br.anhembi.eventos.services.EventoService;
import br.anhembi.eventos.services.UsuarioService;
import br.anhembi.eventos.view.Menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public static void listarEventos(Menu menu, String idUsuarioLogado) {
        eventos = EventoService.carregarEventos();
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.\n");
            return;
        }

        LocalDateTime agora = LocalDateTime.now();
        List<Evento> passados = new ArrayList<>();
        List<Evento> emAndamento = new ArrayList<>();
        List<Evento> futuros = new ArrayList<>();

        for (Evento e : eventos) {
            if (e.getHorarioFim().isBefore(agora)) {
                passados.add(e);
            } else if (e.getHorarioInicio().isAfter(agora)) {
                futuros.add(e);
            } else {
                emAndamento.add(e);
            }
        }

        exibirMenuEventos(menu, passados, emAndamento, futuros, idUsuarioLogado);
    }

    private static void exibirMenuEventos(Menu menu, List<Evento> passados,
            List<Evento> emAndamento, List<Evento> futuros, String idUsuarioLogado) {
        // Construir lista visual
        List<String> opcoesVisuais = new ArrayList<>();
        List<Evento> todosEventos = new ArrayList<>();

        if (!passados.isEmpty()) {
            opcoesVisuais.add("==== Eventos Já Ocorridos ====");
            for (Evento e : passados) {
                opcoesVisuais.add(e.getNome());
                todosEventos.add(e);
            }
        }

        if (!emAndamento.isEmpty()) {
            opcoesVisuais.add("==== Eventos em Andamento ====");
            for (Evento e : emAndamento) {
                opcoesVisuais.add(e.getNome());
                todosEventos.add(e);
            }
        }

        if (!futuros.isEmpty()) {
            opcoesVisuais.add("==== Eventos Futuros ====");
            for (Evento e : futuros) {
                opcoesVisuais.add(e.getNome());
                todosEventos.add(e);
            }
        }

        // Mapear números para eventos
        Map<Integer, Evento> numeroParaEvento = new LinkedHashMap<>();
        int numero = 1;
        for (String linha : opcoesVisuais) {
            if (linha.startsWith("====")) {
                System.out.println(linha);
            } else {
                System.out.printf("%d) %s%n", numero, linha);
                numeroParaEvento.put(numero, todosEventos.get(0));
                todosEventos.remove(0);
                numero++;
            }
        }

        if (numeroParaEvento.isEmpty())
            return;

        while (true) {
            String input = menu.lerLinha("Escolha um número: ");
            try {
                int escolha = Integer.parseInt(input);
                if (numeroParaEvento.containsKey(escolha)) {
                    exibirDetalhesEvento(menu, numeroParaEvento.get(escolha), idUsuarioLogado);
                    break;
                } else {
                    System.out.println("Número inválido, tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
            }
        }
    }

    private static void exibirDetalhesEvento(Menu menu, Evento evento, String idUsuarioLogado) {
        evento = EventoService.buscarPorId(evento.getId());
        Menu.limparTela();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("==== Detalhes do Evento ====");
        System.out.println("Nome: " + evento.getNome());
        System.out.println("Endereço: " + evento.getEndereco());
        System.out.println("Categoria: " + evento.getCategoria());
        System.out.println("Início: " + evento.getHorarioInicio().format(fmt));
        System.out.println("Fim: " + evento.getHorarioFim().format(fmt));
        System.out.println("Descrição: " + evento.getDescricao());

        System.out.println("\nParticipantes:");
        if (evento.getIdParticipantes() == null || evento.getIdParticipantes().length == 0) {
            System.out.println("Nenhum participante inscrito.");
        } else {
            for (String id : evento.getIdParticipantes()) {
                System.out.println(UsuarioService.buscarPorId(id).getNome());
            }
            System.out.println("\n");
        }

        boolean jaInscrito = evento.getIdParticipantes() != null
                && List.of(evento.getIdParticipantes()).contains(idUsuarioLogado);

        String[] opcoes = jaInscrito ? new String[] { "Remover cadastro", "Voltar" }
                : new String[] { "Cadastrar-se no evento", "Voltar" };

        String escolha = menu.mostrarMenu("==== Ações ====", opcoes);

        if (escolha.equals("Cadastrar-se no evento")) {
            EventoService.adicionarParticipante(evento.getId(), idUsuarioLogado);
            System.out.println("Você foi inscrito no evento! \n");
        } else if (escolha.equals("Remover cadastro")) {
            EventoService.removerParticipante(evento.getId(), idUsuarioLogado);
            System.out.println("Você foi removido do evento! \n");
        } else if (escolha.equals("Voltar")) {
            EventosController.listarEventos(menu, idUsuarioLogado);
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
