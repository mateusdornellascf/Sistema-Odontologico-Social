const API = "http://localhost:8080/paciente";
const API_FORMULARIO = "http://localhost:8080/formulario-saude";

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
        rua: document.getElementById("rua").value,
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
    fetch(`${API}/listar`)
        .then(res => {
            if (!res.ok) {
                throw new Error("Falha ao listar pacientes (HTTP " + res.status + ").");
            }
            return res.json();
        })
        .then(pacientes => {
            const lista = document.getElementById("listaPacientes");
            lista.innerHTML = "";

            if (!pacientes || pacientes.length === 0) {
                const li = document.createElement("li");
                li.textContent = "Nenhum paciente cadastrado.";
                lista.appendChild(li);
                return;
            }

            pacientes.forEach(p => {
                const li = document.createElement("li");
                const tels = p.telefones && p.telefones.length
                    ? p.telefones.join(", ")
                    : "sem telefone";
                const ruaTxt = p.rua ? ` | Rua: ${p.rua}` : "";
                li.textContent = `${p.cpf} - ${p.nome}${ruaTxt} | Tel: ${tels}`;
                lista.appendChild(li);
            });
        })
        .catch(err => {
            console.error(err);
            const lista = document.getElementById("listaPacientes");
            lista.innerHTML = "";
            const li = document.createElement("li");
            li.textContent = "Erro ao listar pacientes. Verifique se a API está no ar.";
            lista.appendChild(li);
        });
}

function deletarPacientePorCpf() {
    const cpf = document.getElementById("cpfDeletar").value.trim();
    if (!cpf) {
        alert("Informe o CPF do paciente a remover.");
        return;
    }

    fetch(`${API}/${encodeURIComponent(cpf)}`, { method: "DELETE" })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            listarPacientes();
            const busca = document.getElementById("buscarporcpf").value.trim();
            if (busca === cpf) {
                document.getElementById("resultadoBuscaPaciente").textContent =
                    "Nenhum paciente encontrado com esse CPF.";
            }
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
            if (!res.ok) {
                out.textContent = "Erro ao buscar.";
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

function formularioPayloadPaciente() {
    return {
        alergias: document.getElementById("alergiaFormularioPaciente").value,
        doencas: document.getElementById("doencasFormularioPaciente").value,
        medicamentos: document.getElementById("medicamentoFormularioPaciente").value
    };
}

function preencherOuAtualizarFormularioSaude() {
    const cpf = document.getElementById("cpfFormularioPaciente").value.trim();
    const out = document.getElementById("resultadoFormularioPaciente");

    if (!cpf) {
        out.textContent = "Informe o CPF do paciente.";
        return;
    }

    fetch(`${API}/${encodeURIComponent(cpf)}/formulario-saude`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formularioPayloadPaciente())
    })
        .then(res => res.text())
        .then(msg => {
            out.textContent = msg;
            alert(msg);
        })
        .catch(err => {
            console.error(err);
            out.textContent = "Erro ao salvar formulario.";
        });
}

function carregarFormularioSaudePaciente() {
    const cpf = document.getElementById("cpfFormularioPaciente").value.trim();
    const out = document.getElementById("resultadoFormularioPaciente");

    if (!cpf) {
        out.textContent = "Informe o CPF do paciente.";
        return;
    }

    fetch(`${API_FORMULARIO}/buscar/${encodeURIComponent(cpf)}`)
        .then(res => {
            if (!res.ok) {
                out.textContent = "Erro ao buscar formulario.";
                return null;
            }
            return res.json();
        })
        .then(formularios => {
            if (!formularios) return;
            if (!formularios.length) {
                out.textContent = "Paciente sem formulario cadastrado.";
                return;
            }

            const formulario = formularios[formularios.length - 1];
            document.getElementById("alergiaFormularioPaciente").value = formulario.alergias || "";
            document.getElementById("doencasFormularioPaciente").value = formulario.doencas || "";
            document.getElementById("medicamentoFormularioPaciente").value = formulario.medicamentos || "";
            out.textContent = JSON.stringify(formulario, null, 2);
        })
        .catch(err => {
            console.error(err);
            out.textContent = "Erro ao buscar formulario.";
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
            document.getElementById("ruaAtualizar").value = p.rua || "";
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
        rua: document.getElementById("ruaAtualizar").value,
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

