package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Projeto;
import dao.ProjetoDAO;

public class ProjetoDialog extends JDialog {
    private Projeto projeto;
    private ProjetoDAO projetoDAO;
    private boolean projetoSalvo;
    
    private JTextField txtDescricao;
    private JTextField txtOds;
    private JTextField txtResponsavel;
    private JTextField txtTelefone;
    private JTextField txtDataCriacao;
    private JComboBox<String> cmbStatus;
    
    private JButton btnSalvar;
    private JButton btnCancelar;
    
    private SimpleDateFormat dateFormat;

    public ProjetoDialog(Frame owner, Projeto projeto) {
        super(owner, true);
        this.projeto = projeto != null ? projeto : new Projeto();
        this.projetoDAO = new ProjetoDAO();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.projetoSalvo = false;
        
        inicializarComponentes();
        if (projeto != null) {
            preencherCampos();
        }
    }

    private void inicializarComponentes() {
        setTitle(projeto.getCodigo() == 0 ? "Novo Projeto" : "Editar Projeto");
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDescricao = new JTextField(20);
        panel.add(txtDescricao, gbc);

        // ODS
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(new JLabel("ODS:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtOds = new JTextField(20);
        panel.add(txtOds, gbc);

        // Responsável
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Responsável:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtResponsavel = new JTextField(20);
        panel.add(txtResponsavel, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtTelefone = new JTextField(20);
        panel.add(txtTelefone, gbc);

        // Data Criação
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Data Criação:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDataCriacao = new JTextField(10);
        panel.add(txtDataCriacao, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] statusOptions = {"Em Andamento", "Concluído", "Cancelado"};
        cmbStatus = new JComboBox<>(statusOptions);
        panel.add(cmbStatus, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        // Layout
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Eventos
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void preencherCampos() {
        txtDescricao.setText(projeto.getDescricao());
        txtOds.setText(projeto.getOds());
        txtResponsavel.setText(projeto.getResponsavel());
        txtTelefone.setText(projeto.getTelefone());
        txtDataCriacao.setText(dateFormat.format(projeto.getDataCriacao()));
        cmbStatus.setSelectedItem(projeto.getStatus());
    }

    private void salvar() {
        try {
            projeto.setDescricao(txtDescricao.getText());
            projeto.setOds(txtOds.getText());
            projeto.setResponsavel(txtResponsavel.getText());
            projeto.setTelefone(txtTelefone.getText());
            projeto.setDataCriacao(dateFormat.parse(txtDataCriacao.getText()));
            projeto.setStatus((String) cmbStatus.getSelectedItem());

            if (projeto.getCodigo() == 0) {
                projetoDAO.cadastrar(projeto);
            } else {
                projetoDAO.atualizar(projeto);
            }

            projetoSalvo = true;
            dispose();
            JOptionPane.showMessageDialog(this,
                "Projeto salvo com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar projeto: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isProjetoSalvo() {
        return projetoSalvo;
    }
} 