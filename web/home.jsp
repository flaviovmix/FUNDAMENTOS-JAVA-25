<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="br.projeto.tarefa.TarefaBean"%>
<%@page import="br.projeto.tarefa.TarefaDAO"%>
<%@page import="br.root.config.ConnectionPool"%>
<%@page import="java.sql.*" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lista de Tarefas</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
    </head>
    <body>

    <header>
        <div class="header-inner">
            <button class="btn-add">
                <i class="fa-solid fa-plus"></i>
                Nova Tarefa
            </button>
            <h2>FUNDAMENTOS JAVA 25</h2>
        </div>
    </header>

    <main>

        <h1>LISTA DE TAREFAS</h1>
        
        <table class="task-table">
            
            <thead>
                <tr>
                    <th></th>
                    <th>ID</th>
                    <th>Título</th>
                    <th>Prioridade</th>
                    <th>Responsável</th>
                    <th>Status</th>
                    <th>Editar</th>
                    <th>Excluir</th>
                </tr>
            </thead>
            
            <tbody>
                <%
                    String alertaTipo = (String) request.getAttribute("alertaTipo");
                    String alertaMsg  = (String) request.getAttribute("alertaMsg");
                    List<TarefaBean> tarefas = (List<TarefaBean>) request.getAttribute("tarefas");
                %>

                <% if (alertaMsg != null) { %>
                <tr>
                    <td class="<%= alertaTipo %>" colspan="8">
                        <%= alertaMsg %>
                    </td>
                </tr>

                <% } else { 
                    for (TarefaBean tarefa : tarefas) { %>
                        <tr class="row-link">
                            <td class="prioridade <%= tarefa.getPrioridade() %> cell-link">
                                <a href="tarefa" class="row-anchor"></a>
                            </td>
                            <td><%= tarefa.getId_tarefa() %></td>
                            <td><%= tarefa.getTitulo() %></td>
                            <td><%= tarefa.getPrioridade() %></td>
                            <td><%= tarefa.getResponsavel() %></td>
                            <td><%= tarefa.getStatusText() %></td>
                            <td class="btn-action edit">
                                <a href="#"><i class="fa-solid fa-pen"></i></a>
                            </td>
                            <td class="btn-action delete">
                                <a href="#"
                                   onclick="document.getElementById('del-<%= tarefa.getId_tarefa() %>').submit(); return false;">
                                    <i class="fa-solid fa-trash"></i>
                                </a>

                                <form id="del-<%= tarefa.getId_tarefa() %>"
                                      action="<%= request.getContextPath() %>/tarefas/deletar"
                                      method="post"
                                      style="display:none;">
                                    <input type="hidden" name="id-tarefa" value="<%= tarefa.getId_tarefa() %>">
                                </form>
                            </td>
                        </tr>
                    <% } 
                } %>

            </tbody>
        </table>
            
    </body>
</html>