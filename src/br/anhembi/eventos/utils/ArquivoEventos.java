package br.anhembi.eventos.utils;

import br.anhembi.eventos.model.Evento;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArquivoEventos {
    private static final String ARQUIVO = "events.data";

    public static void salvarEventos(List<Evento> eventos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (Evento e : eventos) {
                writer.println(e.getNome() + ";" + e.getEndereco() + ";" + e.getCategoria() + ";" +
                        e.getHorario().format(formatter) + ";" + e.getDescricao());
            }
        } catch (IOException ex) {
            System.out.println("Erro ao salvar eventos: " + ex.getMessage());
        }
    }

    public static List<Evento> carregarEventos() {
        List<Evento> eventos = new ArrayList<>();
        File file = new File(ARQUIVO);
        if (!file.exists()) return eventos;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    eventos.add(new Evento(parts[0], parts[1], parts[2],
                            LocalDateTime.parse(parts[3], formatter), parts[4]));
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro ao carregar eventos: " + ex.getMessage());
        }
        return eventos;
    }
}
