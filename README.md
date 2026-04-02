# Sistema Odontológico Social

## Descrição

Projeto desenvolvido para gerenciamento básico de uma clínica odontológica social. O sistema permite cadastrar, consultar, atualizar e remover dados de pessoas, pacientes e dentistas, com foco em organização e consistência das informações.

---

## Estrutura do Projeto

### 1. Criação das Tabelas (arquivo.sql)

O script de criação contém todas as tabelas do banco de dados, com uso de:

* Chaves primárias e estrangeiras
* Restrições como `UNIQUE`, `CHECK` e `DEFAULT`
* Integridade referencial com:

  * `ON DELETE CASCADE`
  * `ON UPDATE CASCADE`
  * `ON DELETE SET NULL`

A modelagem foi feita considerando:

* **Pessoa** como entidade principal
* **Paciente** e **Dentista** como especializações

---

### 2. Inserção de Dados (arquivo.sql)

Arquivo com inserções de teste:

* Pelo menos 30 registros por tabela
* Dados consistentes respeitando as relações entre tabelas

---

### 3. Backend

Desenvolvido com Spring Boot, seguindo separação em camadas:

* Controller: endpoints da API
* Service: regras de negócio
* Repository: acesso ao banco (JDBC)

Funcionalidades implementadas:

* CRUD completo para Pessoa, Paciente e Dentista
* Validação básica antes de inserir dados
* Atualização em mais de uma tabela quando necessário

---

### 4. Interface

Interface simples em HTML + JavaScript.

Funcionalidades:

* Cadastro de pessoas
* Listagem de pessoas

A comunicação com o backend é feita usando `fetch`.

---

### 5. Modelagem

#### Esquema Conceitual

* Feito no brModelo
* Representa entidades e relacionamentos

#### Esquema Relacional

* Descrição textual das tabelas, atributos e chaves

---

## Endpoints

### Pessoa

* POST `/pessoa`
* GET `/pessoa`
* GET `/pessoa/{cpf}`
* PUT `/pessoa/{cpf}`
* DELETE `/pessoa/{cpf}`

### Paciente

* POST `/paciente`
* GET `/paciente`
* GET `/paciente/{cpf}`
* PUT `/paciente/{cpf}`
* DELETE `/paciente/{cpf}`

---

## Tecnologias

* Java
* Spring Boot
* MySQL
* JDBC Template
* HTML
* JavaScript

---

## Como executar

1. Criar o banco de dados
2. Rodar o script de criação das tabelas
3. Rodar o script de inserção
4. Iniciar a aplicação Spring Boot
5. Abrir o HTML no navegador

---

## Observações

* O uso de `ON DELETE CASCADE` garante que ao remover uma pessoa, seus dados em paciente ou dentista também sejam removidos automaticamente.
* A separação entre pessoa, paciente e dentista segue o modelo relacional (sem duplicação de dados).
