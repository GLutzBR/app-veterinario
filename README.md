# Teste com Spring Boot + Thymeleaf 

Aplicação compacta de gestão de clínica veterinária desenvolvida para testar o uso do Framework Spring Boot,
em conjunto com Spring Security, Spring Data e Thymeleaf.

A inspiração da aplicação veio de um exercício do curso
[Java - Criação de aplicações web com Spring MVC](https://www.treinaweb.com.br/curso/java-avancado-spring-mvc-completo),
no entanto alterei e incrementei os requisitos.

Detalhes do que foi desenvolvido encontra-se na [documentação](docs).

## Pré Requisitos

- Java JDK 11
- mkcert

## Como usar

O projeto necessita de um certificado SSL.  
Para isto basta gerar utilizando a ferramenta mkcert.  
Para saber como instalar a ferramenta siga o passo-a-passo no próprio
[repositório](https://github.com/FiloSottile/mkcert).

Após a instalação gere o certificado raiz com o comando

```shell
mkcert -install
```

Rode o seguinte comando na raiz deste projeto clonado

```shell
mkcert -pkcs12 -p12-file ./src/main/resources/app-veterinario.p12 "localhost"
```