package view;

import javax.swing.*;
import java.awt.*;
import model.Usuario;
import dao.UsuarioDAO;

public class UsuarioDialog extends JDialog {
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private boolean usuarioSalvo;
    
    private JTextField txtUsuario;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JTextField txtTelefone;
    
    private JButton btnSalvar;
    private JButton btnCancelar;

    public UsuarioDialog(Frame owner, Usuario usuario) {
        super(owner, true);
        this.usuario = usuario != null ? usuario : new Usuario();
        this.usuarioDAO = new UsuarioDAO();
        this.usuarioSalvo = false;
        
        inicializarComponentes();
        if (usuario != null && usuario.getId() != 0) {
            preencherCampos();
        }
    }

    private void inicializarComponentes() {
        setTitle(usuario.getId() == 0 ? "Novo Usuário" : "Editar Usuário");
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuário
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtUsuario = new JTextField(20);
        panel.add(txtUsuario, gbc);

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNome = new JTextField(20);
        panel.add(txtNome, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtSenha = new JPasswordField(20);
        panel.add(txtSenha, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        panel.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtTelefone = new JTextField(20);
        panel.add(txtTelefone, gbc);

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
        txtUsuario.setText(usuario.getUsuario());
        txtNome.setText(usuario.getNome());
        txtEmail.setText(usuario.getEmail());
        txtSenha.setText(usuario.getSenha());
        txtTelefone.setText(usuario.getTelefone());
    }

    private void salvar() {
        try {
            usuario.setUsuario(txtUsuario.getText());
            usuario.setNome(txtNome.getText());
            usuario.setEmail(txtEmail.getText());
            usuario.setSenha(new String(txtSenha.getPassword()));
            usuario.setTelefone(txtTelefone.getText());

            if (usuario.getId() == 0) {
                usuarioDAO.cadastrar(usuario);
            } else {
                usuarioDAO.atualizar(usuario);
            }

            usuarioSalvo = true;
            dispose();
            JOptionPane.showMessageDialog(this,
                "Usuário salvo com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao salvar usuário: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isUsuarioSalvo() {
        return usuarioSalvo;
    }
} 