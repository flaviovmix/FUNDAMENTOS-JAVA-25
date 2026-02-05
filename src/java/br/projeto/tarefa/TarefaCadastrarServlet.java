package br.projeto.tarefa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/tarefas/cadastrar")
public class TarefaCadastrarServlet extends HttpServlet {

    @Override
    protected void doGet(
        HttpServletRequest request, 
        HttpServletResponse response
    ) throws ServletException, IOException {
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request, 
            HttpServletResponse response
    ) throws ServletException, IOException {

        // pega dados do form
        String titulo = request.getParameter("titulo");
        String prioridade = request.getParameter("prioridade");
        String responsavel = request.getParameter("responsavel");
        String descricao = request.getParameter("descricao");
        String statusStr = request.getParameter("status"); 

        if (titulo == null || titulo.trim().isEmpty()) {
            request.setAttribute("alertaTipo", "obrigatorio");
            request.setAttribute("alertaMsg", "Título é obrigatório.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }

        if (prioridade == null || prioridade.trim().isEmpty()) {
            request.setAttribute("alertaTipo", "obrigatorio");
            request.setAttribute("alertaMsg", "Prioridade obrigatória.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }
        
        if (responsavel == null || responsavel.trim().isEmpty()) {
            request.setAttribute("alertaTipo", "obrigatorio");
            request.setAttribute("alertaMsg", "Responsavel é obrigatório.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }
        
        int status;
        try {
            status = Integer.parseInt(statusStr != null ? statusStr.trim() : "");
        } catch (Exception e) {
            request.setAttribute("alertaTipo", "flashErro");
            request.setAttribute("alertaMsg", "Erro ao Editar Tarefa - Status inválido.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        }

        // monta bean
        TarefaBean tarefa = new TarefaBean();
        tarefa.setTitulo(titulo.trim());
        tarefa.setPrioridade(prioridade); 
        tarefa.setResponsavel(responsavel);
        tarefa.setStatus(status);
        tarefa.setDescricao(descricao);

        // salva
        try {
            TarefaDAO dao = new TarefaDAO();
            dao.inserirTarefa(tarefa);

            HttpSession session = request.getSession();
            session.setAttribute("alertaTipo", "sucesso");
            session.setAttribute("alertaMsg", "Tarefa adicionada com sucesso.");

            //PRG - POST REDIRECT GET
            response.sendRedirect(request.getContextPath() + "/tarefas");

        } catch (SQLException e) {
            log("Erro ao cadastrar tarefa", e);

            HttpSession session = request.getSession();
            session.setAttribute("alertaTipo", "erro");
            session.setAttribute("alertaMsg", "Erro ao cadastrar. Contate um administrador do sistema.");

            //PRG - POST REDIRECT GET
            response.sendRedirect(request.getContextPath() + "/tarefas");
        }

    }
}
