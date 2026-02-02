package br.projeto.tarefa;

import br.root.config.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/tarefas/deletar")
public class TarefaDeletarServlet extends HttpServlet {

    @Override
    protected void doPost(
        HttpServletRequest request, 
        HttpServletResponse response
    ) throws ServletException, IOException {

        String idStr = request.getParameter("id-tarefa");
        int id;
        
        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            request.setAttribute("erro", "ID inválido.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }

        try {
            TarefaDAO dao = new TarefaDAO();

            dao.excluirTarefa(id);

            // PRG: evita reenviar POST ao dar F5
            response.sendRedirect(request.getContextPath() + "/tarefas");

        } catch (SQLException e) {
            log("Erro ao deletar tarefa id=" + id, e);

            request.setAttribute("erro", "Erro ao deletar a tarefa. Contate um administrador do sistema.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }
}
