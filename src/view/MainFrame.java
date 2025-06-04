package view;

import javax.swing.*;
import java.awt.*;
import model.Usuario;
import model.Projeto;
import dao.ProjetoDAO;
import dao.UsuarioDAO;
import java.util.List;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

public class MainFrame extends JFrame {
    private Usuario usuarioLogado;
    private ProjetoDAO projetoDAO;
    private UsuarioDAO usuarioDAO;
    private JTabbedPane tabbedPane;
    private JTable tabelaProjetos;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabelaProjetos;
    private DefaultTableModel modeloTabelaUsuarios;
    private SimpleDateFormat dateFormat;

    public MainFrame(Usuario usuario) {
        this.usuarioLogado = usuario;
        this.projetoDAO = new ProjetoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        inicializarComponentes();
        carregarDados();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Gerenciamento - Usuário: " + usuarioLogado.getNome());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Projetos", criarPainelProjetos());
        tabbedPane.addTab("Usuários", criarPainelUsuarios());

        add(tabbedPane);
    }

    private JPanel criarPainelProjetos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Botões de Projetos
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNovoProjeto = new JButton("Novo Projeto");
        JButton btnEditarProjeto = new JButton("Editar");
        JButton btnExcluirProjeto = new JButton("Excluir");
        JButton btnAtualizarProjetos = new JButton("Atualizar");

        painelBotoes.add(btnNovoProjeto);
        painelBotoes.add(btnEditarProjeto);
        painelBotoes.add(btnExcluirProjeto);
        painelBotoes.add(btnAtualizarProjetos);

        // Tabela de Projetos
        String[] colunasProjetos = {"Código", "Descrição", "ODS", "Responsável", "Telefone", "Data Criação", "Status"};
        modeloTabelaProjetos = new DefaultTableModel(colunasProjetos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProjetos = new JTable(modeloTabelaProjetos);
        JScrollPane scrollProjetos = new JScrollPane(tabelaProjetos);

        // Eventos dos botões de projetos
        btnNovoProjeto.addActionListener(e -> abrirFormularioProjeto(null));
        btnEditarProjeto.addActionListener(e -> editarProjetoSelecionado());
        btnExcluirProjeto.addActionListener(e -> excluirProjetoSelecionado());
        btnAtualizarProjetos.addActionListener(e -> carregarProjetos());

        panel.add(painelBotoes, BorderLayout.NORTH);
        panel.add(scrollProjetos, BorderLayout.CENTER);

        return panel;
    }

    private JPanel criarPainelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());

        // Botões de Usuários
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNovoUsuario = new JButton("Novo Usuário");
        JButton btnEditarUsuario = new JButton("Editar");
        JButton btnExcluirUsuario = new JButton("Excluir");
        JButton btnAtualizarUsuarios = new JButton("Atualizar");

        painelBotoes.add(btnNovoUsuario);
        painelBotoes.add(btnEditarUsuario);
        painelBotoes.add(btnExcluirUsuario);
        painelBotoes.add(btnAtualizarUsuarios);

        // Tabela de Usuários
        String[] colunasUsuarios = {"ID", "Usuário", "Nome", "Email", "Telefone"};
        modeloTabelaUsuarios = new DefaultTableModel(colunasUsuarios, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaUsuarios = new JTable(modeloTabelaUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(tabelaUsuarios);

        // Eventos dos botões de usuários
        btnNovoUsuario.addActionListener(e -> abrirFormularioUsuario(null));
        btnEditarUsuario.addActionListener(e -> editarUsuarioSelecionado());
        btnExcluirUsuario.addActionListener(e -> excluirUsuarioSelecionado());
        btnAtualizarUsuarios.addActionListener(e -> carregarUsuarios());

        panel.add(painelBotoes, BorderLayout.NORTH);
        panel.add(scrollUsuarios, BorderLayout.CENTER);

        return panel;
    }

    private void carregarDados() {
        carregarProjetos();
        carregarUsuarios();
    }

    private void carregarProjetos() {
        try {
            modeloTabelaProjetos.setRowCount(0);
            List<Projeto> projetos = projetoDAO.listarTodos();
            
            for (Projeto projeto : projetos) {
                modeloTabelaProjetos.addRow(new Object[]{
                    projeto.getCodigo(),
                    projeto.getDescricao(),
                    projeto.getOds(),
                    projeto.getResponsavel(),
                    projeto.getTelefone(),
                    dateFormat.format(projeto.getDataCriacao()),
                    projeto.getStatus()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar projetos: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarUsuarios() {
        try {
            modeloTabelaUsuarios.setRowCount(0);
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            
            for (Usuario usuario : usuarios) {
                modeloTabelaUsuarios.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getUsuario(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getTelefone()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar usuários: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioProjeto(Projeto projeto) {
        ProjetoDialog dialog = new ProjetoDialog(this, projeto);
        dialog.setVisible(true);
        if (dialog.isProjetoSalvo()) {
            carregarProjetos();
        }
    }

    private void abrirFormularioUsuario(Usuario usuario) {
        UsuarioDialog dialog = new UsuarioDialog(this, usuario);
        dialog.setVisible(true);
        if (dialog.isUsuarioSalvo()) {
            carregarUsuarios();
        }
    }

    private void editarProjetoSelecionado() {
        int linhaSelecionada = tabelaProjetos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            try {
                int codigo = (int) tabelaProjetos.getValueAt(linhaSelecionada, 0);
                Projeto projeto = projetoDAO.buscarPorCodigo(codigo);
                if (projeto != null) {
                    abrirFormularioProjeto(projeto);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao editar projeto: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Selecione um projeto para editar",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editarUsuarioSelecionado() {
        int linhaSelecionada = tabelaUsuarios.getSelectedRow();
        if (linhaSelecionada >= 0) {
            try {
                int id = (int) tabelaUsuarios.getValueAt(linhaSelecionada, 0);
                Usuario usuario = usuarioDAO.buscarPorId(id);
                if (usuario != null) {
                    abrirFormularioUsuario(usuario);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao editar usuário: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Selecione um usuário para editar",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluirProjetoSelecionado() {
        int linhaSelecionada = tabelaProjetos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int codigo = (int) tabelaProjetos.getValueAt(linhaSelecionada, 0);
            int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir este projeto?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                try {
                    projetoDAO.excluir(codigo);
                    carregarProjetos();
                    JOptionPane.showMessageDialog(this,
                        "Projeto excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                        "Erro ao excluir projeto: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Selecione um projeto para excluir",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluirUsuarioSelecionado() {
        int linhaSelecionada = tabelaUsuarios.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int id = (int) tabelaUsuarios.getValueAt(linhaSelecionada, 0);
            
            if (id == usuarioLogado.getId()) {
                JOptionPane.showMessageDialog(this,
                    "Não é possível excluir o usuário logado",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir este usuário?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                try {
                    usuarioDAO.excluir(id);
                    carregarUsuarios();
                    JOptionPane.showMessageDialog(this,
                        "Usuário excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                        "Erro ao excluir usuário: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Selecione um usuário para excluir",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        }
    }
} 