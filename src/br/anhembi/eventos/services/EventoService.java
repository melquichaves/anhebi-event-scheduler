package br.anhembi.eventos.services;

import br.anhembi.eventos.model.Evento;
import br.anhembi.eventos.model.enums.NomeArquivos;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EventoService {

    private static final Random random = new Random();
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm");

    public static void salvarEventos(List<Evento> eventos) {
        try (PrintWriter writer = new PrintWriter(
                new FileWriter(NomeArquivos.EVENTOS.getNomeArquivo()))) {
            for (Evento e : eventos) {
                String participantes = e.getIdParticipantes() != null
                        ? String.join(",", e.getIdParticipantes())
                        : "";
                writer.println(e.getId() + ";" + e.getNome() + ";" + e.getEndereco() + ";"
                        + e.getCategoria() + ";" + e.getHorarioInicio().format(formatter) + ";"
                        + e.getHorarioFim().format(formatter) + ";" + e.getDescricao() + ";"
                        + participantes);
            }
        } catch (IOException ex) {
            System.out.println("Erro ao salvar eventos: " + ex.getMessage());
        }
    }

    public static List<Evento> carregarEventos() {
        List<Evento> eventos = new ArrayList<>();
        File file = new File(NomeArquivos.EVENTOS.getNomeArquivo());
        if (!file.exists())
            return eventos;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 7) {
                    Evento e = new Evento(parts[0], // id
                            parts[1], // nome
                            parts[2], // endereco
                            parts[3], // categoria
                            LocalDateTime.parse(parts[4], formatter), // horarioInicio
                            LocalDateTime.parse(parts[5], formatter), // horarioFim
                            parts[6] // descricao
                    );

                    if (parts.length > 7 && !parts[7].isEmpty()) {
                        String[] participantes = parts[7].split(",");
                        e.setIdParticipantes(participantes);
                    }

                    eventos.add(e);
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro ao carregar eventos: " + ex.getMessage());
        }
        return eventos;
    }

    public static String gerarIdUnico() {
        String id;
        do {
            id = String.format("%04d", random.nextInt(10000));
        } while (buscarPorId(id) != null);
        return id;
    }

    public static Evento buscarPorId(String id) {
        for (Evento e : carregarEventos()) {
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }

    public static void adicionarParticipante(String idEvento, String idParticipante) {
        File arquivoOriginal = new File(NomeArquivos.EVENTOS.getNomeArquivo());
        File arquivoTemp = new File("eventos_temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoOriginal));
                PrintWriter pw = new PrintWriter(new FileWriter(arquivoTemp))) {

            String line;
            boolean encontrado = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(idEvento)) {
                    encontrado = true;

                    // mantém participantes existentes
                    String[] participantes = parts.length > 7 && !parts[7].isEmpty()
                            ? parts[7].split(",")
                            : new String[] {};

                    List<String> lista = new ArrayList<>(List.of(participantes));
                    if (!lista.contains(idParticipante)) {
                        lista.add(idParticipante);
                    }

                    String participantesAtualizados = String.join(",", lista);

                    // escreve linha atualizada
                    pw.println(parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3] + ";"
                            + parts[4] + ";" + parts[5] + ";" + parts[6] + ";"
                            + participantesAtualizados);
                } else {
                    // escreve linha original
                    pw.println(line);
                }
            }

            if (!encontrado) {
                System.out.println("Evento com ID " + idEvento + " não encontrado.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao atualizar participante: " + e.getMessage());
            return;
        }

        // substitui arquivo original pelo temporário
        if (!arquivoTemp.renameTo(arquivoOriginal)) {
            System.out.println("Erro ao substituir arquivo de eventos.");
        }
    }
}
