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
                <tr>
                    <td>1</td>
                    <td>TÍTULO DA TAREFA</td>
                    <td>Média</td>
                    <td>Nome do Responsável</td>
                    <td>Status da Tarefa</td>

                    <td class="btn-action edit">
                        <a href="#"><i class="fa-solid fa-pen"></i></a>
                    </td>

                    <td class="btn-action delete">
                        <a href="#"><i class="fa-solid fa-trash"></i></a>
                    </td>
                </tr>
            </tbody>

        </table>

    </body>  
    
</html>