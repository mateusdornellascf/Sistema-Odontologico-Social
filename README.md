# Clinica Odontologica Social

Aplicacao Spring Boot para gerenciamento de uma clinica odontologica social.

## Requisitos

- Java 21
- Maven
- MySQL

## Como executar

1. Crie o arquivo de configuracao local a partir do exemplo:

```text
clinica-odontologica-social/src/main/resources/application-example.properties
```

Copie esse arquivo para:

```text
clinica-odontologica-social/src/main/resources/application.properties
```

Depois, ajuste as configuracoes do banco e informe sua chave da API do Gemini:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_clinica_odontologica
spring.datasource.username=username
spring.datasource.password=password

gemini.api.key=SUA_CHAVE_DA_API_GEMINI
gemini.model=gemini-3.1-flash-lite
```

> O arquivo `application.properties` e local e nao deve ser commitado.
> Mantenha chaves reais apenas nesse arquivo.

2. Crie o banco e execute os scripts SQL em `src/main/resources/sql`, nesta ordem sugerida:

```text
criacao_db_tables.sql
insersao_dados.sql
ddl_indicies.sql
views.sql
funcoes.sql
procedimentos.sql
triggers.sql
consultas.sql
```

3. Na raiz do projeto, execute:

```bash
mvn spring-boot:run
```

4. Acesse a aplicacao no navegador:

```text
http://localhost:8080
```

## Telas principais

- `http://localhost:8080/index.html`
- `http://localhost:8080/consultas.html`
- `http://localhost:8080/relatorios.html`
- `http://localhost:8080/bd_operacoes.html`

