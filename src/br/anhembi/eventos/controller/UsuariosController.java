package br.anhembi.eventos.controller;

import java.util.Scanner;

import br.anhembi.eventos.model.Usuario;
import br.anhembi.eventos.services.UsuarioService;

public class UsuariosController {
    public static Usuario usuarioLogado;

    public static void cadastrarUsuario(Scanner sc) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Idade: ");
        int idade = sc.nextInt();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        sc.nextLine();

        String newId = UsuarioService.gerarIdUnico();
        usuarioLogado = UsuarioService.cadastrarUsuario(newId, nome, email, idade, telefone);

        System.out.println("Usuário cadastrado com sucesso! Seu ID é: " + usuarioLogado.getId());
    }
    
}
