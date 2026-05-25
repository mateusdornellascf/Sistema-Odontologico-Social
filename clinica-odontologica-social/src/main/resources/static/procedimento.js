const API = "http://localhost:8080/procedimentos";

// ================= UI: exibir campos do tipo escolhido =================
function atualizarCamposTipo() {
    const tipo = document.getElementById("tipoProcedimento").value;
    document.getElementById("camposCirurgico").style.display = tipo === "cirurgico" ? "block" : "none";
    document.getElementById("camposEstetico").style.display  = tipo === "estetico"  ? "block" : "none";
    document.getElementById("camposRotina").style.display    = tipo === "rotina"    ? "block" : "none";
}

// ================= CADASTRAR =================
function cadastrarProcedimento() {
    const tipo = document.getElementById("tipoProcedimento").value;
    const idConsulta = document.getElementById("idConsulta").value;

    if (!tipo) {
        alert("Selecione o tipo do procedimento.");
        return;
    }
    if (!idConsulta) {
        alert("Informe o ID da consulta.");
        return;
    }

    const base = {
        idConsulta: parseInt(idConsulta, 10),
        nomeProcedimento: document.getElementById("nomeProcedimento").value,
        descricao: document.getElementById("descricao").value
    };

    let body;
    if (tipo === "cirurgico") {
        body = {
            ...base,
            dataCirurgia: document.getElementById("dataCirurgia").value,
            cpfCirurgiaoDentista: document.getElementById("cpfCirurgiaoDentista").value,
            valor: parseFloat(document.getElementById("valorCirurgico").value)
        };
    } else if (tipo === "estetico") {
        body = {
            ...base,
            dataSessoes: document.getElementById("dataSessoes").value,
            quantidadeSessoes: parseInt(document.getElementById("quantidadeSessoes").value, 10),
            valor: parseFloat(document.getElementById("valorEstetico").value)
        };
    } else {
        body = {
            ...base,
            dataProcedimentoRotina: document.getElementById("dataProcedimentoRotina").value,
            status: document.getElementById("statusRotina").value,
            valor: parseFloat(document.getElementById("valorRotina").value)
        };
    }

    fetch(`${API}/${tipo}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
    })
        .then(res => {
            if (!res.ok) throw new Error("Erro ao cadastrar");
            return res.json();
        })
        .then(data => alert(data.mensagem || "Procedimento cadastrado!"))
        .catch(err => {
            console.error(err);
            alert("Erro ao cadastrar procedimento.");
        });
}

// ================= LISTAR =================
function listarProcedimentos() {
    fetch(API)
        .then(res => res.json())
        .then(procedimentos => {
            const lista = document.getElementById("listaProcedimentos");
            lista.innerHTML = "";

            if (!procedimentos || procedimentos.length === 0) {
                const li = document.createElement("li");
                li.textContent = "Nenhum procedimento cadastrado.";
                lista.appendChild(li);
                return;
            }

            procedimentos.forEach(p => {
                const li = document.createElement("li");
                const nome = p.nomeProcedimento || "Sem nome";
                const desc = p.descricao || "Sem descrição";
                const cons = p.idConsulta ? ` | Consulta: ${p.idConsulta}` : "";
                li.textContent = `ID: ${p.idProcedimento} - ${nome} | ${desc}${cons}`;
                lista.appendChild(li);
            });
        })
        .catch(err => console.error(err));
}

// ================= BUSCAR POR ID =================
function buscarPorIdProcedimento() {
    const id = document.getElementById("buscarPorId").value.trim();
    const out = document.getElementById("resultadoBuscaId");

    if (!id) {
        out.textContent = "Informe um ID.";
        return;
    }

    fetch(`${API}/${encodeURIComponent(id)}`)
        .then(res => {
            if (res.status === 404) {
                out.textContent = "Nenhum procedimento encontrado com esse ID.";
                return null;
            }
            if (!res.ok) {
                out.textContent = "Erro ao buscar.";
                return null;
            }
            return res.json();
        })
        .then(p => {
            if (!p) return;
            out.textContent = JSON.stringify(p, null, 2);
        })
        .catch(err => {
            console.error(err);
            out.textContent = "Erro ao buscar.";
        });
}

// ================= BUSCAR POR CONSULTA =================
function buscarPorIdConsulta() {
    const idConsulta = document.getElementById("buscarPorConsulta").value.trim();
    const out = document.getElementById("resultadoBuscaConsulta");

    if (!idConsulta) {
        out.textContent = "Informe o ID da consulta.";
        return;
    }

    fetch(`${API}/consulta/${encodeURIComponent(idConsulta)}`)
        .then(res => {
            if (!res.ok) {
                out.textContent = "Erro ao buscar.";
                return null;
            }
            return res.json();
        })
        .then(lista => {
            if (!lista) return;
            if (lista.length === 0) {
                out.textContent = "Nenhum procedimento para essa consulta.";
                return;
            }
            out.textContent = JSON.stringify(lista, null, 2);
        })
        .catch(err => {
            console.error(err);
            out.textContent = "Erro ao buscar.";
        });
}

// ================= ATUALIZAR =================
function atualizarProcedimento() {
    const id = document.getElementById("idAtualizar").value.trim();

    if (!id) {
        alert("Informe o ID do procedimento.");
        return;
    }

    const procedimento = {
        idProcedimento: parseInt(id, 10),
        nomeProcedimento: document.getElementById("nomeAtualizar").value,
        descricao: document.getElementById("descricaoAtualizar").value
    };

    fetch(`${API}/${encodeURIComponent(id)}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(procedimento)
    })
        .then(res => res.text())
        .then(msg => {
            alert(msg === "OK" ? "Procedimento atualizado com sucesso!" : "Não foi possível atualizar.");
            listarProcedimentos();
        })
        .catch(err => {
            console.error(err);
            alert("Erro ao atualizar.");
        });
}

// ================= DELETAR =================
function deletarProcedimento() {
    const id = document.getElementById("idDeletar").value.trim();

    if (!id) {
        alert("Informe o ID do procedimento.");
        return;
    }

    if (!confirm(`Deseja realmente deletar o procedimento ${id}?`)) return;

    fetch(`${API}/${encodeURIComponent(id)}`, { method: "DELETE" })
        .then(res => res.text())
        .then(msg => {
            alert(msg === "OK" ? "Procedimento deletado com sucesso!" : "Não foi possível deletar.");
            listarProcedimentos();
        })
        .catch(err => {
            console.error(err);
            alert("Erro ao deletar.");
        });
}
