# Clinica Odontologica Social

Aplicacao Spring Boot para gerenciamento de uma clinica odontologica social.

## Requisitos

- Java 21
- Maven
- MySQL

## Como executar

1. Configure o banco no arquivo:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_clinica_odontologica
spring.datasource.username=username
spring.datasource.password=password

gemini.api.key=SUA_CHAVE_DA_API_GEMINI
gemini.model=gemini-3.1-flash-lite
```

> Antes de rodar a aplicacao, substitua `SUA_CHAVE_DA_API_GEMINI` pela sua chave da API do Gemini.
> Evite commitar chaves reais no repositorio.

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

