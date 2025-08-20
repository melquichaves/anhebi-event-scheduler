package br.anhembi.eventos.controller;

import br.anhembi.eventos.model.Usuario;
import br.anhembi.eventos.services.UsuarioService;
import br.anhembi.eventos.view.Menu;

public class UsuariosController {
    public static Usuario usuarioLogado;

    public static void cadastrarUsuario(Menu menu) {
        String nome = menu.lerLinha("Nome: ");
        String email = menu.lerLinha("Email: ");

        int idade = 0;
        while (idade <= 0) {
            try {
                idade = Integer.parseInt(menu.lerLinha("Idade: "));
                if (idade <= 0)
                    System.out.print("Idade inválida. Digite novamente: ");
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Por favor, digite um número válido para a idade: ");
            }
        }

        String telefone = menu.lerLinha("Telefone: ");

        String newId = UsuarioService.gerarIdUnico();
        usuarioLogado = UsuarioService.cadastrarUsuario(newId, nome, email, idade, telefone);

        System.out.println("Usuário cadastrado com sucesso! Seu ID é: " + usuarioLogado.getId());
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static boolean logarUsuario(String id) {
        Usuario u = UsuarioService.buscarPorId(id);
        if (u != null) {
            usuarioLogado = u;
            System.out.println("Bem-vindo, " + UsuariosController.usuarioLogado.getNome() + "!");
            return true;
        } else {
            System.out.println("Usuário não encontrado.");
            return false;
        }
    }

    public static void deslogarUsuario() {
        usuarioLogado = null;
    }

}
