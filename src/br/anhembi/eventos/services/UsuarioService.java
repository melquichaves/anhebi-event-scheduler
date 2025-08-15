package br.anhembi.eventos.services;

import java.io.*;
import java.util.*;

import br.anhembi.eventos.model.Usuario;

public class UsuarioService {
    private static final String FILE = "users.data";
    private static final Random random = new Random();

    public static Usuario cadastrarUsuario(String id, String nome, String email, int idade, String telefone) {
        Usuario usuario = new Usuario(id, nome, email, idade, telefone);
        salvarUsuario(usuario);
        return usuario;
    }

    public static Usuario buscarPorId(String id) {
        for (Usuario u : listarUsuarios()) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public static List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(FILE);
        if (!file.exists()) return usuarios;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                usuarios.add(Usuario.fromString(linha));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private static void salvarUsuario(Usuario usuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE, true))) {
            bw.write(usuario.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String gerarIdUnico() {
        String id;
        do {
            id = String.format("%04d", random.nextInt(10000));
        } while (buscarPorId(id) != null);
        return id;
    }
}
