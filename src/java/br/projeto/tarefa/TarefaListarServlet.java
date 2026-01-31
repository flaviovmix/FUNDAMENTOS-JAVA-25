package br.projeto.tarefa;

import br.root.config.ConnectionPool;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // se quiser DI de verdade: crie/salve o pool no ServletContext e pegue daqui.
            ConnectionPool pool = new ConnectionPool();
            TarefaDAO dao = new TarefaDAO(pool);

            List<TarefaBean> tarefas = dao.listarTarefas();
            request.setAttribute("tarefas", tarefas);

            request.getRequestDispatcher("/home.jsp").forward(request, response);

        } catch (SQLException e) {
            // log do servidor (NetBeans/Tomcat) com stacktrace completo
            log("Erro ao listar tarefas", e);

            // mensagem curta pro usuário
            request.setAttribute("erro", "Erro ao conectar ao banco de dados. Contate um administrador do sistema.");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        }
    }
}
