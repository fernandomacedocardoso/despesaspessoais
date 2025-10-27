# 🚀 API REST de Controle de Despesas Pessoais

Este projeto implementa uma API REST para gerenciar despesas e categorias, atendendo aos requisitos de CRUD completo, filtros dinâmicos, resumo financeiro mensal e aderência estrita às boas práticas de desenvolvimento Spring Boot e padrões REST.

## 🎯 Requisitos Atendidos

| Categoria | Requisito | Status | Implementação Chave |
| :--- | :--- | :--- | :--- |

| **Arquitetura** | Java 17+, Spring Boot 3+, MySQL 8 | ✅ | Configurado com Flyway e MySQL 8 (via Docker/Local). |


| **Persistência** | Spring Data JPA, Bean Validation | ✅ | Validações na camada DTO/Service. |

| **Modelagem** | Relacionamentos e Constraints | ✅ | Implementação de FK (categoria\_id) e *Unique Constraint* (`nome` da categoria). |

| **Data/Hora** | Timestamps (`createdAt`/`updatedAt`) | ✅ | Padronização de datas para **`Instant` (UTC)** nas Entidades para evitar problemas de fuso horário. |

| **Filtros** | Paginação e Ordenação | ✅ | Uso de `Pageable` em todas as listagens. |

| **Filtros** | Filtros Dinâmicos | ✅ | Implementado via padrão **Specification** para combinar filtros de data, categoria e descrição (*case-insensitive*). |

| **Documentação** | OpenAPI/Swagger UI | ✅ | Configurado via `springdoc-openapi` e acessível em `/swagger`. |

| **Erros** | Tratamento Consistente | ✅ | **GlobalExceptionHandler** (`@ControllerAdvice`) com *payload* de erro padronizado. |

| **Regra 1** | Valor da Despesa (`> 0`) | ✅ | Validado na camada de Service (`BadRequestException`). |

| **Regra 2** | Nome de Categoria Único | ✅ | Validado no Service (`ConflictException`) e no banco (Unique Constraint). |

| **Regra 3** | Não Deletar Categoria Vinculada | ✅ | Tratado com `ConflictException` (HTTP 409). |

| **Regra 4** | Resumo Mensal | ✅ | Query customizada no Repositório para calcular o `total por categoria` e o `total geral`. |

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
* JDK 17 ou superior
* Docker e Docker Compose (Para o setup mais fácil do banco de dados)
* Maven

### 1. Inicialização do Banco de Dados (Via Docker Compose)

Para iniciar o MySQL 8, execute o comando na raiz do projeto (onde o `docker-compose.yml` deve estar localizado):

```bash
docker-compose up -d mysql-db
(Opcional: Se não usar Docker, configure a URL, usuário e senha do MySQL local no application.yml.)
2. Configuração e BuildO projeto usa o Flyway para aplicar automaticamente as migrations (criação de tabelas categorias e despesas).Bash# Limpa, compila e empacota o projeto
./mvnw clean install
3. Execução da AplicaçãoExecute o JAR gerado no passo anterior:Bashjava -jar target/despesas-pessoais-0.0.1-SNAPSHOT.jar
A API estará disponível em http://localhost:8080.
📚 Endpoints e Documentação
Documentação (Swagger UI)Acesse a interface interativa do Swagger para testar todos os endpoints:URL: http://localhost:8080/swagger 
Resumo dos Endpoints PrincipaisCategoriaMétodoEndpointStatus HTTP em ErroCRUDPOST/api/v1/categorias400 (Validação),
 409 (Conflito)DetalheGET/api/v1/despesas/{id}404 (Não Encontrado)FiltroGET/api/v1/despesas200
 (Paginated Response)RelatórioGET/api/v1/despesas/resumo400 (mes=YYYY-MM obrigatório)
🔍 Exemplos de Chamadas de Teste1. Criar Categoria (POST)Endpoint: POST /api/v1/categoriasJSON{
  "nome": "Alimentação",
  "descricao": "Gastos com comida, mercado, restaurantes"
}
2. Filtro Dinâmico (GET)Use o endpoint /api/v1/despesas com múltiplos parâmetros opcionais:/api/v1/despesas?dataInicio=2025-08-01&categoriaId=1&descricao=almoço&page=0&size=10&sort=data,desc
3. Resumo Mensal (GET)O mês é passado como Query Parameter./api/v1/despesas/resumo?mes=2025-08
4. Tratamento de Conflito (409)Tentar deletar uma categoria que possui despesas vinculadas.DELETE /api/v1/categorias/1
# Retorna 409 Conflict