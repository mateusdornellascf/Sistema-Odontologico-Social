const API = "http://localhost:8080/dentista";

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

function cadastrarDentista() {
  const coordenadorInput = document.getElementById("coordenador").value;

  const dentista = {
    cpf: document.getElementById("cpf").value,
    nome: document.getElementById("nome").value,
    rua: document.getElementById("rua").value,
    cep: document.getElementById("cep").value,
    bairro: document.getElementById("bairro").value,
    numero: document.getElementById("numero").value,
    dataNascimento: document.getElementById("dataNascimento").value,
    cro: document.getElementById("cro").value,
    especialidade: document.getElementById("especialidade").value,
    email: document.getElementById("email").value,
    coordenador: coordenadorInput === "" ? null : coordenadorInput,
    telefones: telefonesDoCampo("telefonesCadastro"),
  };

  fetch(API, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dentista),
  })
    .then((res) => res.text())
    .then((msg) => {
      alert(msg);
      document.getElementById("telefonesCadastro").value = "";
    })
    .catch((err) => console.error(err));
}

function listarDentistas() {
  fetch(API)
    .then((res) => res.json())
    .then((dentistas) => {
      const lista = document.getElementById("listaDentistas");
      lista.innerHTML = "";

      if (!dentistas || dentistas.length === 0) {
        const li = document.createElement("li");
        li.textContent = "Nenhum dentista cadastrado.";
        lista.appendChild(li);
        return;
      }

      dentistas.forEach((d) => {
        const li = document.createElement("li");
        const tels =
          d.telefones && d.telefones.length
            ? d.telefones.join(", ")
            : "sem telefone";
        const ruaTxt = d.rua ? ` | Rua: ${d.rua}` : "";
        li.textContent = `${d.cpf} - ${d.nome} (${d.especialidade || "—"})${ruaTxt} | Tel: ${tels}`;
        lista.appendChild(li);
      });
    })
    .catch((err) => console.error(err));
}

function deletarDentistaPorCpf() {
  const cpf = document.getElementById("cpfDeletar").value.trim();
  if (!cpf) {
    alert("Informe o CPF do dentista a remover.");
    return;
  }

  fetch(`${API}/${encodeURIComponent(cpf)}`, { method: "DELETE" })
    .then((res) => res.text())
    .then((msg) => {
      alert(msg);
      listarDentistas();
      const busca = document.getElementById("buscarporcpf").value.trim();
      if (busca === cpf) {
        document.getElementById("resultadoBuscaDentista").textContent =
          "Nenhum dentista encontrado com esse CPF.";
      }
    })
    .catch((err) => console.error(err));
}

function buscarDentistaPorCpf() {
  const cpf = document.getElementById("buscarporcpf").value.trim();
  const out = document.getElementById("resultadoBuscaDentista");

  if (!cpf) {
    out.textContent = "Informe um CPF.";
    return;
  }

  fetch(`${API}/${encodeURIComponent(cpf)}`)
    .then((res) => {
      if (res.status === 404) {
        out.textContent = "Nenhum dentista encontrado com esse CPF.";
        return null;
      }
      if (!res.ok) {
        out.textContent = "Erro ao buscar.";
        return null;
      }
      return res.json();
    })
    .then((d) => {
      if (d === null || !d) return;
      out.textContent = JSON.stringify(d, null, 2);
    })
    .catch((err) => {
      console.error(err);
      out.textContent = "Erro ao buscar.";
    });
}

function formatarDataParaInput(val) {
  if (val == null || val === "") return "";
  if (typeof val === "number") {
    const d = new Date(val);
    if (!isNaN(d.getTime())) {
      return d.toISOString().slice(0, 10);
    }
  }
  if (typeof val === "string" && /^\d{4}-\d{2}-\d{2}/.test(val)) {
    return val.slice(0, 10);
  }
  return String(val);
}

function carregarParaAtualizarDentista(cpf) {
  fetch(`${API}/${encodeURIComponent(cpf)}`)
    .then((res) => {
      if (res.status === 404) {
        alert("Dentista não encontrado.");
        return null;
      }
      return res.json();
    })
    .then((d) => {
      if (!d) return;

      document.getElementById("cpfAtualizar").value = d.cpf;
      document.getElementById("nomeAtualizar").value = d.nome || "";
      document.getElementById("cepAtualizar").value = d.cep || "";
      document.getElementById("ruaAtualizar").value = d.rua || "";
      document.getElementById("bairroAtualizar").value = d.bairro || "";
      document.getElementById("numeroAtualizar").value = d.numero || "";
      document.getElementById("dataNascimentoAtualizar").value =
        formatarDataParaInput(d.dataNascimento);
      document.getElementById("croAtualizar").value = d.cro || "";
      document.getElementById("especialidadeAtualizar").value =
        d.especialidade || "";
      document.getElementById("emailAtualizar").value = d.email || "";
      document.getElementById("coordenadorAtualizar").value =
        d.coordenador || "";
      document.getElementById("telefonesAtualizar").value =
        telefonesParaTextarea(d.telefones);
    })
    .catch((err) => console.error(err));
}

function atualizarDentista() {
  const cpfBanco = document.getElementById("cpfAtualizar").value.trim();

  if (!cpfBanco) {
    alert("Informe o CPF do dentista no banco.");
    return;
  }

  const coordenadorInput = document.getElementById(
    "coordenadorAtualizar",
  ).value;

  const dentista = {
    cpf: cpfBanco,
    nome: document.getElementById("nomeAtualizar").value,
    rua: document.getElementById("ruaAtualizar").value,
    cep: document.getElementById("cepAtualizar").value,
    bairro: document.getElementById("bairroAtualizar").value,
    numero: document.getElementById("numeroAtualizar").value,
    dataNascimento: document.getElementById("dataNascimentoAtualizar").value,
    cro: document.getElementById("croAtualizar").value,
    especialidade: document.getElementById("especialidadeAtualizar").value,
    email: document.getElementById("emailAtualizar").value,
    coordenador: coordenadorInput === "" ? null : coordenadorInput,
    telefones: telefonesDoCampo("telefonesAtualizar"),
  };

  fetch(`${API}/${encodeURIComponent(cpfBanco)}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dentista),
  })
    .then((res) => res.text())
    .then((msg) => {
      alert(msg);
      listarDentistas();
    })
    .catch((err) => console.error(err));
}
