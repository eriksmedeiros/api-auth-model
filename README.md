# api-auth-model

Este repositório foi desenvolvido como parte de meus estudos em Spring Security. Ele fornece um modelo base para implementar autenticação e autorização em APIs REST utilizando Java, Spring Boot e JWT.

---

## Stack

- Java (JDK 17+ recomendado)
- Maven
- Spring Boot (ver `pom.xml` do projeto para confirmar versões e starters)
- Docker e Docker Compose (opcional, para Banco de Dados)
- Swagger/OpenAPI


---

## Estrutura do projeto

Principais arquivos e diretórios na raiz:

```
.
├─ .gitattributes
├─ .gitignore
├─ .mvn/                 
├─ docker-compose.yml    # Serviços auxiliares (ex.: banco de dados)
├─ mvnw                  
├─ mvnw.cmd              
├─ pom.xml               # Build e dependências do projeto
└─ src/
   ├─ main/              # Código-fonte da aplicação
   └─ test/              # Testes
```

Dentro de `src/main`, espere encontrar os pacotes de aplicação (controllers, services, repositories, configs, etc.) e os arquivos de configuração em `resources` (por exemplo, `application.properties`/`application.yml`).

---

## Como executar

### 1) Subir dependências com Docker Compose (opcional)



```bash
docker compose up -d
```

- Consulte o `docker-compose.yml` para confirmar os serviços (ex.: PostgreSQL) e credenciais padrão.

Para desligar:

```bash
docker compose down
```

### 2) Executar a aplicação
```bash
./mvnw spring-boot:run
```

Você também pode executar a aplicação diretamente pela sua IDE de preferência.


Observações:
- Garanta que os serviços externos (ex.: banco de dados) estejam em execução antes de iniciar a aplicação.
- Ajuste variáveis de ambiente/perfis conforme a seção [Configuração](#configuração).

---

## Configuração

### Variáveis de ambiente

Defina as variáveis necessárias no seu ambiente (ou em um `.env`, se você gerencia o carregamento manualmente). Exemplos comuns em projetos de API com autenticação:


Ajuste os nomes e valores conforme as propriedades realmente usadas pelo projeto.

### Propriedades Spring (exemplos)

Se o projeto utilizar Spring Boot, as propriedades podem ser definidas em `application.properties` ou `application.yml`. Exemplos:

```
server.port=${SERVER_PORT:8080}

spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

# Exemplo JWT (se aplicável)
security.jwt.secret=${JWT_SECRET}
security.jwt.expiration=${JWT_EXPIRATION:3600}
```
---

## Documentação da API (Swagger/OpenAPI)

- Como acessar (ambiente local padrão):
  - Swagger UI:
    - http://localhost:8080/swagger-ui.html
    - http://localhost:8080/swagger-ui/index.html
  - Documentos OpenAPI:
    - JSON: http://localhost:8080/v3/api-docs

- Autorização (JWT/Bearer):
  - Clique em “Authorize” no Swagger UI e informe `Bearer <seu_token>`.
---
## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---
