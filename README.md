# App Car Sales - Microsserviço de Gerenciamento de Venda de Veículos

## Sobre o Projeto

O **App Car Sales** é um microsserviço desenvolvido para concessionárias de carros, responsável pelas vendas dos veículos. Ele concentra as funcionalidades de venda, listegem de veículos disponíveis, listagem de veículos vendidos e pagamentos.

Este serviço faz parte de uma arquitetura de microsserviços, comunicando-se com o serviço de cadastro de carros através de APIs REST para garantir a sincronização de dados entre diferentes domínios do negócio.

## 🛠️ Tecnologias Utilizadas

### Core
- **Java 21** - Linguagem de programação
- **Quarkus 3.26.3** - Framework supersônico e subatômico Java
- **Jakarta EE** - Especificação enterprise para Java
- **Maven** - Gerenciamento de dependências e build

### Persistência
- **Hibernate ORM com Panache** - Simplificação do ORM através do padrão Active Record
- **MySQL** - Banco de dados relacional
- **H2 Database** - Banco em memória para testes

### APIs e Comunicação
- **Quarkus REST** - Implementação moderna de Jakarta REST
- **Jackson** - Serialização/deserialização JSON
- **REST Client** - Cliente HTTP para comunicação entre microsserviços

### Infraestrutura e Deploy
- **Docker** - Containerização da aplicação
- **Amazon ECS** - Orquestração de containers na AWS
- **Amazon ECR** - Registro de imagens Docker
- **Amazon RDS** - Banco de dados MySQL gerenciado
- **GitHub Actions** - CI/CD pipeline

### Qualidade e Testes
- **JUnit 5** - Framework de testes
- **Mockito** - Mock de dependências
- **REST Assured** - Testes de APIs REST
- **JaCoCo** - Cobertura de código

## 💻 Como Executar Localmente

Com o docker rodando, basta executar o comando abaixo:

`quarkus dev`

o banco de dados será criado automaticamente pelo quarkus utilizando o docker.

A aplicação estará disponível em: http://localhost:8081

Swagger UI: http://localhost:8081/q/swagger-ui/

## Como Executar os Testes

execute o comando abaixo:

`mvn clean verify`

para visualizar os resultados dos testes, abra o arquivo `target/coverage/index.html` no seu navegador.
