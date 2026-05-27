const BASE = "http://localhost:8080";

function render(id, html) { document.getElementById(id).innerHTML = html; }
function kpi(id, kpis) {
  render(id, kpis.map(([l, v]) => `<span class="kpi">${l}: ${v}</span>`).join(" "));
}
function tabela(headers, rows, mapper) {
  if (!rows || rows.length === 0) return "<p><em>Nenhum registro encontrado.</em></p>";
  const ths = headers.map(h => `<th>${h}</th>`).join("");
  const trs = rows.map(r => `<tr>${mapper(r).map(c => `<td>${c ?? ""}</td>`).join("")}</tr>`).join("");
  return `<table class="dados"><thead><tr>${ths}</tr></thead><tbody>${trs}</tbody></table>`;
}
function badgeRisco(t) {
  if (!t) return "";
  const s = String(t).toUpperCase();
  let cls = "sem";
  if (s.includes("ALTO")) cls = "alto";
  else if (s.includes("ATEN")) cls = "atencao";
  else if (s.includes("SEM FORM")) cls = "semf";
  return `<span class="badge ${cls}">${s}</span>`;
}

// ============= VIEW 1 =============
async function carregarAlertaSaude() {
  try {
    const r = await fetch(`${BASE}/formulario-saude/alerta-saude`);
    const data = await r.json();
    if (!Array.isArray(data)) {
      kpi("kpiAlertaSaude", [["Total", 0]]);
      render("tabelaAlertaSaude", `<p><em>${data}</em></p>`);
      return;
    }
    const filtro = (document.getElementById("filtroAlertaNome").value || "").toLowerCase();
    const rows = filtro
      ? data.filter(d => (d.nome || "").toLowerCase().includes(filtro))
      : data;
    kpi("kpiAlertaSaude", [["Total na view", data.length], ["Após filtro", rows.length]]);
    render("tabelaAlertaSaude", tabela(
      ["CPF", "Nome", "Consulta", "Data", "Hora", "Alergia", "Doenças", "Medicamento"],
      rows,
      r => [r.cpf, r.nome, r.idConsulta, r.dataConsulta, r.horaConsulta,
            r.alergia, r.doencas, r.medicamento]));
  } catch (e) {
    render("tabelaAlertaSaude", `<p style="color:red">Erro: ${e}</p>`);
  }
}

// ============= VIEW 2 =============
async function carregarDentistasSemFutura() {
  try {
    const r = await fetch(`${BASE}/dentista/sem-consulta-futura`);
    const data = await r.json();
    if (!Array.isArray(data)) {
      kpi("kpiDentSemFutura", [["Total", 0]]);
      render("tabelaDentSemFutura", `<p><em>${data}</em></p>`);
      return;
    }
    kpi("kpiDentSemFutura", [["Dentistas sem agenda futura", data.length]]);
    render("tabelaDentSemFutura", tabela(
      ["CPF", "Nome", "CRO", "Especialidade", "Email", "Coordenador"],
      data,
      r => [r.cpf, r.nome, r.cro, r.especialidade, r.email, r.coordenador || "—"]));
  } catch (e) {
    render("tabelaDentSemFutura", `<p style="color:red">Erro: ${e}</p>`);
  }
}

// ============= CONSULTA 1 =============
async function carregarDentistasAtivos() {
  const n = document.getElementById("minConsultas").value || 1;
  try {
    const r = await fetch(`${BASE}/dentista/mais-ativos?minConsultas=${n}`);
    const data = await r.json();
    if (!Array.isArray(data)) {
      render("tabelaDentAtivos", `<p><em>${data}</em></p>`);
      kpi("kpiDentAtivos", [["Resultados", 0]]);
      return;
    }
    const totais = data.map(d => d.totalConsultas);
    const max = totais.length ? Math.max(...totais) : 0;
    const media = totais.length ? (totais.reduce((a, b) => a + b, 0) / totais.length).toFixed(1) : 0;
    kpi("kpiDentAtivos",
        [["N mínimo", n], ["Dentistas", data.length], ["Máx consultas", max], ["Média", media]]);
    render("tabelaDentAtivos", tabela(
      ["CPF", "Nome", "Especialidade", "CRO", "Total Consultas"],
      data,
      r => [r.cpf, r.nome, r.especialidade, r.cro, r.totalConsultas]));
  } catch (e) {
    render("tabelaDentAtivos", `<p style="color:red">Erro: ${e}</p>`);
  }
}

// ============= CONSULTA 2 =============
async function carregarHistorico() {
  const cpf = document.getElementById("cpfHistorico").value.trim();
  if (!cpf) return alert("Informe o CPF do paciente.");
  try {
    const r = await fetch(`${BASE}/consultas/historico/${encodeURIComponent(cpf)}`);
    const data = await r.json();
    if (!Array.isArray(data)) {
      render("tabelaHistorico", `<p><em>${data}</em></p>`);
      kpi("kpiHistorico", [["Total", 0]]);
      return;
    }
    kpi("kpiHistorico", [["CPF", cpf], ["Total de consultas", data.length]]);
    render("tabelaHistorico", tabela(
      ["ID", "Paciente", "Dentista", "Data", "Hora"],
      data,
      r => [r.idConsulta, r.nomePaciente, r.nomeDentista,
            r.dataConsulta, r.horaConsulta]));
  } catch (e) {
    render("tabelaHistorico", `<p style="color:red">Erro: ${e}</p>`);
  }
}

// ============= CONSULTA 3 =============
async function carregarPacSemConsulta() {
  try {
    const r = await fetch(`${BASE}/paciente/sem-consulta`);
    const data = await r.json();
    if (!Array.isArray(data)) {
      render("tabelaPacSemConsulta", `<p><em>${data}</em></p>`);
      kpi("kpiPacSemConsulta", [["Total", 0]]);
      return;
    }
    kpi("kpiPacSemConsulta", [["Pacientes sem consulta", data.length]]);
    render("tabelaPacSemConsulta", tabela(
      ["CPF", "Nome", "Bairro", "CEP", "Plano de Saúde"],
      data,
      r => [r.cpf, r.nome, r.bairro, r.cep, r.numPlanoSaude || "—"]));
  } catch (e) {
    render("tabelaPacSemConsulta", `<p style="color:red">Erro: ${e}</p>`);
  }
}

// ============= CONSULTA 4 =============
async function carregarProcDentMaisAtivo() {
  try {
    const r = await fetch(`${BASE}/procedimentos/dentista-mais-ativo`);
    const data = await r.json();
    if (!Array.isArray(data)) {
      render("tabelaProcDent", `<p><em>${data}</em></p>`);
      kpi("kpiProcDent", [["Total", 0]]);
      return;
    }
    const dent = data[0]?.nomeDentista || "—";
    kpi("kpiProcDent", [["Dentista mais ativo", dent], ["Procedimentos", data.length]]);
    render("tabelaProcDent", tabela(
      ["ID Proc.", "ID Consulta", "Nome", "Descrição", "CPF Dentista", "Dentista"],
      data,
      r => [r.idProcedimento, r.idConsulta, r.nomeProcedimento,
            r.descricao, r.cpfDentista, r.nomeDentista]));
  } catch (e) {
    render("tabelaProcDent", `<p style="color:red">Erro: ${e}</p>`);
  }
}
