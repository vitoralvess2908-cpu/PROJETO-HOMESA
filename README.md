# Projeto Homesa – Backend

Backend de uma aplicação imobiliária desenvolvido com Spring Boot.
O sistema permite cadastro de usuários, imóveis e realização de compras,
com autenticação baseada em JWT.

## 🚀 Tecnologias utilizadas
- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- JPA / Hibernate
- Banco de dados H2 (ambiente de desenvolvimento)
- Maven

## 📁 Estrutura do projeto
src/
├── controller
├── service
├── repository
├── dto
├── model
└── configuration


## ⚙️ Configuração do ambiente

Antes de rodar o projeto, configure as seguintes variáveis de ambiente:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION_MS`

Exemplo para ambiente de desenvolvimento:

```text
DB_URL=jdbc:h2:mem:meubanco
DB_USER=sa
DB_PASSWORD=
JWT_SECRET=dev-secret-com-32-bytes
JWT_EXPIRATION_MS=3600000
```
▶️ Como executar o projeto

Clone o repositório:
````text
git clone https://github.com/seu-usuario/projeto-homesa.git
````
Entre na pasta do projeto:
````text
cd projeto-homesa
````

Execute a aplicação:
````text
./mvnw spring-boot:run
````
A aplicação estará disponível em:
````text
http://localhost:8080
````

