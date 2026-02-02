<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                <c:choose>
                    <c:when test="${not empty erro}">
                        <tr>
                            <td colspan="8" style="text-align:center; font-weight:bold; color:#c00;">
                                ${erro}
                            </td>
                        </tr>
                    </c:when>

                    <c:when test="${empty tarefas}">
                        <tr>
                            <td colspan="8" style="text-align:center; font-weight:bold;">
                                Nenhuma tarefa cadastrada.
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                    <c:forEach var="tarefa" items="${tarefas}">
                        <tr>
                            <td class="prioridade ${tarefa.prioridade}"></td>
                            <td>${tarefa.id_tarefa}</td>
                            <td>${tarefa.titulo}</td>
                            <td>${tarefa.prioridade}</td>
                            <td>${tarefa.responsavel}</td>
                            <td>${tarefa.status}</td>

                            <td class="btn-action edit">
                                <a href="#">
                                    <i class="fa-solid fa-pen"></i>
                                </a>
                            </td>

                            <td class="btn-action delete">
                                <a href="#"
                                   onclick="document.getElementById('del-${tarefa.id_tarefa}').submit(); return false;">
                                    <i class="fa-solid fa-trash"></i>
                                </a>

                                <form id="del-${tarefa.id_tarefa}"
                                      action="${pageContext.request.contextPath}/tarefas/deletar"
                                      method="post"
                                      style="display:none;">
                                    <input type="hidden" name="id" value="${tarefa.id_tarefa}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

    </body>
</html>
