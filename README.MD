# Customer Service - E-commerce

Este é o serviço responsável pelo gerenciamento de clientes do e-commerce. O projeto foi estruturado seguindo os princípios de Clean Architecture e Clean Code.

## Pré-requisitos

Antes de começar, certifique-se de ter instalado:
- Docker (20.10.x ou superior)
- Docker Compose (2.x ou superior)
- Java 21 (para desenvolvimento local)
- Maven 3.8.x (para desenvolvimento local)

## Estrutura do Projeto

O projeto está organizado nas seguintes camadas:

```
src/main/java/br/com/ecommerce/customer/
├── application/
│   ├── dto/           # Data Transfer Objects
│   ├── usecases/      # Casos de uso da aplicação
│   └── ports/         # Interfaces/Contratos entre camadas
│
├── domain/
│   ├── entities/      # Entidades de domínio
│   ├── valueobjects/  # Objetos de valor imutáveis
│   └── exceptions/    # Exceções específicas do domínio
│
├── infrastructure/
│   ├── config/        # Configurações do Spring e aplicação
│   ├── persistence/   # Implementações de repositórios
│   └── messaging/     # Implementações de mensageria
│
├── presentation/
│   ├── controllers/   # Controllers da API
│   ├── responses/     # Classes de resposta da API
│   ├── requests/      # Classes de requisição da API
│   └── mappers/       # Conversores entre DTOs e objetos de domínio
│
├── interfaces/
│   ├── rest/          # Definições de endpoints e rotas
│   ├── events/        # Handlers de eventos
│   └── handlers/      # Handlers genéricos e middlewares
│
└── shared/
    ├── utils/         # Utilitários compartilhados
    └── validation/    # Validações comuns
```

### Descrição das Camadas

#### Domain (Camada de Domínio)
- Contém as regras de negócio centrais
- Independente de frameworks e bibliotecas externas
- Define as entidades principais e regras de negócio
- Não possui dependências com outras camadas

#### Application (Camada de Aplicação)
- Implementa os casos de uso do sistema
- Orquestra o fluxo de dados entre as entidades
- Define interfaces (ports) para comunicação com o mundo exterior
- Contém DTOs para transferência de dados entre camadas

#### Infrastructure (Camada de Infraestrutura)
- Implementa a persistência de dados
- Contém configurações do framework
- Implementa a comunicação com serviços externos
- Fornece implementações concretas para as interfaces definidas na camada de aplicação

#### Presentation (Camada de Apresentação)
- Responsável pela apresentação dos dados ao cliente
- Contém os controllers que recebem as requisições HTTP
- Define os formatos de request/response da API
- Realiza validações básicas de entrada
- Trata a serialização/deserialização dos dados
- Possui mappers para converter DTOs em objetos de domínio e vice-versa

#### Interfaces (Camada de Interface)
- Define os contratos da API REST
- Gerencia os eventos do sistema
- Implementa handlers de eventos
- Gerencia aspectos de segurança da API

#### Shared (Camada Compartilhada)
- Contém código utilitário usado por múltiplas camadas
- Implementa validações comuns
- Define exceções e tipos compartilhados

## Princípios Arquiteturais

Este projeto segue os princípios da Clean Architecture:
1. Independência de Frameworks
2. Testabilidade
3. Independência de UI
4. Independência de Banco de Dados
5. Independência de Agentes Externos

As dependências fluem de fora para dentro, com o domínio no centro, livre de dependências externas.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Maven

## Como Executar

### Usando Docker Compose (Recomendado)

1. **Clone o repositório**
   ```bash
   git clone <url-do-repositorio>
   cd customer-service
   ```

2. **Construa e inicie os containers**
   ```bash
   # Construir as imagens
   docker-compose build

   # Iniciar os serviços em background
   docker-compose up -d
   ```

3. **Verifique o status dos serviços**
   ```bash
   docker-compose ps
   ```

4. **Acesse os serviços**
   - API: http://localhost:8080
   - MongoDB Express (Interface Admin): http://localhost:8081
     - Usuário: custormer-auth
     - Senha: MongoExpress2025!
   - MongoDB: localhost:27017
     - Usuário Root: root
     - Senha Root: MongoDB2025!

5. **Visualize os logs**
   ```bash
   # Todos os serviços
   docker-compose logs -f

   # Serviço específico
   docker-compose logs -f app
   docker-compose logs -f mongo
   docker-compose logs -f mongo-express
   ```

6. **Parar os serviços**
   ```bash
   # Parar mantendo os volumes
   docker-compose down

   # Parar e remover volumes
   docker-compose down -v
   ```

### Monitoramento e Manutenção

1. **Verificar saúde dos serviços**
   ```bash
   # Status da API
   curl http://localhost:8080/actuator/health

   # Monitorar recursos
   docker stats
   ```

2. **Reiniciar serviços**
   ```bash
   # Reiniciar um serviço específico
   docker-compose restart app
   
   # Reiniciar todos os serviços
   docker-compose restart
   ```

3. **Limpar ambiente**
   ```bash
   # Remover todos os containers e redes
   docker-compose down

   # Remover tudo, incluindo volumes
   docker-compose down -v --rmi all
   ```

### Desenvolvimento Local

Para desenvolvimento local sem Docker:

1. **Instale o MongoDB localmente ou use um serviço remoto**

2. **Configure o application.yml**
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb://localhost:27017/customer-service
   ```

3. **Execute a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```

## Endpoints da API

### Criar Cliente
```http
POST /api/customers
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com",
  "phone": "11999999999",
  "addresses": [
    {
      "street": "Rua Exemplo",
      "number": "123",
      "complement": "Apto 45",
      "neighborhood": "Centro",
      "city": "São Paulo",
      "state": "SP",
      "zipCode": "01234-567"
    }
  ]
}
```

## Troubleshooting

1. **Erro de conexão com MongoDB**
   - Verifique se o container do MongoDB está rodando: `docker-compose ps`
   - Verifique os logs: `docker-compose logs mongo`
   - Confirme as credenciais no application.yml

2. **Aplicação não inicia**
   - Verifique os logs: `docker-compose logs app`
   - Confirme se o MongoDB está acessível
   - Verifique as configurações de memória no docker-compose.yml

3. **Mongo Express não acessível**
   - Confirme se o container está rodando: `docker-compose ps`
   - Verifique os logs: `docker-compose logs mongo-express`
   - Tente reiniciar o serviço: `docker-compose restart mongo-express`

## Como Contribuir

[Instruções de contribuição serão adicionadas]

### Mappers

O projeto utiliza mappers para realizar a conversão entre DTOs (Data Transfer Objects) e objetos de domínio. Esta abordagem segue os princípios do Clean Architecture, mantendo a separação de responsabilidades e facilitando a manutenção do código.

#### Estrutura dos Mappers
```java
// CustomerMapper
public class CustomerMapper {
    public static Customer toDomain(CreateCustomerRequest request) {
        // Converte CreateCustomerRequest para Customer
    }
}

// AddressMapper
public class AddressMapper {
    public static Address toDomain(AddressRequest request) {
        // Converte AddressRequest para Address
    }
}
```

#### Benefícios dos Mappers
- Separação clara entre objetos de transporte de dados (DTOs) e objetos de domínio
- Centralização da lógica de conversão
- Facilidade de manutenção e teste
- Redução de acoplamento entre camadas
- Melhor organização do código
