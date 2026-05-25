const BASE = "http://localhost:8080/dashboard";

// paleta consistente
const PAL = {
  primary: "#0077b6",
  primary2: "#00b4d8",
  ok: "#2a9d8f",
  warn: "#f4a261",
  danger: "#c1121f",
  muted: "#888",
  palette: ["#0077b6", "#00b4d8", "#90e0ef", "#023e8a", "#2a9d8f",
            "#f4a261", "#e76f51", "#c1121f", "#9d4edd", "#43aa8b"]
};

// guarda referência das charts para destruir antes de redesenhar
const charts = {};
function makeChart(id, config) {
  if (charts[id]) charts[id].destroy();
  charts[id] = new Chart(document.getElementById(id), config);
}

async function getJson(url) {
  const r = await fetch(url);
  if (!r.ok) throw new Error("HTTP " + r.status);
  return r.json();
}

function brl(v) {
  if (v == null) return "R$ 0,00";
  return v.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
}

// ====================== KPIs ======================
async function carregarKpis() {
  const r = await fetch(`${BASE}/resumo`);
  const d = await r.json();
  const grid = document.getElementById("kpiGrid");
  const cards = [
    ["Pacientes",             d.totalPacientes,          ""],
    ["Dentistas",             d.totalDentistas,          "sec"],
    ["Consultas",             d.totalConsultas,          "sec"],
    ["Procedimentos",         d.totalProcedimentos,      ""],
    ["Pessoas (total)",       d.totalPessoas,            "sec"],
    ["Formulários de saúde",  d.totalFormularios,        "ok"],
    ["% Pac. c/ formulário",  d.pctPacientesComFormulario + "%", "ok"],
    ["Ticket médio cirúrgico", brl(d.ticketMedioCirurgico), "warn"],
    ["Ticket médio estético", brl(d.ticketMedioEstetico), "warn"],
    ["Ticket médio rotina",   brl(d.ticketMedioRotina),  "warn"],
    ["Faturamento total",     brl(d.valorTotalGeral),    "ok"],
  ];
  grid.innerHTML = cards.map(([l, v, cls]) => `
    <div class="kpi-card ${cls}">
      <div class="label">${l}</div>
      <div class="value">${v ?? "—"}</div>
    </div>`).join("");
}

// ====================== 1. Linha consultas/mês ======================
async function carregarLinha() {
  const ano = document.getElementById("anoFiltro").value;
  const url = ano ? `${BASE}/consultas-por-mes?ano=${ano}` : `${BASE}/consultas-por-mes`;
  const d = await (await fetch(url)).json();
  makeChart("chartLinhaMes", {
    type: "line",
    data: {
      labels: d.map(x => x.mes),
      datasets: [{
        label: "Consultas",
        data: d.map(x => x.total),
        borderColor: PAL.primary,
        backgroundColor: "rgba(0,119,182,0.15)",
        fill: true, tension: 0.3, pointRadius: 4
      }]
    },
    options: { responsive: true, plugins: { legend: { display: false } } }
  });
}

// ====================== 2. Barras dentistas ======================
async function carregarBarrasDent() {
  const limit = document.getElementById("topDentistas").value || 10;
  const d = await (await fetch(`${BASE}/consultas-por-dentista?limit=${limit}`)).json();
  makeChart("chartBarrasDent", {
    type: "bar",
    data: {
      labels: d.map(x => x.nome),
      datasets: [{
        label: "Consultas",
        data: d.map(x => x.total),
        backgroundColor: PAL.primary
      }]
    },
    options: {
      responsive: true,
      plugins: { legend: { display: false } },
      scales: { y: { beginAtZero: true, ticks: { precision: 0 } } }
    }
  });
}

// ====================== 3. Pizza risco ======================
async function carregarPizzaRisco() {
  const d = await (await fetch(`${BASE}/risco-distribuicao`)).json();
  makeChart("chartPizzaRisco", {
    type: "pie",
    data: {
      labels: d.map(x => x.classificacao),
      datasets: [{
        data: d.map(x => x.total),
        backgroundColor: d.map(x => {
          const s = (x.classificacao || "").toUpperCase();
          if (s.includes("ALTO"))    return PAL.danger;
          if (s.includes("ATEN"))    return PAL.warn;
          if (s.includes("SEM FORM")) return PAL.muted;
          return PAL.ok;
        })
      }]
    },
    options: { responsive: true }
  });
}

// ====================== 4. Donut procedimentos ======================
async function carregarDonut() {
  const d = await (await fetch(`${BASE}/procedimentos-por-tipo`)).json();
  makeChart("chartDonutProc", {
    type: "doughnut",
    data: {
      labels: d.map(x => `${x.tipo} (médio ${brl(x.valorMedio)})`),
      datasets: [{
        data: d.map(x => x.total),
        backgroundColor: [PAL.primary, PAL.warn, PAL.ok]
      }]
    },
    options: { responsive: true }
  });
}

// ====================== 5. Radar especialidade ======================
async function carregarRadar() {
  const d = await (await fetch(`${BASE}/consultas-por-especialidade`)).json();
  makeChart("chartRadarEsp", {
    type: "radar",
    data: {
      labels: d.map(x => x.especialidade),
      datasets: [{
        label: "Consultas",
        data: d.map(x => x.total),
        backgroundColor: "rgba(0,119,182,0.25)",
        borderColor: PAL.primary,
        pointBackgroundColor: PAL.primary
      }]
    },
    options: {
      responsive: true,
      scales: { r: { beginAtZero: true, ticks: { precision: 0 } } }
    }
  });
}

// ====================== 6. Histograma idade + stats ======================
async function carregarIdade() {
  const d = await (await fetch(`${BASE}/idade-stats`)).json();
  makeChart("chartHistIdade", {
    type: "bar",
    data: {
      labels: d.histograma.map(x => x.faixa),
      datasets: [{
        label: "Pacientes",
        data: d.histograma.map(x => x.total),
        backgroundColor: PAL.primary2
      }]
    },
    options: {
      responsive: true,
      plugins: { legend: { display: false } },
      scales: { y: { beginAtZero: true, ticks: { precision: 0 } } }
    }
  });
  document.getElementById("statsIdade").innerHTML = `
    <span>Amostra: ${d.amostra}</span>
    <span>Média: ${d.media}</span>
    <span>Mediana: ${d.mediana}</span>
    <span>Moda: ${d.moda}</span>
    <span>Variância: ${d.variancia}</span>
    <span>Desvio: ${d.desvio}</span>
    <span>Mín–Máx: ${d.min}–${d.max}</span>
  `;
}

// ====================== 7. Scatter consultas x procedimentos ======================
async function carregarScatter() {
  const d = await (await fetch(`${BASE}/correlacao-consultas-procedimentos`)).json();
  makeChart("chartScatter", {
    type: "scatter",
    data: {
      datasets: [{
        label: "Dentistas",
        data: d.map(x => ({ x: x.totalConsultas, y: x.totalProcedimentos, nome: x.nome })),
        backgroundColor: PAL.primary,
        pointRadius: 6
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: { display: false },
        tooltip: {
          callbacks: {
            label: ctx => `${ctx.raw.nome}: ${ctx.raw.x} consultas / ${ctx.raw.y} procs`
          }
        }
      },
      scales: {
        x: { title: { display: true, text: "Total de consultas" }, beginAtZero: true, ticks: { precision: 0 } },
        y: { title: { display: true, text: "Total de procedimentos" }, beginAtZero: true, ticks: { precision: 0 } }
      }
    }
  });
}

// ====================== 8. Bairros ======================
async function carregarBairros() {
  const d = await (await fetch(`${BASE}/pacientes-por-bairro`)).json();
  makeChart("chartBairros", {
    type: "bar",
    data: {
      labels: d.map(x => x.bairro),
      datasets: [{
        label: "Pacientes",
        data: d.map(x => x.total),
        backgroundColor: PAL.palette
      }]
    },
    options: {
      indexAxis: "y",
      responsive: true,
      plugins: { legend: { display: false } },
      scales: { x: { beginAtZero: true, ticks: { precision: 0 } } }
    }
  });
}

// ====================== Orquestrador ======================
function marcarErro(canvasId, motivo) {
  const canvas = document.getElementById(canvasId);
  if (!canvas) return;
  const card = canvas.closest(".chart-card");
  if (!card) return;
  // remove canvas problemático para não ficar branco
  canvas.style.display = "none";
  const msg = document.createElement("div");
  msg.style.cssText = "color:#c1121f;font-size:0.85rem;background:#fde2e4;padding:8px;border-radius:6px;";
  msg.textContent = "⚠ Não foi possível carregar este gráfico: " + motivo;
  card.appendChild(msg);
}

async function atualizarTudo() {
  const tarefas = [
    ["kpis",       carregarKpis,       null],
    ["linhaMes",   carregarLinha,      "chartLinhaMes"],
    ["barrasDent", carregarBarrasDent, "chartBarrasDent"],
    ["pizzaRisco", carregarPizzaRisco, "chartPizzaRisco"],
    ["donut",      carregarDonut,      "chartDonutProc"],
    ["radar",      carregarRadar,      "chartRadarEsp"],
    ["idade",      carregarIdade,      "chartHistIdade"],
    ["scatter",    carregarScatter,    "chartScatter"],
    ["bairros",    carregarBairros,    "chartBairros"],
  ];
  const resultados = await Promise.allSettled(tarefas.map(([, fn]) => fn()));
  resultados.forEach((r, i) => {
    if (r.status === "rejected") {
      const [nome, , canvasId] = tarefas[i];
      console.error("Falha em " + nome, r.reason);
      if (canvasId) marcarErro(canvasId, (r.reason && r.reason.message) || "erro no servidor");
    }
  });
}

document.addEventListener("DOMContentLoaded", atualizarTudo);
