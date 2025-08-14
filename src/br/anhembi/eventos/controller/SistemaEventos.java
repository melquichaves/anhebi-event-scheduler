package br.anhembi.eventos.controller;

import br.anhembi.eventos.model.Evento;
import br.anhembi.eventos.model.Usuario;
import br.anhembi.eventos.utils.ArquivoEventos;
import br.anhembi.eventos.view.Menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaEventos {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Evento> eventos = ArquivoEventos.carregarEventos();
    private static Menu menu = new Menu();

    public static void main(String[] args) {
        Scanner sc = menu.getScanner();
        int opcao;
        do {
            opcao = menu.mostrarMenuPrincipal();
            sc.nextLine();
            switch (opcao) {
                case 1 -> cadastrarUsuario(sc);
                case 2 -> cadastrarEvento(sc);
                case 3 -> listarEventos();
                case 4 -> System.out.println("Funcionalidade de participação ainda não implementada");
                case 5 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 5);

        ArquivoEventos.salvarEventos(eventos);
    }

    private static void cadastrarUsuario(Scanner sc) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine();
        usuarios.add(new Usuario(nome, email, idade));
        System.out.println("Usuário cadastrado!\n");
    }

    private static void cadastrarEvento(Scanner sc) {
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

    private static void listarEventos() {
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
