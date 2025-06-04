package dao;

import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private DatabaseConnection dbConnection;

    public UsuarioDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    public void cadastrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, cpf, rg, email, faculdade, ra) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getRg());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getFaculdade());
            stmt.setString(6, usuario.getRa());
            
            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorCpfRg(String cpf, String rg) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE cpf = ? AND rg = ?";
        
        try (Connection conn = dbConnection.getConnection();
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
        
        try (Connection conn = dbConnection.getConnection();
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