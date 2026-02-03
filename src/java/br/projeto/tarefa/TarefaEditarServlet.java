package br.projeto.tarefa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/tarefas/editar")
public class TarefaEditarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/tarefas");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/tarefas");
            return;
        }

        try {
            TarefaDAO dao = new TarefaDAO();
            TarefaBean tarefa = dao.buscarPorId(id);

            if (tarefa == null) {
                request.setAttribute("alertaTipo", "erro");
                request.setAttribute("alertaMsg", "Tarefa não encontrada.");
            } else {
                // manda a tarefa pro JSP preencher o modal
                request.setAttribute("tarefaEditar", tarefa);
                // flag pra você saber que tem que abrir modal em modo editar
                request.setAttribute("abrirModalEditar", true);
            }

            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (SQLException e) {
            log("Erro ao buscar tarefa para edição", e);
            request.setAttribute("alertaTipo", "erro");
            request.setAttribute("alertaMsg", "Erro ao carregar tarefa. Contate um administrador do sistema.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id_tarefa");
        String titulo = request.getParameter("titulo");
        String prioridade = request.getParameter("prioridade");
        String responsavel = request.getParameter("responsavel");
        String descricao = request.getParameter("descricao");
        String statusStr = request.getParameter("status");

        int id;
        try {
            id = Integer.parseInt(idStr.trim());
        } catch (Exception e) {
            request.setAttribute("alertaTipo", "erro");
            request.setAttribute("alertaMsg", "ID inválido.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            request.setAttribute("alertaTipo", "erro");
            request.setAttribute("alertaMsg", "Título é obrigatório.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }

        int status = 0;
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            try {
                status = Integer.parseInt(statusStr.trim());
            } catch (NumberFormatException ignore) {
                status = 0;
            }
        }

        TarefaBean tarefa = new TarefaBean();
        tarefa.setId_tarefa(id);
        tarefa.setTitulo(titulo.trim());
        tarefa.setPrioridade(prioridade);
        tarefa.setResponsavel(responsavel);
        tarefa.setStatus(status);
        tarefa.setDescricao(descricao);

        try {
            TarefaDAO dao = new TarefaDAO();
            dao.atualizarTarefa(tarefa);

            response.sendRedirect(request.getContextPath() + "/tarefas");

        } catch (SQLException e) {
            log("Erro ao editar tarefa", e);
            request.setAttribute("alertaTipo", "erro");
            request.setAttribute("alertaMsg", "Erro ao editar. Contate um administrador do sistema.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }
}
