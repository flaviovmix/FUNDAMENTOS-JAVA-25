package br.projeto.tarefa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/tarefas/editar")
public class TarefaEditarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String idStr = request.getParameter("id_tarefa");
        String titulo = request.getParameter("titulo");
        String prioridade = request.getParameter("prioridade");
        String responsavel = request.getParameter("responsavel");
        String descricao = request.getParameter("descricao");
        String statusStr = request.getParameter("status");

        // --- validações ---
        int id;
        try {
            id = Integer.parseInt(idStr != null ? idStr.trim() : "");
        } catch (Exception e) {
            session.setAttribute("alertaTipo", "flashErro");
            session.setAttribute("alertaMsg", "Erro ao Editar Tarefa - ID inválido.");
            response.sendRedirect(request.getContextPath() + "/tarefas");
            return;
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            session.setAttribute("alertaTipo", "flashErro");
            session.setAttribute("alertaMsg", "Título é obrigatório.");
            response.sendRedirect(request.getContextPath() + "/tarefas");
            return;
        }

        if (prioridade == null || prioridade.trim().isEmpty()) {
            session.setAttribute("alertaTipo", "flashErro");
            session.setAttribute("alertaMsg", "Prioridade obrigatória.");
            response.sendRedirect(request.getContextPath() + "/tarefas");
            return;
        }

        if (responsavel == null || responsavel.trim().isEmpty()) {
            session.setAttribute("alertaTipo", "flashErro");
            session.setAttribute("alertaMsg", "Responsável é obrigatório.");
            response.sendRedirect(request.getContextPath() + "/tarefas");
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

        // --- monta bean ---
        TarefaBean tarefa = new TarefaBean();
        tarefa.setId_tarefa(id);
        tarefa.setTitulo(titulo.trim());
        tarefa.setPrioridade(prioridade);
        tarefa.setResponsavel(responsavel.trim());
        tarefa.setStatus(status);
        tarefa.setDescricao(descricao); // pode ser null/"" mesmo

        // --- salva ---
        try {
            TarefaDAO dao = new TarefaDAO();
            dao.atualizarTarefa(tarefa);

            session.setAttribute("alertaTipo", "sucesso");
            session.setAttribute("alertaMsg", "Tarefa editada com sucesso.");

        } catch (SQLException e) {
            log("Erro ao editar tarefa", e);

            session.setAttribute("alertaTipo", "erro");
            session.setAttribute("alertaMsg", "Erro ao editar. Contate um administrador do sistema.");
        }

        // PRG
        response.sendRedirect(request.getContextPath() + "/tarefas");
    }
}
