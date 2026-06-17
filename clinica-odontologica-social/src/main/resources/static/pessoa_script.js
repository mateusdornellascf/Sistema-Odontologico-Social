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

function telefonesParaTextarea(list) {
    if (!list || !list.length) return "";
    return list.join("\n");
}

// ================= LISTAR =================

function listar() {
    fetch(API)
        .then((res) => {
            if (!res.ok) {
                throw new Error("Erro ao listar pessoas (HTTP " + res.status + ")");
            }
            return res.json();
        })
        .then((pessoas) => {
            const lista = document.getElementById("lista");
            lista.innerHTML = "";

            if (!pessoas || pessoas.length === 0) {
                const li = document.createElement("li");
                li.textContent = "Nenhuma pessoa cadastrada.";
                lista.appendChild(li);
                return;
            }

            pessoas.forEach((p) => {
                const li = document.createElement("li");
                const tels = p.telefones && p.telefones.length
                    ? p.telefones.join(", ")
                    : "sem telefone";
                li.textContent = `${p.cpf} - ${p.nome} | ${p.rua} | Tel: ${tels}`;
                lista.appendChild(li);
            });
        })
        .catch((err) => {
            console.error(err);
            const lista = document.getElementById("lista");
            lista.innerHTML = "";
            const li = document.createElement("li");
            li.textContent = "Erro ao listar pessoas.";
            lista.appendChild(li);
        });
}

// ================= BUSCAR =================

function buscarPorCpf() {
    const cpf = document.getElementById("buscarporcpf").value.trim();
    if (!cpf) {
        alert("Informe um CPF.");
        return;
    }

    fetch(`${API}/${encodeURIComponent(cpf)}`)
        .then((res) => {
            if (res.status === 404) {
                alert("Pessoa não encontrada.");
                return null;
            }
            return res.json();
        })
        .then((p) => {
            if (!p) {
                document.getElementById("resultadoBusca").textContent =
                    "Nenhuma pessoa encontrada com esse CPF.";
                return;
            }

            const out = document.getElementById("resultadoBusca");
            out.textContent = JSON.stringify(p, null, 2);
        })
        .catch((err) => {
            console.error(err);
            out.textContent = "Erro ao buscar.";
        });
}

// ================= ATUALIZAR =================

function atualizarPessoa() {
    const cpf = document.getElementById("cpfAtualizar").value;

    const pessoa = {
        cpf: cpf,
        nome: document.getElementById("nomeAtualizar").value,
        rua: document.getElementById("ruaAtualizar").value,
        cep: document.getElementById("cepAtualizar").value,
        bairro: document.getElementById("bairroAtualizar").value,
        numero: document.getElementById("numeroAtualizar").value,
        dataNascimento: document.getElementById("dataAtualizar").value,
        telefones: telefonesDoCampo("telefonesAtualizar")
    };

    fetch(`${API}/${encodeURIComponent(cpf)}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pessoa)
    })
        .then((res) => res.text())
        .then((msg) => {
            alert(msg);
            listar();
        })
        .catch((err) => console.error(err));
}

function deletarPorCpf() {
    const cpf = document.getElementById("cpfDeletar").value.trim();
    if (!cpf) {
        alert("Informe o CPF da pessoa a remover.");
        return;
    }

    fetch(`${API}/${encodeURIComponent(cpf)}`, { method: "DELETE" })
        .then((res) => res.text())
        .then((msg) => {
            alert(msg);
            listar();
            const busca = document.getElementById("buscarporcpf").value.trim();
            if (busca === cpf) {
                document.getElementById("resultadoBusca").textContent =
                    "Nenhuma pessoa encontrada com esse CPF.";
            }
        })
        .catch((err) => console.error(err));
}

// ================= CADASTRAR =================

function cadastrar() {
    const pessoa = {
        cpf: document.getElementById("cpf").value,
        nome: document.getElementById("nome").value,
        rua: document.getElementById("rua").value,
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

// ================= CARREGAR PARA ATUALIZAR =================

function carregarParaAtualizar(cpf) {
    fetch(`${API}/${encodeURIComponent(cpf)}`)
        .then((res) => {
            if (res.status === 404) {
                alert("Pessoa não encontrada.");
                return null;
            }
            return res.json();
        })
        .then((p) => {
            if (!p) return;

            document.getElementById("cpfAtualizar").value = p.cpf;
            document.getElementById("nomeAtualizar").value = p.nome || "";
            document.getElementById("cepAtualizar").value = p.cep || "";
            document.getElementById("ruaAtualizar").value = p.rua || "";
            document.getElementById("bairroAtualizar").value = p.bairro || "";
            document.getElementById("numeroAtualizar").value = p.numero || "";
            document.getElementById("dataAtualizar").value = p.dataNascimento || "";
            document.getElementById("telefonesAtualizar").value = telefonesParaTextarea(p.telefones);
        })
        .catch((err) => console.error(err));
}
