<%@page import="java.util.List"%>
<%@page import="br.projeto.tarefa.TarefaBean"%>
<%@page import="br.projeto.tarefa.TarefaDAO"%>
<%@page import="br.root.config.ConnectionPool"%>
<%@page import="java.sql.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lista de Tarefas</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">
    </head>
    <body>
        
        <h2>FUNDAMENTOS JAVA 25</h2>
        
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
                    String erro = (String) request.getAttribute("erro");
                    List<TarefaBean> tarefas = (List<TarefaBean>) request.getAttribute("tarefas");
                %>
                    <% if (erro != null) { %>
                        <tr>
                            <td colspan="8" style="text-align:center; font-weight:bold; color:#c00;">
                                <%= erro %>
                            </td>
                        </tr>
                    <% } else if (tarefas == null || tarefas.isEmpty()) { %>
                        <tr>
                            <td colspan="8" style="text-align:center; font-weight:bold;">
                                Nenhuma tarefa cadastrada.
                            </td>
                        </tr>
                    <% } else { 
                        for (TarefaBean tarefa : tarefas) { %>

                        <tr>
                            <td class="prioridade <%= tarefa.getPrioridade() %>"></td>
                            <td><%= tarefa.getId_tarefa() %></td>
                            <td><%= tarefa.getTitulo() %></td>
                            <td><%= tarefa.getPrioridade() %></td>
                            <td><%= tarefa.getResponsavel() %></td>
                            <td><%= tarefa.getStatus() %></td>
                            <td class="btn-action edit"><a href="#"><i class="fa-solid fa-pen"></i></a></td>
                            <td class="btn-action delete"><a href="#"><i class="fa-solid fa-trash"></i></a></td>
                        </tr>

                    <%  } 
                } %>
            </tbody>
        </table>
            
    </body>
</html>