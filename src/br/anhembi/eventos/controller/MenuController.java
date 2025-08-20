package br.anhembi.eventos.controller;

import br.anhembi.eventos.model.enums.ListaOpcoesMenus;
import br.anhembi.eventos.services.EventoService;
import br.anhembi.eventos.view.Menu;

public class MenuController {
    private final Menu menu;

    public MenuController(Menu menu) {
        this.menu = menu;
    }

    public boolean exibirMenuLogin() {
        String[] options = ListaOpcoesMenus.MENU_LOGIN;
        String option;
        do {
            option = menu.mostrarMenu("==== Menu Login ====", options);
            switch (option) {
            case "Entrar com ID de usuário":
                Menu.limparTela();
                String id = menu.lerLinha("Digite seu ID (4 dígitos): ");
                if (UsuariosController.logarUsuario(id)) {
                    return true;
                }
                break;
            case "Novo usuário":
                Menu.limparTela();
                UsuariosController.cadastrarUsuario(menu);
                System.out.println("Login automático realizado. \n");
                return true;
            case "Sair":
                System.out.println("Saindo...");
                return false;
            default:
                System.out.println("Opção inválida.");
            }
        } while (true);
    }

    public void exibirMenuPrincipal() {
        String[] options = ListaOpcoesMenus.MENU_PRINCIPAL;
        String option;
        do {
            option = menu.mostrarMenu("==== Menu Principal ====", options);
            switch (option) {
            case "Cadastrar evento":
                EventosController.cadastrarEvento(menu);
                break;
            case "Listar eventos":
                EventosController.listarEventos(menu, UsuariosController.usuarioLogado.getId());
                break;
            case "Participar de evento":
                System.out.println("Funcionalidade de participação ainda não implementada");
                break;
            case "Deslogar":
                System.out.println("Deslogando...");
                UsuariosController.deslogarUsuario();
                return; // Volta ao menu de login
            case "Sair":
                System.out.println("Saindo...");
                EventoService.salvarEventos(EventosController.eventos);
                System.exit(0);
            default:
                System.out.println("Opção inválida!");
            }
        } while (true);
    }
}