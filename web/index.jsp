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
                    String url = "jdbc:postgresql://localhost:5432/DB_GENERICO";
                    String user = "postgres";
                    String pass = "1234";

                    Connection con = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;

                    try {
                        Class.forName("org.postgresql.Driver");
                        con = DriverManager.getConnection(url, user, pass);

                        String sql = "SELECT * FROM tarefas";
                        ps = con.prepareStatement(sql);
                        rs = ps.executeQuery();
                        while (rs.next()) { %> 
                            <tr>
                                <td><%= rs.getInt("id_tarefa") %></td> 
                                <td><%= rs.getString("titulo") %></td> 
                                <td><%= rs.getString("prioridade") %></td> 
                                <td><%= rs.getString("responsavel") %></td> 

                                <% if (rs.getInt("status") == 0) { %>
                                    <td>inativo</td>
                                <% } else { %>
                                    <td>ativo</td>
                                <% } %>

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
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                        if (con != null) {
                            con.close();
                        }
                    }

                %> 
            </tbody>

        </table>

    </body>  
    
</html>