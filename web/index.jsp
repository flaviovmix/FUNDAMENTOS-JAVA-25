<%@page import="br.root.config.ConnectionPool"%>
<%@page import="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lista de Tarefas</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
        <link rel="stylesheet" href="./assets/style.css"/>
    </head>
    <body>
        
        <h2>FUNDAMENTOS JAVA 25</h2>
        
        <table class="task-table">
            
            <thead>
                <tr>
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
                    String sql = "SELECT * FROM tarefas";
                    try (
                            Connection con = ConnectionPool.getConexao(); 
                            PreparedStatement ps = con.prepareStatement(sql); 
                            ResultSet rs = ps.executeQuery();
                        ) {
                        while (rs.next()) { %>
                            <tr>
                                <td><%= rs.getInt("id_tarefa")%></td>
                                <td><%= rs.getString("titulo")%></td>
                                <td><%= rs.getString("prioridade")%></td>
                                <td><%= rs.getString("responsavel")%></td>
                                <td>
                                    <%= rs.getInt("status") == 0 ? "inativo" : "ativo"%>
                                </td>
                                <td class="btn-action edit">
                                    <a href="#"><i class="fa-solid fa-pen"></i></a>
                                </td>
                                <td class="btn-action delete">
                                    <a href="#"><i class="fa-solid fa-trash"></i></a>
                                </td>
                            </tr>
                        <% }
                        } catch (Exception e) {
                            out.println("Erro: " + e.getMessage());
                        } %>
            </tbody>
            
        </table>
            
    </body>
</html>