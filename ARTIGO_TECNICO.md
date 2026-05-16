# Arquitetura Onion com Spring Boot 4 e Java 25: Um Guia Prático para Sistemas Escaláveis

## Introdução

No desenvolvimento de software moderno, a escolha da arquitetura é fundamental para garantir a manutenibilidade, testabilidade e evolução contínua do sistema. Este artigo técnico explora a implementação de uma API REST de e-commerce (baseada no dataset Olist) utilizando a **Onion Architecture** (Arquitetura Cebola), integrando tecnologias de ponta como Java 25, Spring Boot 4, HashiCorp Vault, e infraestrutura como código com Terraform e Kubernetes.

## 1. O Core da Estratégia: Arquitetura Onion

A Arquitetura Onion, proposta por Jeffrey Palermo, coloca o **Domínio** no centro da aplicação. Diferente da arquitetura tradicional em camadas, onde a lógica de negócio depende do banco de dados, na Onion todas as dependências apontam para o centro.

### Camadas do Projeto

1.  **Domain (Core):** Contém as entidades de negócio e as interfaces de repositório. É a camada mais interna e não depende de nenhuma outra.
2.  **Application:** Implementa os casos de uso (Services), DTOs e Mappers. Depende apenas do Domínio.
3.  **Infrastructure:** Contém as implementações técnicas, como repositórios JPA, configurações de segurança (JWT), integrações com Cloud e ferramentas como Flyway/Liquibase.
4.  **Presentation:** A camada de entrada, onde residem os Controllers REST e os Assemblers HATEOAS.

### Exemplo Prático: Inversão de Dependência no Repositório

No Domínio, definimos apenas a interface:

```java
// br.com.onion.domain.repository.CustomerRepository
public interface CustomerRepository {
    Page<Customer> findAll(Pageable pageable);
    Optional<Customer> findById(String id);
    Customer save(Customer customer);
}
```

A implementação técnica fica na camada de Infraestrutura, isolando o core de detalhes do framework:

```java
// br.com.onion.infrastructure.repository.JpaCustomerRepository
@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, String>, CustomerRepository {
}
```

## 2. Tecnologias e Segurança de Ponta

### Autenticação JWT e Segurança Progressiva
O sistema utiliza **Spring Security 7.x** com autenticação **Stateless** via JWT. Além do controle de acesso, implementamos um **Rate Limiting** robusto por IP utilizando a biblioteca **Bucket4j**, protegendo a API contra ataques de força bruta e DoS.

```java
// Trecho do RateLimitFilter utilizando Bucket4j
Bucket bucket = buckets.computeIfAbsent(clientIp, key -> Bucket.builder()
    .addLimit(limit -> limit.capacity(60).refillGreedy(60, Duration.ofMinutes(1)))
    .build());

if (bucket.tryConsume(1)) {
    filterChain.doFilter(request, response);
} else {
    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
}
```

### Gerenciamento de Secrets com HashiCorp Vault
Para evitar o vazamento de credenciais (como senhas de banco e chaves JWT), o projeto integra o **HashiCorp Vault**. As configurações sensíveis são injetadas dinamicamente via **Spring Cloud Vault**, garantindo que nenhum segredo seja armazenado em texto puro nos repositórios de código.

## 3. Modelo de Maturidade REST e Documentação

A API atende ao Nível 3 do Modelo de Maturidade de Richardson ao implementar **HATEOAS** (Hypermedia as the Engine of Application State). Isso permite que o cliente da API descubra os recursos disponíveis dinamicamente através de links nas respostas.

### Resposta HATEOAS Paginada
```json
{
  "_embedded": {
    "customerResponseList": [...]
  },
  "_links": {
    "self": { "href": "http://localhost:8080/api/customers?page=0&size=20" }
  },
  "page": {
    "size": 20,
    "totalElements": 99441,
    "totalPages": 4973,
    "number": 0
  }
}
```

A documentação é gerada automaticamente pelo **SpringDoc OpenAPI (Swagger)**, fornecendo uma interface interativa para testes e exploração dos endpoints.

## 4. Persistência e Evolução de Dados

O uso do **Liquibase** permite o versionamento do schema do banco de dados (PostgreSQL). No contexto deste projeto, ele é responsável por criar as tabelas do dataset Olist e realizar a carga inicial de dados via CSV, garantindo que o ambiente de desenvolvimento seja idêntico ao de produção.

## 5. Infraestrutura Moderna: De Containers a Nuvem

O projeto foi concebido para ser *Cloud Native*:

*   **Docker:** Multi-stage builds garantem imagens JRE leves e seguras.
*   **Kubernetes:** Manifestos completos com suporte a **HPA (Horizontal Pod Autoscaler)**, permitindo que a aplicação escale de 2 a 10 pods conforme a demanda de CPU.
*   **Terraform:** Provisionamento automatizado para **AWS (EKS)**, **Azure (AKS)** e **GCP (GKE)**, permitindo a replicação da infraestrutura em minutos.

## Conclusão

A combinação da Arquitetura Onion com tecnologias modernas do ecossistema Spring Boot resulta em um sistema resiliente, escalável e fácil de manter. O isolamento da lógica de negócio e a automação da infraestrutura são diferenciais competitivos que permitem entregas rápidas e seguras em ambientes corporativos de alta complexidade.

---
*Este artigo foi elaborado com base no projeto Spring Boot Onion - Uma referência em arquitetura e boas práticas de programação.*

---

## Referências

1. PALERMO, Jeffrey. **The Onion Architecture**. 2008. Disponível em: https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/
2. MOSCHETTI, Vitor. **Onion Architecture: Definição, Camadas e Benefícios**. Medium, 2023. Disponível em: https://vitormoschetti.medium.com/onion-architecture-defini%C3%A7%C3%A3o-camadas-e-benef%C3%ADcios-551f460c3b2c
3. RICHARDSON, Leonard; RUBY, Sam. **RESTful Web Services**. O'Reilly Media, 2007.
4. FOWLER, Martin. **Richardson Maturity Model**. 2010. Disponível em: https://martinfowler.com/articles/richardsonMaturityModel.html
5. Spring Boot Reference Documentation. Disponível em: https://spring.io/projects/spring-boot
6. Spring Security Reference. Disponível em: https://docs.spring.io/spring-security/reference/
7. Spring HATEOAS Reference. Disponível em: https://docs.spring.io/spring-hateoas/docs/current/reference/html/
8. HashiCorp Vault Documentation. Disponível em: https://developer.hashicorp.com/vault/docs
9. Liquibase Documentation. Disponível em: https://docs.liquibase.com/
10. Bucket4j - Rate Limiting Library. Disponível em: https://github.com/bucket4j/bucket4j
11. Kubernetes Documentation. Disponível em: https://kubernetes.io/docs/
12. Terraform by HashiCorp. Disponível em: https://developer.hashicorp.com/terraform/docs
13. Brazilian E-Commerce Public Dataset by Olist. Kaggle. Disponível em: https://www.kaggle.com/datasets/olistbr/brazilian-ecommerce
14. MARTIN, Robert C. **Clean Architecture**. Prentice Hall, 2017.
15. RICHARDS, Mark; FORD, Neal. **Fundamentos da Arquitetura de Software: Uma Abordagem de Engenharia**. O'Reilly Media / Novatec, 2020.