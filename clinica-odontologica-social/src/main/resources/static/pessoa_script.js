const API = "http://localhost:8080/pessoa";

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

function cadastrar() {
    const pessoa = {
        cpf: document.getElementById("cpf").value,
        nome: document.getElementById("nome").value,
        cep: document.getElementById("cep").value,
        bairro: document.getElementById("bairro").value,
        numero: document.getElementById("numero").value,
        dataNascimento: document.getElementById("data").value,
        telefones: telefonesDoCampo("telefonesCadastro")
    };

    fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pessoa)
    })
        .then((res) => res.text())
        .then((msg) => {
            alert(msg);
            document.getElementById("telefonesCadastro").value = "";
        })
        .catch((err) => console.error(err));
}

function listar() {
    fetch(API)
        .then((res) => res.json())
        .then((pessoas) => {
            const lista = document.getElementById("lista");
            lista.innerHTML = "";

            pessoas.forEach((p) => {
                const li = document.createElement("li");

                const tels = p.telefones ? p.telefones.join(", ") : "sem telefone";

                li.textContent = `${p.cpf} - ${p.nome} | Tel: ${tels}`;
                lista.appendChild(li);
            });
        });
}
function buscarPorCpf() {
    const cpf = document.getElementById("buscarporcpf").value.trim();
    const out = document.getElementById("resultadoBusca");

    fetch(`${API}/${cpf}`)
        .then((res) => res.json())
        .then((p) => {
            out.textContent = JSON.stringify(p, null, 2);
        });
}

// ================= ATUALIZAR =================

function atualizarPessoa() {
    const cpf = document.getElementById("cpfAtualizar").value;

    const pessoa = {
        cpf: cpf,
        nome: document.getElementById("nomeAtualizar").value,
        cep: document.getElementById("cepAtualizar").value,
        bairro: document.getElementById("bairroAtualizar").value,
        numero: document.getElementById("numeroAtualizar").value,
        dataNascimento: document.getElementById("dataAtualizar").value,
        telefones: telefonesDoCampo("telefonesAtualizar")
    };

    fetch(`${API}/${cpf}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pessoa)
    })
        .then((res) => res.text())
        .then((msg) => alert(msg));
}

function deletarPorCpf() {
    const cpf = document.getElementById("cpfDeletar").value;

    fetch(`${API}/${cpf}`, { method: "DELETE" })
        .then((res) => res.text())
        .then((msg) => alert(msg));
}
