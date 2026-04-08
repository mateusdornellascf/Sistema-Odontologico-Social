const API_FORM = "http://localhost:8080/formulario-saude";
const API_PACIENTE = "http://localhost:8080/paciente";

function montarFormularioPayload() {
  return {
    alergias: document.getElementById("alergiaFormulario").value,
    doencas: document.getElementById("doencasFormulario").value,
    medicamentos: document.getElementById("medicamentoFormulario").value,
  };
}

function salvarOuAtualizarFormulario() {
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
        out.textContent = "Erro ao buscar formulario.";
        return null;
      }
      return res.json();
    })
    .then((formularios) => {
      if (!formularios) return;
      if (!formularios.length) {
        out.textContent = "Nenhum formulario encontrado para esse paciente.";
        return;
      }

      const ultimo = formularios[formularios.length - 1];
      out.textContent = JSON.stringify(ultimo, null, 2);
    })
    .catch((err) => {
      console.error(err);
      out.textContent = "Erro ao buscar formulario.";
    });
}

function listarFormularios() {
  fetch(`${API_FORM}/listar`)
    .then((res) => res.json())
    .then((formularios) => {
      const lista = document.getElementById("listaFormularios");
      lista.innerHTML = "";

      if (!formularios || !formularios.length) {
        const li = document.createElement("li");
        li.textContent = "Nenhum formulario cadastrado.";
        lista.appendChild(li);
        return;
      }

      formularios.forEach((f) => {
        const li = document.createElement("li");
        li.textContent = `ID ${f.idFormulario} | CPF ${f.cpfPaciente} | Alergia: ${f.alergias || "-"} | Doencas: ${f.doencas || "-"} | Medicamento: ${f.medicamentos || "-"}`;
        lista.appendChild(li);
      });
    })
    .catch((err) => console.error(err));
}
