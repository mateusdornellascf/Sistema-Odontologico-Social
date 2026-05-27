(function () {
    const links = [
        { href: "dashboard.html", label: "Dashboard" },
        { href: "pessoa.html", label: "Gerenciar Pessoas" },
        { href: "paciente.html", label: "Gerenciar Pacientes" },
        { href: "dentista.html", label: "Gerenciar Dentistas" },
        { href: "consultas.html", label: "Gerenciar Consultas" },
        { href: "formularioSaude.html", label: "Formulários de Saúde" },
        { href: "procedimento.html", label: "Procedimentos" },
        { href: "relatorios.html", label: "Relatórios" },
        { href: "bd_operacoes.html", label: "Operações do BD" },
        { href: "chat.html", label: "Assistente de IA", special: true }
    ];

    document.addEventListener("DOMContentLoaded", function () {
        const paginaAtual = window.location.pathname.split("/").pop() || "dashboard.html";
        const conteudoExistente = Array.from(document.body.children)
            .filter(function (elemento) {
                return elemento.tagName !== "SCRIPT";
            });
        const shell = document.createElement("div");
        const sidebar = document.createElement("aside");
        const main = document.createElement("main");
        const marca = document.createElement("a");
        const navegacao = document.createElement("nav");

        shell.className = "app-shell";
        sidebar.className = "sidebar";
        main.className = "main-content";

        marca.className = "sidebar-brand";
        marca.href = "dashboard.html";
        marca.textContent = "Clínica Odontológica";
        sidebar.appendChild(marca);

        navegacao.className = "sidebar-nav";
        navegacao.setAttribute("aria-label", "Navegação principal");
        links.forEach(function (item) {
            const link = document.createElement("a");
            link.href = item.href;
            link.textContent = item.label;
            link.className = "sidebar-link";
            if (item.special) {
                link.classList.add("sidebar-link-special");
            }
            if (paginaAtual === item.href) {
                link.classList.add("active");
                link.setAttribute("aria-current", "page");
            }
            navegacao.appendChild(link);
        });
        sidebar.appendChild(navegacao);

        conteudoExistente.forEach(function (elemento) {
            main.appendChild(elemento);
        });
        shell.appendChild(sidebar);
        shell.appendChild(main);

        document.body.classList.add("app-layout");
        document.body.appendChild(shell);
    });
}());
