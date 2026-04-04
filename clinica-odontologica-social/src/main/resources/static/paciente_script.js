const API = "http://localhost:8080/paciente";

function parseTelefones(text) {
    if (!text || typeof text !== "string") return [];
    return text
        .split(/[\n,;]+/)
        .map((s) => s.trim())
        .filter(Boolean);
}

function telefonesDoCampo(id) {
    return parseTelefones(document.getElementById(id).value);
}

function telefonesParaTextarea(list) {
    if (!list || !list.length) return "";
    return list.join("\n");
}

function cadastrarPaciente() {
    const paciente = {
        cpf: document.getElementById("cpf").value,
        nome: document.getElementById("nome").value,
        cep: document.getElementById("cep").value,
        bairro: document.getElementById("bairro").value,
        numero: document.getElementById("numero").value,
        dataNascimento: document.getElementById("dataNascimento").value,
        numPlanoSaude: document.getElementById("numPlanoSaude").value,
        telefones: telefonesDoCampo("telefonesCadastro")
    };

    fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(paciente)
    })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            document.getElementById("telefonesCadastro").value = "";
        })
        .catch(err => console.error(err));
}

function listarPacientes() {
    fetch(API)
        .then(res => res.json())
        .then(pacientes => {
            const lista = document.getElementById("listaPacientes");
            lista.innerHTML = "";

            pacientes.forEach(p => {
                const li = document.createElement("li");
                const tels = p.telefones && p.telefones.length
                    ? p.telefones.join(", ")
                    : "sem telefone";
                li.textContent = `${p.cpf} - ${p.nome} | Tel: ${tels}`;
                lista.appendChild(li);
            });
        })
        .catch(err => console.error(err));
}

function deletarPacientePorCpf(cpf) {
    fetch(`${API}/${encodeURIComponent(cpf)}`, { method: "DELETE" })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            listarPacientes();
        })
        .catch(err => console.error(err));
}

function buscarPacientePorCpf() {
    const cpf = document.getElementById("buscarporcpf").value.trim();
    const out = document.getElementById("resultadoBuscaPaciente");

    if (!cpf) {
        out.textContent = "Informe um CPF.";
        return;
    }

    fetch(`${API}/${encodeURIComponent(cpf)}`)
        .then(res => {
            if (res.status === 404) {
                out.textContent = "Nenhum paciente encontrado com esse CPF.";
                return null;
            }
            return res.json();
        })
        .then(p => {
            if (p === null || !p) return;
            out.textContent = JSON.stringify(p, null, 2);
        })
        .catch(err => {
            console.error(err);
            out.textContent = "Erro ao buscar.";
        });
}

function carregarParaAtualizarPaciente(cpf) {
    fetch(`${API}/${encodeURIComponent(cpf)}`)
        .then(res => {
            if (res.status === 404) {
                alert("Paciente não encontrado.");
                return null;
            }
            return res.json();
        })
        .then(p => {
            if (!p) return;

            document.getElementById("cpfAtualizar").value = p.cpf;
            document.getElementById("nomeAtualizar").value = p.nome || "";
            document.getElementById("cepAtualizar").value = p.cep || "";
            document.getElementById("bairroAtualizar").value = p.bairro || "";
            document.getElementById("numeroAtualizar").value = p.numero || "";
            document.getElementById("dataNascimentoAtualizar").value = p.dataNascimento || "";
            document.getElementById("numPlanoSaudeAtualizar").value = p.numPlanoSaude || "";
            document.getElementById("telefonesAtualizar").value = telefonesParaTextarea(p.telefones);
        })
        .catch(err => console.error(err));
}

function atualizarPaciente() {
    const cpfBanco = document.getElementById("cpfAtualizar").value.trim();

    if (!cpfBanco) {
        alert("Informe o CPF da pessoa no banco.");
        return;
    }
    const paciente = {
        cpf: cpfBanco,
        nome: document.getElementById("nomeAtualizar").value,
        cep: document.getElementById("cepAtualizar").value,
        bairro: document.getElementById("bairroAtualizar").value,
        numero: document.getElementById("numeroAtualizar").value,
        dataNascimento: document.getElementById("dataNascimentoAtualizar").value,
        numPlanoSaude: document.getElementById("numPlanoSaudeAtualizar").value,
        telefones: telefonesDoCampo("telefonesAtualizar")
    };

    fetch(`${API}/${encodeURIComponent(cpfBanco)}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(paciente)
    })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            listarPacientes();
        })
        .catch(err => console.error(err));
}

