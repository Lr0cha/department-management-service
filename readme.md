<div align="center">
  <h1>💼 Gerenciamento de Departamentos e Empregados</h1>
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,postgresql,docker,githubactions" alt="My Skills" />
  </a>
</div>


Este é um **Web Service RESTful** desenvolvido com **Spring Boot**, que permite o gerenciamento de **departamentos** e **empregados**, com autenticação via **JWT** e controle de acesso baseado em papéis (`ADMIN`, `COMMON`). O projeto inclui **workflows** automatizados para **build, testes e push** para um registry (**Docker hub**), garantindo a integração contínua e entrega eficiente da aplicação.

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
- ✅ Testes de integração com **TestContainers**
- 🔜 Métricas com **Spring Actuator**
- ✅ **Github Actions** para **CI/CD**

## 🔐 Regras de Acesso

### 👤 Usuário `ADMIN`

- Tem acesso a **todos os endpoints** da API.
- **Não pode deletar ou atualizar outros administradores** – apenas a si mesmo.
- Deve ser **atualizado o `Role` para `ADMIN` diretamente no banco de dados**.

### 👤 Usuário `COMMON`

- Pode **acessar apenas seus próprios dados** através de `GET /employees/{id}`.
- **Não pode listar todos os empregados**, nem cadastrar ou alterar dados de terceiros.

## <div align=center>📂 Estrutura de Endpoints (parcial)</div>

<div align="center">

| Método | Endpoint            | Papel necessário |
|--------|---------------------|------------------|
| POST   | `/login`            | ADMIN / COMMON   |
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

## 📝 Observações

Este projeto está em fase de desenvolvimento. Sugestões, melhorias e correções são bem-vindas! 😄
