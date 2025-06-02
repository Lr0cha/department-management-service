# 💼 Sistema de Gerenciamento de Departamentos e Empregados

Este é um **Web Service RESTful** desenvolvido com **Spring Boot**, que permite o gerenciamento de **departamentos** e **empregados**, com autenticação via **JWT** e controle de acesso baseado em papéis (`ADMIN`, `COMMON`).

> ⚠️ **Projeto em desenvolvimento:** funcionalidades podem mudar ao longo do tempo.


## 🚀 Funcionalidades Implementadas

- ✅ Cadastro e gerenciamento de departamentos e empregados  
- ✅ Vinculação de empregado a um departamento  
- ✅ Autenticação e autorização com Spring Security + JWT  
- ✅ Filtragem com Specification  
- ✅ Validações e tratamento global de erros  
- ✅ Banco de dados PostgreSQL com Docker Compose  
- ✅ Controle de acesso baseado em papéis (`ADMIN` e `COMMON`)
- 🔜 Documentação com Swagger
- 🔜 Testes de unidade e integração  
- 🔜 Métricas com Actuator
  

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

</div>

## <div align=center>📂 Estrutura de Endpoints (parcial)</div>

<div align="center">

| Método | Endpoint                  | Papel necessário |
|--------|---------------------------|------------------|
| POST   | `/login`                  | PUBLIC           |
| GET    | `/employees`              | ADMIN            |
| GET    | `/employees/{id}`         | ADMIN / COMMON   |
| POST   | `/employees`              | ADMIN            |
| PATCH  | `/employees/{field}/{id}` | ADMIN            |
| DELETE | `/employees/{id}`         | ADMIN            |
| GET    | `/departments`            | ADMIN            |
| GET    | `/departments/{id}`       | ADMIN            |
| POST   | `/departments`            | ADMIN            |
| DELETE | `/departments/{id}`       | ADMIN            |

</div>

## 📝 Observações

Este projeto está em fase de desenvolvimento. Sugestões, melhorias e correções são bem-vindas! 😄
