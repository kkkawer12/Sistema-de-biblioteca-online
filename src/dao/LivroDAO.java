package dao;

import model.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    private Connection connection;

    public LivroDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public void cadastrar(Livro livro) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, categoria, descricao, arquivo_path) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getCategoria().name());
            stmt.setString(4, livro.getDescricao());
            stmt.setString(5, livro.getArquivoPath());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                livro.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE livros SET titulo = ?, autor = ?, categoria = ?, descricao = ?, arquivo_path = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getCategoria().name());
            stmt.setString(4, livro.getDescricao());
            stmt.setString(5, livro.getArquivoPath());
            stmt.setInt(6, livro.getId());
            
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM livros WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM livros WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarLivro(rs);
            }
        }
        return null;
    }

    public List<Livro> listarTodos() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                livros.add(criarLivro(rs));
            }
        }
        return livros;
    }

    public List<Livro> buscarPorCategoria(Livro.Categoria categoria) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE categoria = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoria.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                livros.add(criarLivro(rs));
            }
        }
        return livros;
    }

    public List<Livro> buscarPorTitulo(String titulo) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE titulo LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + titulo + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                livros.add(criarLivro(rs));
            }
        }
        return livros;
    }

    private Livro criarLivro(ResultSet rs) throws SQLException {
        Livro livro = new Livro();
        livro.setId(rs.getInt("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setCategoria(Livro.Categoria.valueOf(rs.getString("categoria")));
        livro.setDescricao(rs.getString("descricao"));
        livro.setArquivoPath(rs.getString("arquivo_path"));
        livro.setDataCadastro(rs.getTimestamp("data_cadastro"));
        return livro;
    }
} 