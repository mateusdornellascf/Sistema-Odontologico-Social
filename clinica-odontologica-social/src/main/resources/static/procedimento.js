const API = "http://localhost:8080/procedimentos";

let tipoSelecionado = "";

// ================= UI =================
function toggleProcedimentos() {
  const menu = document.getElementById("menuProcedimentos");
  menu.style.display = menu.style.display === "none" ? "block" : "none";
}

function selecionarTipo(tipo) {
  tipoSelecionado = tipo.toLowerCase();

  document.getElementById("formDinamico").style.display = "block";
  document.getElementById("tituloDinamico").innerText =
    "Preencher procedimento " + tipo;
}

// ================= SALVAR =================
async function salvarOuAtualizarFormulario() {
  try {
    const idConsulta = document.getElementById("idConsulta").value;

    if (!idConsulta) {
      alert("Informe o ID da consulta!");
      return;
    }

    let body = {
      idConsulta: parseInt(idConsulta),
      nomeProcedimento: prompt("Nome do procedimento:"),
      descricao: prompt("Descrição:"),
    };

    // CAMPOS ESPECÍFICOS
    if (tipoSelecionado === "cirurgico") {
      body.dataCirurgia = prompt("Data (YYYY-MM-DD):");
      body.cpfCirurgiaoDentista = prompt("CPF do dentista:");
      body.valor = parseFloat(prompt("Valor:"));
    }

    if (tipoSelecionado === "estetico") {
      body.valor = parseFloat(prompt("Valor:"));
    }

    const response = await fetch(`${API}/${tipoSelecionado}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });

    if (!response.ok) {
      throw new Error("Erro ao salvar");
    }

    const data = await response.json();

    alert(data.mensagem);
  } catch (error) {
    console.error(error);
    alert("Erro ao salvar!");
  }
}

// ================= RESTO =================
function listarProcedimentos() {
  fetch(API)
    .then((res) => res.json())
    .then((procedimentos) => {
      const lista = document.getElementById("listaProcedimentos");
      lista.innerHTML = "";

      if (!procedimentos || procedimentos.length === 0) {
        const li = document.createElement("li");
        li.textContent = "Nenhum procedimento cadastrado.";
        lista.appendChild(li);
        return;
      }

      procedimentos.forEach((p) => {
        const li = document.createElement("li");

        const nome = p.nomeProcedimento || "Sem nome";
        const desc = p.descricao || "Sem descrição";
        const idConsulta = p.idConsulta ? ` | Consulta: ${p.idConsulta}` : "";

        li.textContent = `ID: ${p.idProcedimento} - ${nome} | ${desc}${idConsulta}`;

        lista.appendChild(li);
      });
    })
    .catch((err) => console.error(err));
}

function buscarPorId() {
  const id = prompt("ID:");
  fetch(`${API}/${id}`)
    .then((res) => res.json())
    .then((data) => console.log(data));
}

function buscarPorConsulta() {
  const id = prompt("ID consulta:");
  fetch(`${API}/consulta/${id}`)
    .then((res) => res.json())
    .then((data) => console.log(data));
}

function atualizarProcedimento() {
  const id = prompt("ID:");
  fetch(`${API}/${id}`, { method: "PUT" })
    .then((res) => res.text())
    .then(alert);
}

function deletarProcedimento() {
  const id = prompt("ID:");
  fetch(`${API}/${id}`, { method: "DELETE" })
    .then((res) => res.text())
    .then(alert);
}
