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
        
    <div class="page-header">

        <h1>LISTA DE TAREFAS</h1>

        <div class="legenda-prioridade">
            <span class="legenda-label">níveis de prioridade</span>

            <div class="legenda-badges">
                <span class="badge baixa">baixa</span>
                <span class="badge media">média</span>
                <span class="badge alta">alta</span>
            </div>
        </div>

    </div>

<%
    // flash (pós-ação: deletar/cadastrar/editar)
    String alertaTipo = (String) request.getAttribute("alertaTipo");
    String alertaMsg  = (String) request.getAttribute("alertaMsg");

    // estado da tela (lista vazia)
    String alertaInfoTipo = (String) request.getAttribute("alertaInfoTipo");
    String alertaInfoMsg  = (String) request.getAttribute("alertaInfoMsg");

    List<TarefaBean> tarefas = (List<TarefaBean>) request.getAttribute("tarefas");
%>

<%-- mini-modal APENAS para sucesso (flash) --%>
<% if (alertaMsg != null && "sucesso".equals(alertaTipo)) { %>
    <div class="mini-modal <%= alertaTipo %>" id="miniModal">
        <span><%= alertaMsg %></span>
        <button type="button" onclick="fecharMiniModal()">×</button>
    </div>
<% } %>
<% if (alertaMsg != null && "flashErro".equals(alertaTipo)) { %>
    <div class="mini-modal <%= alertaTipo %>" id="miniModal">
        <span><%= alertaMsg %></span>
        <button type="button" onclick="fecharMiniModal()">×</button>
    </div>
<% } %>

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

        <%-- ERRO (flash) dentro da tabela --%>
        <% if (alertaMsg != null && "erro".equals(alertaTipo)) { %>
            <tr>
                <td class="<%= alertaTipo %>" colspan="8">
                    <%= alertaMsg %>
                </td>
            </tr>
        <% } %>

        <%-- INFO de lista vazia dentro da tabela (estado real) --%>
        <% if (tarefas != null && tarefas.isEmpty() && alertaInfoMsg != null) { %>
            <tr>
                <td class="<%= alertaInfoTipo %>" colspan="8">
                    <%= alertaInfoMsg %>
                </td>
            </tr>
        <% } %>

        <%-- LISTAGEM normal (só quando tem itens) --%>
        <%
            if (tarefas != null && !tarefas.isEmpty()) {
                for (TarefaBean tarefa : tarefas) {
        %>

            <tr class="row-link">
                <td class="prioridade <%= tarefa.getPrioridade() %> cell-link">
                    <a href="tarefa" class="row-anchor"></a>
                </td>
                <td><%= tarefa.getId_tarefa() %></td>
                <td><%= tarefa.getTitulo() %></td>
                <td><%= tarefa.getPrioridade() %></td>
                <td><%= tarefa.getResponsavel() %></td>
                <td><%= tarefa.getStatusText() %></td>

                <!-- EDITAR -->
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

                <!-- EXCLUIR -->
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

        <%
                }
            }
        %>

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
                        <option value="0">ativo</option>
                        <option value="1">inativo</option>
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