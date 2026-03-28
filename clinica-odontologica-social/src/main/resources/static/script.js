const API = "http://localhost:8080/pessoa";

function cadastrar() {
    const pessoa = {
        cpf: document.getElementById("cpf").value,
        nome: document.getElementById("nome").value,
        cep: document.getElementById("cep").value,
        bairro: document.getElementById("bairro").value,
        numero: document.getElementById("numero").value,
        dataNascimento: document.getElementById("data").value
    };

    fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pessoa)
    })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
        })
        .catch(err => console.error(err));
}

function listar() {
    fetch(API)
        .then(res => res.json())
        .then(pessoas => {
            const lista = document.getElementById("lista");
            lista.innerHTML = "";
            pessoas.forEach(p => {
                const li = document.createElement("li");
                li.textContent = `${p.cpf} - ${p.nome}`;
                lista.appendChild(li);
            });
        })
        .catch(err => console.error(err));
}

function buscarPorCpf() {
    const cpf = document.getElementById("buscarporcpf").value.trim();
    const out = document.getElementById("resultadoBusca");
    if (!cpf) {
        out.textContent = "Informe um CPF.";
        return;
    }
    fetch(`${API}/${encodeURIComponent(cpf)}`)
        .then(res => {
            if (res.status === 404) {
                out.textContent = "Nenhuma pessoa encontrada com esse CPF.";
                return null;
            }
            return res.json();
        })
        .then(p => {
            if (p === null) return;
            if (!p || !p.cpf) {
                out.textContent = "Nenhuma pessoa encontrada com esse CPF.";
                return;
            }
            out.textContent = JSON.stringify(p, null, 2);
        })
        .catch(err => {
            console.error(err);
            out.textContent = "Erro ao buscar.";
        });
}

function atualizarPessoa() {
    const cpfBanco = document.getElementById("cpfAtualizar").value.trim();
    if (!cpfBanco) {
        alert("Informe o CPF da pessoa no banco.");
        return;
    }
    const pessoa = {
        cpf: cpfBanco,
        nome: document.getElementById("nomeAtualizar").value,
        cep: document.getElementById("cepAtualizar").value,
        bairro: document.getElementById("bairroAtualizar").value,
        numero: document.getElementById("numeroAtualizar").value,
        dataNascimento: document.getElementById("dataAtualizar").value
    };

    fetch(`${API}/${encodeURIComponent(cpfBanco)}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(pessoa)
    })
        .then(res => res.text())
        .then(msg => alert(msg))
        .catch(err => console.error(err));
}

function deletarPorCpf() {
    const cpf = document.getElementById("cpfDeletar").value.trim();
    if (!cpf) {
        alert("Informe o CPF.");
        return;
    }
    fetch(`${API}/${encodeURIComponent(cpf)}`, { method: "DELETE" })
        .then(res => res.text())
        .then(msg => alert(msg))
        .catch(err => console.error(err));
}
