# 💼 Sistema de Gerenciamento de Departamentos e Empregados

Este é um **Web Service RESTful** desenvolvido com **Spring Boot**, que permite o gerenciamento de **departamentos** e **empregados**, com autenticação via **JWT** e controle de acesso baseado em papéis (`ADMIN`, `COMMON`).

> [!NOTE]\
> **Projeto em desenvolvimento:** funcionalidades podem mudar ao longo do tempo.


## 🚀 Funcionalidades Implementadas

- ✅ Cadastro e gerenciamento de departamentos e empregados  
- ✅ Vinculação de empregado a um departamento  
- ✅ Autenticação e autorização com **Spring Security** + **JWT**
- ✅ Paginação e ordenação com `Pageable` nos endpoints de listagem
- ✅ Filtragem com **Specification**  
- ✅ Validações personalizadas para atualização de dados (email, senha, endereço)
- ✅ Integração com **ViaCEP** via `WebClient` para buscar endereço por CEP
- ✅ Auditoria automática (usuário e data de criação/atualização)
- ✅ Validações e tratamento global de erros  
- ✅ Banco de dados **PostgreSQL** e **Dockerfile** em **Docker Compose**  
- ✅ Controle de acesso baseado em papéis (`ADMIN` e `COMMON`)
- ✅ Documentação com **Swagger**
- ✅ Testes de unidade para camada **services**
- 🔜 Testes de integração com **TestContainers**
- 🔜 Métricas com **Spring Actuator**
- ✅ **Github Actions** para **CI/CD**

## 🔐 Regras de Acesso

### 👤 Usuário `ADMIN`

- Tem acesso a **todos os endpoints** da API.
- **Não pode deletar ou atualizar outros administradores** – apenas a si mesmo.
- Deve ser **atualizado o `Role` para `ADMIN` diretamente no banco de dados**.

### 👤 Usuário `COMMON`

- Pode **acessar apenas seus próprios dados** através de `GET /employees/{id}`, desde que o `id` pertença a ele.
- **Não pode listar todos os empregados**, nem cadastrar ou alterar dados de terceiros.


## <div align=center>📦 Tecnologias Utilizadas</div>

<div align="center">

| Tecnologia       | Descrição                           |
|------------------|-------------------------------------|
| Java 21          | Linguagem principal                 |
| Spring Boot      | Framework para desenvolvimento web  |
| Spring Security  | Autenticação e autorização          |
| JWT              | Tokens de autenticação              |
| Spring Data JPA  | Acesso e persistência de dados      |
| PostgreSQL       | Banco de dados relacional           |
| Maven            | Gerenciador de dependências         |
| Docker Compose   | Conteinerização (banco e aplicação) |
| WebClient        | Cliente HTTP reativo (ViaCEP)       |
</div>

## <div align=center>📂 Estrutura de Endpoints (parcial)</div>

<div align="center">

| Método | Endpoint            | Papel necessário |
|--------|---------------------|------------------|
| POST   | `/login`            | PUBLIC           |
| GET    | `/employees`        | ADMIN            |
| GET    | `/employees/{id}`   | ADMIN / COMMON   |
| POST   | `/employees`        | ADMIN            |
| PATCH  | `/employees/{id}`   | ADMIN            |
| DELETE | `/employees/{id}`   | ADMIN            |
| GET    | `/departments`      | ADMIN            |
| GET    | `/departments/{id}` | ADMIN            |
| POST   | `/departments`      | ADMIN            |
| DELETE | `/departments/{id}` | ADMIN            |

</div>

## ✨ Detalhes do Novo Endpoint de Atualização

- O endpoint `PATCH /employees/{id}` substitui atualizações parciais separadas.
- Permite atualizar telefone, departamento, endereço, email e senha com regras específicas:
    - Para atualizar **email**, é necessário informar o email atual.
    - Para atualizar **senha**, são necessários: senha atual, nova senha e confirmação.
    - Para atualizar o **endereço**, é obrigatório enviar o CEP e o número da residência juntos.
- Todas essas validações são feitas com um **validador personalizado**.

## 📝 Observações

Este projeto está em fase de desenvolvimento. Sugestões, melhorias e correções são bem-vindas! 😄
