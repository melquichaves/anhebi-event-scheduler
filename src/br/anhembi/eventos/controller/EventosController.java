package br.anhembi.eventos.controller;

import br.anhembi.eventos.model.Evento;
import br.anhembi.eventos.model.enums.ListaCategorias;
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
        String categoria = menu.mostrarMenu("Escolha a categoria do evento:",
                ListaCategorias.CATEGORIAS);
        LocalDateTime horarioInicio = dateTimePrompt(menu, "Data de Início (dd/MM/yyyy HH:mm): ");

        LocalDateTime horarioFim;
        while (true) {
            horarioFim = dateTimePrompt(menu, "Data de Fim (dd/MM/yyyy HH:mm): ");
            if (horarioFim.isBefore(horarioInicio)) {
                System.out.println(
                        "A data de fim não pode ser anterior à data de início. Tente novamente.");
            } else {
                break;
            }
        }

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

        Map<Integer, Evento> numeroParaEvento = new HashMap<>();
        int numero = 1;

        System.out.println("==== Lista de Eventos ====");

        numero = exibirEventosPorCategoria(menu, "Eventos Já Ocorridos", passados, numero,
                numeroParaEvento);
        numero = exibirEventosPorCategoria(menu, "Eventos em Andamento", emAndamento, numero,
                numeroParaEvento);
        numero = exibirEventosPorCategoria(menu, "Eventos Futuros", futuros, numero,
                numeroParaEvento);

        // Adiciona opção de voltar
        System.out.printf("\n%d) Voltar%n", numero);
        int numeroVoltar = numero;

        while (true) {
            String input = menu.lerLinha("Escolha um número: ");
            try {
                int escolha = Integer.parseInt(input);
                if (escolha == numeroVoltar) {
                    // Sai do menu de eventos
                    break;
                } else if (numeroParaEvento.containsKey(escolha)) {
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

    private static int exibirEventosPorCategoria(Menu menu, String titulo, List<Evento> lista,
            int numeroBase, Map<Integer, Evento> mapa) {
        if (!lista.isEmpty()) {
            System.out.println("==== " + titulo + " ====");
            for (Evento e : lista) {
                System.out.printf("%d) %s%n", numeroBase, e.getNome());
                mapa.put(numeroBase, e);
                numeroBase++;
            }
            System.out.println("\n");
        }
        return numeroBase;
    }

    private static void exibirDetalhesEvento(Menu menu, Evento evento, String idUsuarioLogado) {
        // Recarrega o evento para pegar participantes atualizados
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
            System.out.println("Nenhum participante inscrito. \n");
        } else {
            for (String id : evento.getIdParticipantes()) {
                System.out.println(UsuarioService.buscarPorId(id).getNome());
            }
            System.out.println("\n");
        }

        boolean jaInscrito = evento.getIdParticipantes() != null
                && Arrays.asList(evento.getIdParticipantes()).contains(idUsuarioLogado);

        String[] opcoes = jaInscrito ? new String[] { "Remover cadastro", "Voltar" }
                : new String[] { "Cadastrar-se no evento", "Voltar" };

        String escolha = menu.mostrarMenu("==== Ações ====", opcoes);

        if (escolha.equals("Cadastrar-se no evento")) {
            EventoService.adicionarParticipante(evento.getId(), idUsuarioLogado);
            System.out.println("Você foi inscrito no evento! \n");
            exibirDetalhesEvento(menu, evento, idUsuarioLogado); // atualiza a tela
        } else if (escolha.equals("Remover cadastro")) {
            EventoService.removerParticipante(evento.getId(), idUsuarioLogado);
            System.out.println("Você foi removido do evento! \n");
            exibirDetalhesEvento(menu, evento, idUsuarioLogado); // atualiza a tela
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
