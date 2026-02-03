package br.projeto.tarefa;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idStr = request.getParameter("id_tarefa");
            int id = Integer.parseInt(idStr);

            TarefaDAO dao = new TarefaDAO();
            dao.excluirTarefa(id);

            response.sendRedirect(request.getContextPath() + "/tarefas");

        } catch (NumberFormatException e) {
            log("ID inválido ao deletar", e);
            response.sendRedirect(request.getContextPath() + "/tarefas");

        } catch (SQLException e) {
            log("Erro ao deletar tarefa", e);
            response.sendRedirect(request.getContextPath() + "/tarefas");
        }
    }
}
