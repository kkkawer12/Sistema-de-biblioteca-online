package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.UsuarioDAO;
import model.Usuario;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private UsuarioDAO usuarioDAO;

    public LoginFrame() {
        usuarioDAO = new UsuarioDAO();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Login - Sistema de Projetos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Usuário
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Usuário:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        txtSenha = new JPasswordField(15);
        panel.add(txtSenha, gbc);

        // Botão Login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        btnLogin = new JButton("Login");
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> realizarLogin());

        add(panel);
    }

    private void realizarLogin() {
        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());

        try {
            Usuario user = usuarioDAO.autenticar(usuario, senha);
            if (user != null) {
                MainFrame mainFrame = new MainFrame(user);
                mainFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Usuário ou senha inválidos!", 
                    "Erro de Login", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao realizar login: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new LoginFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
} 