package dao;

import model.Emprestimo;
import model.Usuario;
import model.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {
    private Connection connection;
    private UsuarioDAO usuarioDAO;
    private LivroDAO livroDAO;

    public EmprestimoDAO() {
        this.connection = ConnectionFactory.getConnection();
        this.usuarioDAO = new UsuarioDAO();
        this.livroDAO = new LivroDAO();
    }

    public void cadastrar(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimos (usuario_id, livro_id, data_emprestimo, data_devolucao, status) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, emprestimo.getUsuario().getId());
            stmt.setInt(2, emprestimo.getLivro().getId());
            stmt.setTimestamp(3, new Timestamp(emprestimo.getDataEmprestimo().getTime()));
            stmt.setTimestamp(4, new Timestamp(emprestimo.getDataDevolucao().getTime()));
            stmt.setString(5, emprestimo.getStatus().name());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                emprestimo.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Emprestimo emprestimo) throws SQLException {
        String sql = "UPDATE emprestimos SET status = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, emprestimo.getStatus().name());
            stmt.setInt(2, emprestimo.getId());
            
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM emprestimos WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Emprestimo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM emprestimos WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarEmprestimo(rs);
            }
        }
        return null;
    }

    public List<Emprestimo> buscarPorUsuario(Usuario usuario) throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE usuario_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                emprestimos.add(criarEmprestimo(rs));
            }
        }
        return emprestimos;
    }

    public List<Emprestimo> buscarPorLivro(Livro livro) throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE livro_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, livro.getId());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                emprestimos.add(criarEmprestimo(rs));
            }
        }
        return emprestimos;
    }

    public List<Emprestimo> listarTodos() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                emprestimos.add(criarEmprestimo(rs));
            }
        }
        return emprestimos;
    }

    private Emprestimo criarEmprestimo(ResultSet rs) throws SQLException {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(rs.getInt("id"));
        emprestimo.setUsuario(usuarioDAO.buscarPorId(rs.getInt("usuario_id")));
        emprestimo.setLivro(livroDAO.buscarPorId(rs.getInt("livro_id")));
        emprestimo.setDataEmprestimo(rs.getTimestamp("data_emprestimo"));
        emprestimo.setDataDevolucao(rs.getTimestamp("data_devolucao"));
        emprestimo.setStatus(Emprestimo.Status.valueOf(rs.getString("status")));
        return emprestimo;
    }
} 