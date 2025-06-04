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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {
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
        
        Map<String, String> result = new HashMap<>();
        
        try {
            // Verifica se o CPF já está cadastrado
            String cpf = request.getParameter("cpf");
            if (usuarioDAO.buscarPorCPF(cpf) != null) {
                result.put("status", "error");
                result.put("message", "CPF já cadastrado!");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } else {
                // Cria novo usuário
                Usuario usuario = new Usuario();
                usuario.setNome(request.getParameter("nome"));
                usuario.setEmail(request.getParameter("email"));
                usuario.setCpf(cpf);
                usuario.setRg(request.getParameter("rg"));
                usuario.setRa(request.getParameter("ra"));
                usuario.setFaculdade(request.getParameter("faculdade"));
                
                // Hash da senha antes de salvar
                String senhaHash = PasswordUtil.hashPassword(request.getParameter("senha"));
                usuario.setSenha(senhaHash);
                
                // Salva o usuário
                usuarioDAO.salvar(usuario);
                
                result.put("status", "success");
                result.put("message", "Cadastro realizado com sucesso!");
                result.put("redirect", "login.html");
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "Erro ao realizar cadastro: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(result));
    }
} 