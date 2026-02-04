package br.projeto.tarefa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/tarefas/buscar-por-id")
public class TarefaBuscarPorIdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String acao  = request.getParameter("acao"); // "editar" | "deletar"

        if (idStr == null || idStr.trim().isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("alertaTipo", "flashErro");
            session.setAttribute("alertaMsg", "ID não informado.");
            response.sendRedirect(request.getContextPath() + "/tarefas");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            HttpSession session = request.getSession();
            session.setAttribute("alertaTipo", "flashErro");
            session.setAttribute("alertaMsg", "ID inválido.");
            response.sendRedirect(request.getContextPath() + "/tarefas");
            return;
        }

        // valida acao (pra não abrir nada errado)
        if (!"editar".equals(acao) && !"deletar".equals(acao)) {
            HttpSession session = request.getSession();
            session.setAttribute("alertaTipo", "flashErro");
            session.setAttribute("alertaMsg", "Ação inválida.");
            response.sendRedirect(request.getContextPath() + "/tarefas");
            return;
        }

        try {
            TarefaDAO dao = new TarefaDAO();
            TarefaBean tarefa = dao.buscarPorId(id);

            if (tarefa == null) {
                HttpSession session = request.getSession();
                session.setAttribute("alertaTipo", "flashErro");
                session.setAttribute("alertaMsg", "Tarefa não encontrada.");
                response.sendRedirect(request.getContextPath() + "/tarefas");
                return;
            }

            request.setAttribute("tarefaSelecionada", tarefa);
            request.setAttribute("abrirModal", acao); // "editar" ou "deletar"

            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (SQLException e) {
            log("Erro ao buscar tarefa", e);

            HttpSession session = request.getSession();
            session.setAttribute("alertaTipo", "erro");
            session.setAttribute("alertaMsg", "Erro ao carregar tarefa. Contate um administrador do sistema.");

            response.sendRedirect(request.getContextPath() + "/tarefas");
        }
    }
}
