package util;

import dao.UsuarioDAO;
import model.Usuario;

public class CriarUsuarioAdmin {
    public static void main(String[] args) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            // Criando usuário administrador
            Usuario admin = new Usuario();
            admin.setUsuario("admin");
            admin.setNome("Administrador");
            admin.setEmail("admin@sistema.com");
            admin.setSenha("admin123");
            admin.setTelefone("(00) 0000-0000");
            
            usuarioDAO.cadastrar(admin);
            
            System.out.println("Usuário administrador criado com sucesso!");
            System.out.println("Login: admin");
            System.out.println("Senha: admin123");
            
        } catch (Exception e) {
            System.err.println("Erro ao criar usuário administrador: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 