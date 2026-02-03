package br.projeto.tarefa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/tarefas")
public class TarefaListarServlet extends HttpServlet {

    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        // 1️⃣ Mensagens vindas de ações anteriores (flash message)
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object tipo = session.getAttribute("alertaTipo");
            Object msg  = session.getAttribute("alertaMsg");

            if (tipo != null && msg != null) {
                request.setAttribute("alertaTipo", tipo);
                request.setAttribute("alertaMsg", msg);

                session.removeAttribute("alertaTipo");
                session.removeAttribute("alertaMsg");
            }
        }

        try {
            // 2️⃣ Regra principal: listar tarefas
            TarefaDAO dao = new TarefaDAO();
            List<TarefaBean> tarefas = dao.listarTarefas();

            if (tarefas == null || tarefas.isEmpty()) {
                request.setAttribute("alertaTipo", "info");
                request.setAttribute("alertaMsg", "Nenhuma tarefa cadastrada.");
            } else {
                request.setAttribute("tarefas", tarefas);
            }

            // 3️⃣ Renderização da tela
            request.getRequestDispatcher("/home.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            // 4️⃣ Log técnico (não vai pro usuário)
            Throwable causa = e.getCause();
            if (causa instanceof javax.naming.NamingException) {
                log("JNDI indisponível", e);
            } else {
                log("Erro SQL", e);
            }

            // 5️⃣ Mensagem amigável pro usuário
            request.setAttribute("alertaTipo", "erro");
            request.setAttribute(
                "alertaMsg",
                "Erro ao conectar ao banco de dados. Contate um administrador do sistema."
            );

            request.getRequestDispatcher("/home.jsp")
                   .forward(request, response);
        }
    }
}



//@WebServlet("/tarefas")
//public class TarefaListarServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(
//            HttpServletRequest request, 
//            HttpServletResponse response
//        ) throws ServletException, IOException {
//
//      throw new RuntimeException("Teste de erro 500");
//    }
//}
