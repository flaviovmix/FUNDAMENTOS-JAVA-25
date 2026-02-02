package br.projeto.tarefa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        try {
            TarefaDAO dao = new TarefaDAO();
            List<TarefaBean> tarefas = dao.listarTarefas();

            if (tarefas == null || tarefas.isEmpty()) {
                request.setAttribute("alertaTipo", "info");
                request.setAttribute("alertaMsg", "Nenhuma tarefa cadastrada.");
            } else {
                request.setAttribute("tarefas", tarefas);
            }

            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (SQLException e) {
            Throwable causa = e.getCause();
            if (causa instanceof javax.naming.NamingException) {
                log("JNDI indisponível: " + causa.getMessage(), e);
            } else {
                log("SQL erro: " + e.getMessage(), e);
            }

            request.setAttribute("alertaTipo", "erro");
            request.setAttribute("alertaMsg",
                "Erro ao conectar ao banco de dados. Contate um administrador do sistema."
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
