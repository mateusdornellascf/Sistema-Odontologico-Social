const API = "http://localhost:8080/bd";

function badgeRisco(r) {
  if (!r) return "";
  const t = r.toUpperCase();
  let cls = "sem";
  if (t.includes("ALTO")) cls = "alto";
  else if (t.includes("ATENCAO") || t.includes("ATENÇÃO")) cls = "atencao";
  else if (t.includes("SEM FORM")) cls = "semf";
  return `<span class="badge ${cls}">${t}</span>`;
}

function render(elId, html) {
  document.getElementById(elId).innerHTML = html;
}

function tabela(headers, rows, mapper) {
  if (!rows || rows.length === 0) return "<p><em>Nenhum registro encontrado.</em></p>";
  const ths = headers.map(h => `<th>${h}</th>`).join("");
  const trs = rows.map(r => `<tr>${mapper(r).map(c => `<td>${c ?? ""}</td>`).join("")}</tr>`).join("");
  return `<table class="dados"><thead><tr>${ths}</tr></thead><tbody>${trs}</tbody></table>`;
}

// ---------- FUNÇÕES ----------
function fnCalcularIdade() {
  const cpf = document.getElementById("cpfIdade").value.trim();
  if (!cpf) return alert("Informe o CPF.");
  fetch(`${API}/funcoes/idade/${encodeURIComponent(cpf)}`)
    .then(r => r.json())
    .then(d => {
      const idade = d.idade ?? "<em>(pessoa não encontrada)</em>";
      render("saidaIdade",
        `<span class="kpi">CPF: ${d.cpf}</span><span class="kpi">Idade: ${idade}</span>`);
    })
    .catch(e => render("saidaIdade", `<p style="color:red">Erro: ${e}</p>`));
}

function fnClassificarRisco() {
  const cpf = document.getElementById("cpfRisco").value.trim();
  if (!cpf) return alert("Informe o CPF.");
  fetch(`${API}/funcoes/risco/${encodeURIComponent(cpf)}`)
    .then(r => r.json())
    .then(d => {
      render("saidaRisco",
        `<span class="kpi">CPF: ${d.cpf}</span> Classificação: ${badgeRisco(d.classificacao)}`);
    })
    .catch(e => render("saidaRisco", `<p style="color:red">Erro: ${e}</p>`));
}

// ---------- PROCEDURES ----------
function spGerarAlertas() {
  const data = document.getElementById("dataAlertas").value;
  if (!data) return alert("Informe a data.");
  fetch(`${API}/procedures/gerar-alertas`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ data })
  })
    .then(r => r.json())
    .then(d => {
      render("saidaProcAlertas",
        `<span class="kpi">${d.procedure}</span>
         <span class="kpi">Data: ${d.data}</span>
         <span class="kpi">Alertas na data: ${d.totalAlertasNaData}</span>`);
      carregarAlertas();
    })
    .catch(e => render("saidaProcAlertas", `<p style="color:red">Erro: ${e}</p>`));
}

function carregarAlertas(todos = false) {
  const data = document.getElementById("dataAlertas").value;
  const url = todos || !data ? `${API}/alertas` : `${API}/alertas?data=${data}`;
  fetch(url)
    .then(r => r.json())
    .then(rows => {
      render("tabelaAlertas", tabela(
        ["ID", "Consulta", "CPF Paciente", "CPF Dentista", "Risco", "Mensagem", "Gerado em"],
        rows,
        r => [r.idAlerta, r.idConsulta, r.cpfPaciente, r.cpfDentista,
              badgeRisco(r.classificacaoRisco), r.mensagem,
              (r.dataGeracao || "").replace("T", " ")]));
    })
    .catch(e => render("tabelaAlertas", `<p style="color:red">Erro: ${e}</p>`));
}

function spRemarcar() {
  const idConsulta = document.getElementById("idConsultaSp").value;
  const data = document.getElementById("dataSp").value;
  const hora = document.getElementById("horaSp").value;
  if (!idConsulta || !data || !hora) return alert("Preencha todos os campos.");

  fetch(`${API}/procedures/remarcar`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ idConsulta, data, hora })
  })
    .then(r => r.json())
    .then(d => render("saidaSpRemarcar",
      `<span class="kpi">${d.procedure}</span> ${d.mensagem}`))
    .catch(e => render("saidaSpRemarcar", `<p style="color:red">Erro: ${e}</p>`));
}

// ---------- TRIGGER LOG ----------
function carregarLogConsulta() {
  const id = document.getElementById("idConsultaLog").value;
  const url = id ? `${API}/log-consulta?idConsulta=${id}` : `${API}/log-consulta`;
  fetch(url)
    .then(r => r.json())
    .then(rows => {
      render("tabelaLog", tabela(
        ["ID Log", "Consulta", "CPF Pac.", "CPF Dent.",
         "Data antiga", "Hora antiga", "Data nova", "Hora nova", "Operação", "Registrado em"],
        rows,
        r => [r.idLog, r.idConsulta, r.cpfPaciente, r.cpfDentista,
              r.dataAntiga, r.horaAntiga, r.dataNova, r.horaNova,
              r.operacao, (r.dataLog || "").replace("T", " ")]));
    })
    .catch(e => render("tabelaLog", `<p style="color:red">Erro: ${e}</p>`));
}
