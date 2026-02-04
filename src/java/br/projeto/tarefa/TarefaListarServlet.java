package br.projeto.tarefa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@WebServlet("/tarefas")
public class TarefaListarServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        // 1) Flash message (vem da session e vira request por 1 request)
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object tipo = session.getAttribute("alertaTipo");
            Object msg = session.getAttribute("alertaMsg");

            if (tipo != null && msg != null) {
                request.setAttribute("alertaTipo", tipo);
                request.setAttribute("alertaMsg", msg);

                session.removeAttribute("alertaTipo");
                session.removeAttribute("alertaMsg");
            }
        }

        try {
            TarefaDAO dao = new TarefaDAO();
            List<TarefaBean> tarefas = dao.listarTarefas();
            
            if (tarefas.isEmpty()) {
                request.setAttribute("alertaInfoTipo", "info");
                request.setAttribute("alertaInfoMsg", "Nenhuma tarefa cadastrada.");
            }

            request.setAttribute("tarefas", tarefas);
            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (SQLException e) {
            log("Erro ao acessar o banco de dados", e);

            request.setAttribute("tarefas", java.util.Collections.emptyList());
            request.setAttribute("alertaTipo", "erro");
            request.setAttribute(
                    "alertaMsg",
                    "Erro ao conectar ao banco de dados. Contate um administrador do sistema."
            );

            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (Exception e) { // blindagem final
            log("Erro inesperado", e);

            request.setAttribute("tarefas", java.util.Collections.emptyList());
            request.setAttribute("alertaTipo", "erro");
            request.setAttribute(
                    "alertaMsg",
                    "Erro inesperado. Contate um administrador do sistema."
            );

            request.getRequestDispatcher("/home.jsp").forward(request, response);
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
