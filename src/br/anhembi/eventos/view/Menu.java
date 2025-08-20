package br.anhembi.eventos.view;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public static void limparTela() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public String mostrarMenu(String title, String[] opcoes) {
        while (true) {
            System.out.println(title);
            for (int i = 0; i < opcoes.length; i++) {
                System.out.printf("%d) %s%n", i + 1, opcoes[i]);
            }

            System.out.print("Escolha uma opção pelo número: ");
            String input = scanner.nextLine();

            try {
                int escolha = Integer.parseInt(input);
                if (escolha >= 1 && escolha <= opcoes.length) {
                    limparTela(); // limpa a tela ao selecionar uma opção válida
                    return opcoes[escolha - 1];
                } else {
                    System.out.println("Número inválido! Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
            }
        }
    }

    public String lerLinha(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
