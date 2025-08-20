package br.anhembi.eventos.controller;

import br.anhembi.eventos.view.Menu;

public class SistemaEventos {

    public static void main(String[] args) {
        Menu menu = new Menu();
        MenuController menuController = new MenuController(menu);

        while (true) {
            boolean loggedIn = menuController.exibirMenuLogin();
            if (loggedIn) {
                menuController.exibirMenuPrincipal();
            } else {
                // Usuário escolheu sair do menu de login
                break;
            }
        }
    }
}
