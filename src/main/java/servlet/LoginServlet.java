package servlet;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import model.Usuario;
import util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        
        Map<String, String> result = new HashMap<>();
        
        try {
            Usuario usuario = usuarioDAO.buscarPorCPF(cpf);
            
            if (usuario != null && PasswordUtil.checkPassword(senha, usuario.getSenha())) {
                // Login bem sucedido
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                
                result.put("status", "success");
                result.put("message", "Login realizado com sucesso!");
                result.put("redirect", "area-usuario.html");
            } else {
                result.put("status", "error");
                result.put("message", "CPF ou senha inválidos!");
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "Erro ao realizar login: " + e.getMessage());
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(result));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("login.html");
        }
    }
} 