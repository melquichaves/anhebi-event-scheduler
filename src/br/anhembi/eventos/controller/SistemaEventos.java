package br.anhembi.eventos.controller;

import br.anhembi.eventos.model.Usuario;
import br.anhembi.eventos.services.UsuarioService;
import br.anhembi.eventos.utils.ArquivoEventos;
import br.anhembi.eventos.view.Menu;

import java.util.Scanner;

public class SistemaEventos {
    private static Menu menu = new Menu();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (UsuariosController.usuarioLogado == null) {
            System.out.println("==== Sistema de Eventos ====");
            System.out.println("1 - Entrar com ID de usuário");
            System.out.println("2 - Novo usuário");
            System.out.print("Escolha: ");
            String opcao = sc.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Digite seu ID (4 dígitos): ");
                    String id = sc.nextLine();
                    Usuario u = UsuarioService.buscarPorId(id);
                    if (u != null) {
                        UsuariosController.usuarioLogado = u;
                        System.out.println("Bem-vindo, " + UsuariosController.usuarioLogado.getNome() + "!");
                        exibirMenuPrincipal(sc);
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                    break;
                case "2":
                    UsuariosController.cadastrarUsuario(sc);
                    System.out.println("Login automático realizado.");
                    exibirMenuPrincipal(sc);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void exibirMenuPrincipal(Scanner sc) {
        int opcao;
        do {
        opcao = menu.mostrarMenuPrincipal();
            sc.nextLine();
            switch (opcao) {
                case 1 -> UsuariosController.cadastrarUsuario(sc);
                case 2 -> EventosController.cadastrarEvento(sc);
                case 3 -> EventosController.listarEventos();
                case 4 -> System.out.println("Funcionalidade de participação ainda não implementada");
                case 5 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 5);

        ArquivoEventos.salvarEventos(EventosController.eventos);
    }

}
