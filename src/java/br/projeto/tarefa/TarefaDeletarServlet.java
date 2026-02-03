package br.projeto.tarefa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/tarefas/deletar")
public class TarefaDeletarServlet extends HttpServlet {

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        String idStr = request.getParameter("id_tarefa");

        try {
            int id = Integer.parseInt(idStr);

            TarefaDAO dao = new TarefaDAO();
            dao.excluirTarefa(id);

            // flash message
            HttpSession session = request.getSession();
            session.setAttribute("alertaTipo", "danger");
            session.setAttribute("alertaMsg", "Tarefa deletada com sucesso.");

        } catch (NumberFormatException | SQLException e) {
            log("Erro ao deletar tarefa", e);

            HttpSession session = request.getSession();
            session.setAttribute("alertaTipo", "erro");
            session.setAttribute(
                "alertaMsg",
                "Erro ao deletar tarefa."
            );
        }

        // PRG
        response.sendRedirect(request.getContextPath() + "/tarefas");
    }
}
