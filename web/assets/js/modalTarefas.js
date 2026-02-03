function openModalTarefas() {
    document.getElementById('modalTarefas').style.display = 'flex';
}

function closeModalTarefas() {
    document.getElementById('modalTarefas').style.display = 'none';
}

function novaTarefa() {
    setCamposHabilitados(true);
    document.getElementById("botaoConfirmacao").classList.remove("btn-excluirTarefa");

    const ctx = document.body.dataset.context;

    document.getElementById("tituloModal").innerText = "Nova Tarefa";
    document.getElementById("botaoConfirmacao").innerText = "Salvar Tarefa";
    document.getElementById("botaoConfirmacao").classList.add("btn-novaTarefa");
    document.getElementById("botaoConfirmacao").classList.remove("btn-editarTarefa");
    
    document.getElementById("bgModal").classList.add("bg-nova");
    document.getElementById("bgModal").classList.remove("bg-editar");
    document.getElementById("bgModal").classList.remove("bg-excluir");
    
    document.querySelector(".form-tarefa-custom").reset();

    document.getElementById("titulo").value = "";
    document.getElementById("responsavel").value = "";
    document.getElementById("descricao").value = "";
    document.getElementById("data_criacao").value = "";
    document.getElementById("status").value = "";

    document.querySelector(".form-tarefa-custom").action = ctx + "/tarefas/cadastrar";

    openModalTarefas();
}

function editarTarefa(id, titulo, prioridade, responsavel, status, descricao) {
    setCamposHabilitados(true);
    document.getElementById("botaoConfirmacao").classList.remove("btn-excluirTarefa");

    const ctx = document.body.dataset.context;

    document.getElementById("tituloModal").innerText = "Editar Tarefa";
    document.getElementById("botaoConfirmacao").innerText = "Salvar Alterações";
    document.getElementById("botaoConfirmacao").classList.add("btn-editarTarefa");
    document.getElementById("botaoConfirmacao").classList.remove("btn-novaTarefa");
    
    document.getElementById("bgModal").classList.remove("bg-nova");
    document.getElementById("bgModal").classList.add("bg-editar");
    document.getElementById("bgModal").classList.remove("bg-excluir");

    document.getElementById("id_tarefa").value = id;
    document.getElementById("titulo").value = titulo || "";
    document.getElementById("responsavel").value = responsavel || "";
    document.getElementById("descricao").value = descricao || "";
    document.getElementById("status").value = (status != null ? String(status) : "");

    // prioridade (radio)
    const radios = document.querySelectorAll('input[name="prioridade"]');
    radios.forEach(r => r.checked = (r.value === prioridade));

    document.querySelector(".form-tarefa-custom").action = ctx + "/tarefas/editar";

    openModalTarefas();
}

function excluirTarefa(id, titulo, prioridade, responsavel, status, descricao) {

    const ctx = document.body.dataset.context;

    document.getElementById("tituloModal").innerText = "Excluir Tarefa";
    document.getElementById("botaoConfirmacao").innerText = "Confirmar Exclusão";

    // classes do botão (pra cor)
    document.getElementById("botaoConfirmacao").classList.add("btn-excluirTarefa");
    document.getElementById("botaoConfirmacao").classList.remove("btn-novaTarefa");
    document.getElementById("botaoConfirmacao").classList.remove("btn-editarTarefa");
    
    document.getElementById("bgModal").classList.remove("bg-nova");
    document.getElementById("bgModal").classList.remove("bg-editar");
    document.getElementById("bgModal").classList.add("bg-excluir");

    // preenche igual ao editar
    document.getElementById("id_tarefa").value = id;
    document.getElementById("titulo").value = titulo || "";
    document.getElementById("responsavel").value = responsavel || "";
    document.getElementById("descricao").value = descricao || "";
    document.getElementById("status").value = (status != null ? String(status) : "");

    // prioridade (radio)
    document.querySelectorAll('input[name="prioridade"]').forEach(r => {
        r.checked = (r.value === prioridade);
    });

    // action para deletar
    document.querySelector(".form-tarefa-custom").action = ctx + "/tarefas/deletar";

    // trava tudo (mas mantém o hidden id_tarefa enviando)
    setCamposHabilitados(false);

    openModalTarefas();
}

function setCamposHabilitados(habilitar) {
    document.getElementById("titulo").disabled = !habilitar;
    document.getElementById("responsavel").disabled = !habilitar;
    document.getElementById("descricao").disabled = !habilitar;
    document.getElementById("data_criacao").disabled = !habilitar; // se você usa esse campo
    document.getElementById("status").disabled = !habilitar;

    document.querySelectorAll('input[name="prioridade"]').forEach(r => {
        r.disabled = !habilitar;
    });
}
