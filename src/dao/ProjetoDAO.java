package dao;

import model.Projeto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {
    private Connection connection;

    public ProjetoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public void cadastrar(Projeto projeto) throws SQLException {
        String sql = "INSERT INTO projetos (descricao, ods, responsavel, telefone, data_criacao, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, projeto.getDescricao());
            stmt.setString(2, projeto.getOds());
            stmt.setString(3, projeto.getResponsavel());
            stmt.setString(4, projeto.getTelefone());
            stmt.setDate(5, new java.sql.Date(projeto.getDataCriacao().getTime()));
            stmt.setString(6, projeto.getStatus());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                projeto.setCodigo(rs.getInt(1));
            }
        }
    }

    public void atualizar(Projeto projeto) throws SQLException {
        String sql = "UPDATE projetos SET descricao = ?, ods = ?, responsavel = ?, telefone = ?, data_criacao = ?, status = ? WHERE codigo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, projeto.getDescricao());
            stmt.setString(2, projeto.getOds());
            stmt.setString(3, projeto.getResponsavel());
            stmt.setString(4, projeto.getTelefone());
            stmt.setDate(5, new java.sql.Date(projeto.getDataCriacao().getTime()));
            stmt.setString(6, projeto.getStatus());
            stmt.setInt(7, projeto.getCodigo());
            
            stmt.executeUpdate();
        }
    }

    public void excluir(int codigo) throws SQLException {
        String sql = "DELETE FROM projetos WHERE codigo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }

    public Projeto buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM projetos WHERE codigo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return criarProjeto(rs);
            }
        }
        return null;
    }

    public List<Projeto> listarTodos() throws SQLException {
        List<Projeto> projetos = new ArrayList<>();
        String sql = "SELECT * FROM projetos";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                projetos.add(criarProjeto(rs));
            }
        }
        return projetos;
    }

    private Projeto criarProjeto(ResultSet rs) throws SQLException {
        Projeto projeto = new Projeto();
        projeto.setCodigo(rs.getInt("codigo"));
        projeto.setDescricao(rs.getString("descricao"));
        projeto.setOds(rs.getString("ods"));
        projeto.setResponsavel(rs.getString("responsavel"));
        projeto.setTelefone(rs.getString("telefone"));
        projeto.setDataCriacao(rs.getDate("data_criacao"));
        projeto.setStatus(rs.getString("status"));
        return projeto;
    }
} 