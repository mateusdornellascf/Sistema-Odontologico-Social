const API_FORM = "http://localhost:8080/formulario-saude";
const API_PACIENTE = "http://localhost:8080/paciente";

function montarFormularioPayload() {
    return {
        alergias: document.getElementById("alergiaFormulario").value,
        doencas: document.getElementById("doencasFormulario").value,
        medicamentos: document.getElementById("medicamentoFormulario").value,
    };
}

// ================= CADASTRAR =================
function salvarFormulario() {
    const cpf = document.getElementById("cpfFormulario").value.trim();
    if (!cpf) {
        alert("Informe o CPF do paciente.");
        return;
    }

    fetch(`${API_PACIENTE}/${encodeURIComponent(cpf)}/formulario-saude`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(montarFormularioPayload()),
    })
        .then((res) => res.text())
        .then((msg) => alert(msg))
        .catch((err) => console.error(err));
}

// ================= BUSCAR POR CPF =================
function buscarFormularioPorCpf() {
    const cpf = document.getElementById("cpfBuscarFormulario").value.trim();
    const out = document.getElementById("resultadoBuscaFormulario");
    if (!cpf) {
        out.textContent = "Informe um CPF.";
        return;
    }

    fetch(`${API_FORM}/buscar/${encodeURIComponent(cpf)}`)
        .then((res) => {
            if (!res.ok) {
                out.textContent = "Erro ao buscar formulário.";
                return null;
            }
            return res.json();
        })
        .then((formularios) => {
            if (!formularios) return;
            if (!formularios.length) {
                out.textContent = "Nenhum formulário encontrado para esse paciente.";
                return;
            }
            out.textContent = JSON.stringify(formularios, null, 2);
        })
        .catch((err) => {
            console.error(err);
            out.textContent = "Erro ao buscar formulário.";
        });
}

// ================= LISTAR =================
function listarFormularios() {
    fetch(`${API_FORM}/listar`)
        .then((res) => res.json())
        .then((formularios) => {
            const lista = document.getElementById("listaFormularios");
            lista.innerHTML = "";

            if (!formularios || !formularios.length) {
                const li = document.createElement("li");
                li.textContent = "Nenhum formulário cadastrado.";
                lista.appendChild(li);
                return;
            }

            formularios.forEach((f) => {
                const li = document.createElement("li");
                li.textContent = `ID ${f.idFormulario} | CPF ${f.cpfPaciente} | Alergia: ${f.alergias || "-"} | Doenças: ${f.doencas || "-"} | Medicamento: ${f.medicamentos || "-"}`;
                lista.appendChild(li);
            });
        })
        .catch((err) => console.error(err));
}

// ================= DELETAR POR ID =================
function deletarFormularioPorId() {
    const id = document.getElementById("idDeletarFormulario").value.trim();
    if (!id) {
        alert("Informe o ID do formulário.");
        return;
    }
    if (!confirm(`Deseja realmente deletar o formulário ${id}?`)) return;

    fetch(`${API_FORM}/${encodeURIComponent(id)}`, { method: "DELETE" })
        .then((res) => res.text())
        .then((msg) => {
            alert(msg);
            listarFormularios();
        })
        .catch((err) => console.error(err));
}

// ================= DELETAR POR CPF =================
function deletarFormulariosPorCpf() {
    const cpf = document.getElementById("cpfDeletarFormulario").value.trim();
    if (!cpf) {
        alert("Informe o CPF do paciente.");
        return;
    }
    if (!confirm(`Deseja realmente deletar TODOS os formulários do CPF ${cpf}?`)) return;

    fetch(`${API_FORM}/paciente/${encodeURIComponent(cpf)}`, { method: "DELETE" })
        .then((res) => res.text())
        .then((msg) => {
            alert(msg);
            listarFormularios();
        })
        .catch((err) => console.error(err));
}
