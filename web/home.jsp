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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/modalTarefa.css">
    </head>

    <body data-context="${pageContext.request.contextPath}">

    <header>
        <div class="header-inner">
            <button class="btn-add" onclick="novaTarefa()">
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
                              <a href="#"
                                 onclick="editarTarefa(
                                    <%= tarefa.getId_tarefa() %>,
                                    '<%= tarefa.getTitulo().replace("\\", "\\\\").replace("'", "\\'") %>',
                                    '<%= tarefa.getPrioridade() %>',
                                    '<%= tarefa.getResponsavel().replace("\\", "\\\\").replace("'", "\\'") %>',
                                    <%= tarefa.getStatus() %>,
                                    '<%= (tarefa.getDescricao() == null ? "" : tarefa.getDescricao().replace("\\", "\\\\").replace("'", "\\'")) %>'
                                 ); return false;">
                                <i class="fa-solid fa-pen"></i>
                              </a>
                            </td>

                            <td class="btn-action delete">
                              <a href="#"
                                 onclick="excluirTarefa(
                                    <%= tarefa.getId_tarefa() %>,
                                    '<%= tarefa.getTitulo().replace("\\", "\\\\").replace("'", "\\'") %>',
                                    '<%= tarefa.getPrioridade() %>',
                                    '<%= tarefa.getResponsavel().replace("\\", "\\\\").replace("'", "\\'") %>',
                                    <%= tarefa.getStatus() %>,
                                    '<%= (tarefa.getDescricao() == null ? "" : tarefa.getDescricao().replace("\\", "\\\\").replace("'", "\\'")) %>'
                                 ); return false;">
                                <i class="fa-solid fa-trash"></i>
                              </a>
                            </td>

                        </tr>
                    <% } 
                } %>

            </tbody>
        </table>
                
<!--MODAL-->
<div class='overlay-custom' id='modalTarefas' style='display:none;'>
    <div class='box-modal-custom' id='bgModal'>

        <button type="button" class="btn-close-custom" onclick="closeModalTarefas()">×</button>
        <h2 id="tituloModal">Nova Tarefa</h2>

        <form action="" method="post" class="form-tarefa-custom">

            <!-- ✅ ÚNICA COISA NOVA PRA EDITAR FUNCIONAR -->
            <input type="hidden" id="id_tarefa" name="id_tarefa" value="">

            <div class="group-custom">
                <label class="label-custom" for="titulo">Título</label>
                <input type="text" id="titulo" name="titulo" required>
            </div>

            <div class="columns-2-custom">

                <div class="group-custom">
                    <label class="label-custom">Prioridade</label>

                    <div class="radio-custom">

                        <label class="label-custom">
                            <input type="radio" name="prioridade" value="alta" required>
                            Alta
                        </label>

                        <label class="label-custom">
                            <input type="radio" name="prioridade" value="media">
                            Média
                        </label>

                        <label class="label-custom">
                            <input type="radio" name="prioridade" value="baixa">
                            Baixa
                        </label>

                    </div>
                </div>

                <div class="group-custom">
                    <label class="label-custom" for="responsavel">Responsável</label>
                    <input type="text" id="responsavel" name="responsavel" required>
                </div>

            </div>

            <div class="columns-2-custom">

                <div class="group-custom">
                    <label class="label-custom" for="data_criacao">Data de Criação</label>
                    <input type="date" id="data_criacao" name="data_criacao" value="2025-01-01" required>
                </div>

                <div class="group-custom">
                    <label class="label-custom" for="status">Status</label>
                    <select id="status" name="status" required>
                        <option value="">Selecione</option>
                        <option value="0">Rascunho</option>
                        <option value="1">Pendente</option>
                        <option value="2">Concluida</option>
                        <option value="3">Deleta</option>
                    </select>
                </div>

            </div>

            <div class="group-custom">
                <label class="label-custom" for="descricao">Descrição</label>
                <textarea id="descricao" name="descricao" rows="4"></textarea>
            </div>

            <button type="submit" id="botaoConfirmacao" class="btn-save-custom">Salvar Tarefa</button>

        </form>

    </div>
</div>


<!--FIM DO MODAL-->
        
        <script src="./assets/js/modalTarefas.js"></script>
    </body>
</html>