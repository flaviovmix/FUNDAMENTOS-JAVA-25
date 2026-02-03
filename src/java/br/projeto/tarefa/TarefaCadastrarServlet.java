package br.projeto.tarefa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/tarefas/cadastrar")
public class TarefaCadastrarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // pega dados do form
        String titulo = request.getParameter("titulo");
        String prioridade = request.getParameter("prioridade");
        String responsavel = request.getParameter("responsavel");
        String descricao = request.getParameter("descricao");
        String statusStr = request.getParameter("status"); // opcional

        // validação mínima
        if (titulo == null || titulo.trim().isEmpty()) {
            request.setAttribute("alertaTipo", "erro");
            request.setAttribute("alertaMsg", "Título é obrigatório.");
            request.getRequestDispatcher("/tarefa_cadastrar.jsp").forward(request, response);
            return;
        }

        int status = 0; // default
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            try {
                status = Integer.parseInt(statusStr.trim());
            } catch (NumberFormatException ignore) {
                status = 0;
            }
        }

        // monta bean
        TarefaBean tarefa = new TarefaBean();
        tarefa.setTitulo(titulo.trim());
        tarefa.setPrioridade(prioridade);     // pode vir null, ok
        tarefa.setResponsavel(responsavel);   // pode vir null, ok
        tarefa.setStatus(status);
        tarefa.setDescricao(descricao);

        // salva
        try {
            TarefaDAO dao = new TarefaDAO();
            dao.inserirTarefa(tarefa);

            // evita re-submit no refresh
            response.sendRedirect(request.getContextPath() + "/tarefas");

        } catch (SQLException e) {
            log("Erro ao cadastrar tarefa", e);

            request.setAttribute("alertaTipo", "erro");
            request.setAttribute("alertaMsg", "Erro ao cadastrar. Contate um administrador do sistema.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }
}
