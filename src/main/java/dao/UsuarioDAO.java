package dao;

import model.Usuario;
import util.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    public void salvar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, cpf, rg, faculdade, ra, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getRg());
            stmt.setString(5, usuario.getFaculdade());
            stmt.setString(6, usuario.getRa());
            stmt.setString(7, usuario.getSenha());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getLong(1));
                }
            }
        }
    }
    
    public Usuario buscarPorCPF(String cpf) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE cpf = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setRg(rs.getString("rg"));
                    usuario.setFaculdade(rs.getString("faculdade"));
                    usuario.setRa(rs.getString("ra"));
                    usuario.setSenha(rs.getString("senha"));
                    return usuario;
                }
            }
        }
        return null;
    }
    
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, rg = ?, faculdade = ?, ra = ?, senha = ? WHERE cpf = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getRg());
            stmt.setString(4, usuario.getFaculdade());
            stmt.setString(5, usuario.getRa());
            stmt.setString(6, usuario.getSenha());
            stmt.setString(7, usuario.getCpf());
            
            stmt.executeUpdate();
        }
    }
    
    public void excluir(String cpf) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE cpf = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorCpfRg(String cpf, String rg) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE cpf = ? AND rg = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            stmt.setString(2, rg);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extrairUsuario(rs);
                }
            }
        }
        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(extrairUsuario(rs));
            }
        }
        return usuarios;
    }

    private Usuario extrairUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setCpf(rs.getString("cpf"));
        usuario.setRg(rs.getString("rg"));
        usuario.setEmail(rs.getString("email"));
        usuario.setFaculdade(rs.getString("faculdade"));
        usuario.setRa(rs.getString("ra"));
        return usuario;
    }
} 