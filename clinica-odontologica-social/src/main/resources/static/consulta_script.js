const API = "http://localhost:8080/consulta";

function textoResposta(res) {
    const ct = res.headers.get("Content-Type") || "";
    if (ct.includes("application/json")) {
        return res.json().then((j) => JSON.stringify(j, null, 2));
    }
    return res.text();
}

function formatarConsulta(c) {
    return `#${c.idConsulta} | paciente ${c.idPaciente} | dentista ${c.idDentista} | ${c.data} ${c.hora}`;
}

function preencherLista(ulId, consultas) {
    const lista = document.getElementById(ulId);
    lista.innerHTML = "";

    if (!consultas || consultas.length === 0) {
        const li = document.createElement("li");
        li.textContent = "Nenhuma consulta.";
        lista.appendChild(li);
        return;
    }

    consultas.forEach((c) => {
        const li = document.createElement("li");
        li.textContent = formatarConsulta(c);
        lista.appendChild(li);
    });
}
function criarConsulta() {
    const body = {
        idPaciente: document.getElementById("cpfPacienteCriar").value.trim(),
        idDentista: document.getElementById("cpfDentistaCriar").value.trim(),
        dataConsulta: document.getElementById("dataConsultaCriar").value.trim(),
        horaConsulta: document.getElementById("horaConsultaCriar").value.trim(),
    };

    fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
    })
        .then((res) => textoResposta(res).then((msg) => ({ ok: res.ok, msg })))
        .then(({ msg }) => {
            alert(msg);
        })
        .catch((err) => console.error(err));
}

function listarConsultas() {
    fetch(API)
        .then((res) => {
            if (!res.ok) return textoResposta(res).then((t) => Promise.reject(t));
            return res.json();
        })
        .then((consultas) => preencherLista("listaConsultas", consultas))
        .catch((err) => console.error(err));
}

function remarcarConsulta() {
    const id = document.getElementById("idRemarcar").value.trim();

    if (!id) {
        alert("Informe o ID da consulta.");
        return;
    }

    const body = {
        data: document.getElementById("novaDataRemarcar").value.trim(),
        hora: document.getElementById("novaHoraRemarcar").value.trim(),
    };

    fetch(`${API}/remarcar/${encodeURIComponent(id)}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
    })
        .then((res) => textoResposta(res).then((msg) => ({ ok: res.ok, msg })))
        .then(({ msg }) => {
            alert(msg);
        })
        .catch((err) => console.error(err));
}
function deletarConsulta() {
    const id = document.getElementById("idDeletar").value.trim();

    if (!id) {
        alert("Informe o ID da consulta.");
        return;
    }

    fetch(`${API}/${encodeURIComponent(id)}`, { method: "DELETE" })
        .then((res) => textoResposta(res).then((msg) => ({ ok: res.ok, msg })))
        .then(({ msg }) => {
            alert(msg);
        })
        .catch((err) => console.error(err));
}

function listarConsultasDentista() {
    const cpf = document.getElementById("cpfDentistaListar").value.trim();

    if (!cpf) {
        alert("Informe o CPF do dentista.");
        return;
    }

    fetch(`${API}/dentista/${encodeURIComponent(cpf)}`)
        .then((res) => {
            if (!res.ok) return textoResposta(res).then((t) => Promise.reject(t));
            return res.json();
        })
        .then((consultas) => preencherLista("listaConsultasDentista", consultas))
        .catch((err) => {
            console.error(err);
            alert(typeof err === "string" ? err : "Erro ao listar.");
        });
}

function listarConsultasPaciente() {
    const cpf = document.getElementById("cpfPacienteListar").value.trim();

    if (!cpf) {
        alert("Informe o CPF do paciente.");
        return;
    }

    fetch(`${API}/paciente/${encodeURIComponent(cpf)}`)
        .then((res) => {
            if (!res.ok) return textoResposta(res).then((t) => Promise.reject(t));
            return res.json();
        })
        .then((consultas) => preencherLista("listaConsultasPaciente", consultas))
        .catch((err) => {
            console.error(err);
            alert(typeof err === "string" ? err : "Erro ao listar.");
        });
}

// ===================== PROCEDIMENTOS =====================

const API_PROCEDIMENTO = "http://localhost:8080/consultas/{idConsulta}/procedimentos";

// Mostrar / esconder menu
function toggleProcedimentos() {
    const menu = document.getElementById("menuProcedimentos");
    menu.style.display = menu.style.display === "none" ? "block" : "none";
}

// Criar procedimentos
function criarProcedimentoCirurgico() {
    fetch(`${API_PROCEDIMENTO}/cirurgico`, { method: "POST" })
        .then((res) => textoResposta(res).then((msg) => ({ msg })))
        .then(({ msg }) => alert(msg))
        .catch((err) => console.error(err));
}

function criarProcedimentoEstetico() {
    fetch(`${API_PROCEDIMENTO}/estetico`, { method: "POST" })
        .then((res) => textoResposta(res).then((msg) => ({ msg })))
        .then(({ msg }) => alert(msg))
        .catch((err) => console.error(err));
}

function criarProcedimentoRotina() {
    fetch(`${API_PROCEDIMENTO}/rotina`, { method: "POST" })
        .then((res) => textoResposta(res).then((msg) => ({ msg })))
        .then(({ msg }) => alert(msg))
        .catch((err) => console.error(err));
}

// Listar procedimentos
function listarProcedimentos() {
    fetch(API_PROCEDIMENTO)
        .then((res) => {
            if (!res.ok) return textoResposta(res).then((t) => Promise.reject(t));
            return res.json();
        })
        .then((dados) => {
            console.log(dados);
            alert("Procedimentos listados no console");
        })
        .catch((err) => console.error(err));
}

// Atualizar procedimento
function atualizarProcedimento() {
    const id = prompt("Informe o ID do procedimento:");
    if (!id) {
        alert("ID obrigatório");
        return;
    }

    fetch(`${API_PROCEDIMENTO}/${encodeURIComponent(id)}`, {
        method: "PUT"
    })
        .then((res) => textoResposta(res).then((msg) => ({ msg })))
        .then(({ msg }) => alert(msg))
        .catch((err) => console.error(err));
}

// Deletar procedimento
function deletarProcedimento() {
    const id = prompt("Informe o ID do procedimento:");
    if (!id) {
        alert("ID obrigatório");
        return;
    }

    fetch(`${API_PROCEDIMENTO}/${encodeURIComponent(id)}`, {
        method: "DELETE"
    })
        .then((res) => textoResposta(res).then((msg) => ({ msg })))
        .then(({ msg }) => alert(msg))
        .catch((err) => console.error(err));
}

// Buscar por consulta
function buscarPorConsulta() {
    const idConsulta = prompt("Informe o ID da consulta:");
    if (!idConsulta) {
        alert("ID obrigatório");
        return;
    }

    fetch(`${API_PROCEDIMENTO}/consulta/${encodeURIComponent(idConsulta)}`)
        .then((res) => {
            if (!res.ok) return textoResposta(res).then((t) => Promise.reject(t));
            return res.json();
        })
        .then((dados) => {
            console.log(dados);
            alert("Resultado no console");
        })
        .catch((err) => console.error(err));
}

// Buscar por ID
function buscarPorId() {
    const id = prompt("Informe o ID do procedimento:");
    if (!id) {
        alert("ID obrigatório");
        return;
    }

    fetch(`${API_PROCEDIMENTO}/${encodeURIComponent(id)}`)
        .then((res) => {
            if (!res.ok) return textoResposta(res).then((t) => Promise.reject(t));
            return res.json();
        })
        .then((dados) => {
            console.log(dados);
            alert("Resultado no console");
        })
        .catch((err) => console.error(err));
}