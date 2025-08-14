package br.anhembi.eventos.view;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    public int mostrarMenuPrincipal() {
        System.out.println("==== Sistema de Eventos ====");
        System.out.println("1. Cadastrar usuário");
        System.out.println("2. Cadastrar evento");
        System.out.println("3. Listar eventos");
        System.out.println("4. Participar de evento");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
    }

    public Scanner getScanner() {
        return scanner;
    }
}

