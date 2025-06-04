package dao;

import model.Avaliacao;
import model.Usuario;
import model.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {
    private Connection connection;
    private UsuarioDAO usuarioDAO;
    private LivroDAO livroDAO;

    public AvaliacaoDAO() {
        this.connection = ConnectionFactory.getConnection();
        this.usuarioDAO = new UsuarioDAO();
        this.livroDAO = new LivroDAO();
    }

    public void cadastrar(Avaliacao avaliacao) throws SQLException {
        String sql = "INSERT INTO avaliacoes (usuario_id, livro_id, nota, comentario) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, avaliacao.getUsuario().getId());
            stmt.setInt(2, avaliacao.getLivro().getId());
            stmt.setInt(3, avaliacao.getNota());
            stmt.setString(4, avaliacao.getComentario());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                avaliacao.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Avaliacao avaliacao) throws SQLException {
        String sql = "UPDATE avaliacoes SET nota = ?, comentario = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, avaliacao.getNota());
            stmt.setString(2, avaliacao.getComentario());
            stmt.setInt(3, avaliacao.getId());
            
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM avaliacoes WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Avaliacao> buscarPorLivro(Livro livro) throws SQLException {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM avaliacoes WHERE livro_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, livro.getId());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                avaliacoes.add(criarAvaliacao(rs));
            }
        }
        return avaliacoes;
    }

    public List<Avaliacao> buscarPorUsuario(Usuario usuario) throws SQLException {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM avaliacoes WHERE usuario_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                avaliacoes.add(criarAvaliacao(rs));
            }
        }
        return avaliacoes;
    }

    public double calcularMediaAvaliacoes(Livro livro) throws SQLException {
        String sql = "SELECT AVG(nota) as media FROM avaliacoes WHERE livro_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, livro.getId());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("media");
            }
        }
        return 0.0;
    }

    public boolean usuarioJaAvaliou(Usuario usuario, Livro livro) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM avaliacoes WHERE usuario_id = ? AND livro_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, livro.getId());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
        }
        return false;
    }

    private Avaliacao criarAvaliacao(ResultSet rs) throws SQLException {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(rs.getInt("id"));
        avaliacao.setUsuario(usuarioDAO.buscarPorId(rs.getInt("usuario_id")));
        avaliacao.setLivro(livroDAO.buscarPorId(rs.getInt("livro_id")));
        avaliacao.setNota(rs.getInt("nota"));
        avaliacao.setComentario(rs.getString("comentario"));
        avaliacao.setDataAvaliacao(rs.getTimestamp("data_avaliacao"));
        return avaliacao;
    }
} 