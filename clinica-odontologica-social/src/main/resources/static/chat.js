async function enviarPergunta() {
    const pergunta = document.getElementById("pergunta").value;
    const respostaElemento = document.getElementById("resposta");

    if (!pergunta.trim()) {
        respostaElemento.innerText = "Digite uma pergunta antes de enviar.";
        return;
    }

    respostaElemento.innerText = "Consultando a IA...";

    try {
        const response = await fetch("/chat", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                pergunta: pergunta
            })
        });

        const data = await response.json();

        respostaElemento.innerText = data.resposta;

    } catch (error) {
        respostaElemento.innerText = "Erro ao consultar o agente de IA.";
        console.error(error);
    }
}

async function carregarSugestoes() {
    const sugestoesDiv = document.getElementById("sugestoes");

    try {
        const response = await fetch("/chat/suggestions");
        const data = await response.json();

        data.suggestions.forEach(sugestao => {
            const button = document.createElement("button");
            button.innerText = sugestao;
            button.className = "suggestion-button";

            button.onclick = () => {
                document.getElementById("pergunta").value = sugestao;
                enviarPergunta();
            };

            sugestoesDiv.appendChild(button);
        });

    } catch (error) {
        sugestoesDiv.innerText = "Não foi possível carregar sugestões.";
        console.error(error);
    }
}

carregarSugestoes();