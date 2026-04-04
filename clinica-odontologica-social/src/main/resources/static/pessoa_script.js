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

function listar() {
    fetch(API)
        .then((res) => res.json())
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

                const tels = p.telefones ? p.telefones.join(", ") : "sem telefone";

                const ruaTxt = p.rua ? ` | Rua: ${p.rua}` : "";
                li.textContent = `${p.cpf} - ${p.nome}${ruaTxt} | Tel: ${tels}`;
                lista.appendChild(li);
            });
        })
        .catch((err) => console.error(err));
}

function buscarPorCpf() {
    const cpf = document.getElementById("buscarporcpf").value.trim();
    const out = document.getElementById("resultadoBusca");

    if (!cpf) {
        out.textContent = "Informe um CPF.";
        return;
    }

    fetch(`${API}/${encodeURIComponent(cpf)}`)
        .then((res) => {
            if (res.status === 404) {
                out.textContent = "Nenhuma pessoa encontrada com esse CPF.";
                return null;
            }
            if (!res.ok) {
                out.textContent = "Erro ao buscar.";
                return null;
            }
            return res.json();
        })
        .then((p) => {
            if (p === null || !p) return;
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
        .then((msg) => alert(msg));
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
