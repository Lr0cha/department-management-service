# Sistema de Gerenciamento de Departamentos e Empregados

Este é um web service desenvolvido com Spring Boot para gerenciamento de **departamentos** e **empregados**, com controle de acesso restrito a usuários administradores.

## ⚠️ Status do Projeto

🚧 **Em desenvolvimento**  
Funcionalidades ainda estão sendo implementadas. O serviço ainda não está finalizado e pode sofrer alterações.

## 📋 Funcionalidades previstas

- [x] Cadastro de departamentos e empregados
- [x] Vinculação de empregado a departamento
- [x] Controle de acesso via `ADMIN`
- [x] Validações e tratamento de erros
- [ ] Autenticação com JWT
- [ ] Specification
- [ ] Testes de unidade
- [ ] Testes de integração
- [ ] Documentação com Swagger

## 🔐 Acesso

Apenas usuários com papel `ADMIN` podem acessar os endpoints da API.  
Administradores devem ser criados diretamente no banco de dados, com senha previamente criptografada com **BCrypt**.

## 🛠️ Tecnologias

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- Docker

## 📝 Observações

Este projeto está em fase de desenvolvimento. Sugestões, melhorias e correções são bem-vindas! 😄
