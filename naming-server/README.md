# Naming Server
Componente responsável por gerenciar o **Eureka**.

---

## O que é o Eureka?
O **Eureka** é uma ferramenta criada pela Netflix e integrada ao **Spring Cloud**, usada para **descoberta de serviços**. Ele ajuda diferentes partes de uma aplicação (microservices) a se encontrarem e se comunicarem sem precisar de configurações fixas.

---

## Para que serve o Eureka?

1. **Descoberta de Serviços**  
   Imagine que você tem vários microserviços rodando e precisa que eles se conectem. O Eureka funciona como uma "lista telefônica" onde cada serviço se registra, facilitando a localização pelos outros.

2. **Balanceamento de Carga**  
   Se você tem um microserviço rodando em **duas ou mais instâncias**, o Eureka ajuda a distribuir o tráfego de forma equilibrada entre elas.  
   **Exemplo:**
    - Você tem um serviço de pedidos com 2 instâncias.
    - Quando uma API chama esse serviço, o Eureka distribui as chamadas para que as duas instâncias sejam usadas igualmente, evitando sobrecarga.

3. **Facilidade na Escalabilidade**  
   Quando você adiciona ou remove instâncias de um microserviço, o Eureka se ajusta automaticamente, mantendo a comunicação entre os serviços sem precisar de reconfigurações manuais.

---

## Por que foi usado o Eureka?
- Evita a necessidade de configurar manualmente os endereços dos serviços.
- Melhora a performance e a resiliência da aplicação, equilibrando o tráfego.
- Facilita a comunicação entre microserviços, mesmo em ambientes dinâmicos.

---

## Por que não Ribbon?
- Possui uma interface gráfica que facilita a visualização do processo de descoberta e balanceamento dos serviços (microservices).
- É fácil de implementar, exigindo apenas um pequeno trecho de configuração no `application.yml`.

---

## Como implementar
Implementar o **Eureka** na sua arquitetura de microserviços é simples. Siga os passos abaixo:

---

### 1. Criar o Microserviço Eureka Server
#### **1.1. Configurar a Classe Principal**
Adicione as anotações `@SpringBootApplication` e `@EnableEurekaServer` na classe principal:

```java
@SpringBootApplication
@EnableEurekaServer
public class Application {
   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }
}
```
### 2. Adicionar Dependências
#### **2.1. Configurar o `pom.xml`**
Inclua a seguinte dependência no seu arquivo `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```
### 3. Configurar o `application.yml`
#### **3.1. Configuração básica para o Eureka Server**
Adicione as seguintes configurações no arquivo `application.yml`:

```yaml
eureka:
  client:
    register-with-eureka: false  # O servidor Eureka não se registra nele mesmo.
    fetch-registry: false        # O servidor não busca registros de outros servidores.
