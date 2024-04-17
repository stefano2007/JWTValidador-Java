# Projeto JWT Validador

## Indice
- [Resumo](#resumo)
- [Ferramentas](#ferramentas)
- [Instalação](#instalação)
- [Overview da API](#overview-da-api)
- [Docker](#docker)
- [Agradecimentos](#agradecimentos)

## Resumo

Criar um API que receba um JWT (string) e verificar se é validos conforme regras

- O JWT deve ser válido contendos apenas as Claims (Name, Role e Seed) 
- O campo Name não pode conter números e possuir até 256 caracteres
- O campo Role pode conter apenas 1 valor (Admin, Member e External)
- O campo Seed deve ser um número primo.
O retorno deve ser "verdadeiro" ou "falso"

## Ferramentas

- [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring](https://spring.io/) - Framework.
- [Maven](https://maven.apache.org/) - Gerenciador de Dependências. 
- [Intellj](https://www.jetbrains.com/idea/download/) Community Edition - IDE Desenvolvimento. 
- [GIT](https://git-scm.com/downloads) - Controle de versão. 
- [Swagger](https://swagger.io/) - Documentação da API
- [Insomnia](https://insomnia.rest/download) - Teste da API

Neste projeto, foi desenvolvidos e abordados os seguintes tópicos:

- Arquitetura REST API.

- Princípios SOLID e Clean Code.

- Tratamento de exceções.

-------------------------

## Instalação

### Pre Requesitos

- [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

### Baixando e executando o projeto

Baixando o projeto 

```
git clone https://github.com/stefano2007/JWTValidador-Java.git
```

Ir para pasta do projeto

```
cd JWTValidador-Java
```

Complilar o projeto

```
mvn clean package
```

Rodar o projeto

```
java -jar target/jwtvalidador-1.0.0.jar
```

Obs. Opção também de roda no docker [adiante](#docker).

### Testes Unitários

[JUnit](https://junit.org/) 5.7.0

[Mockito](https://site.mockito.org/) 5.10.2

Execute o testes unitários usando o comando a seguir:

```
mvn test
```

![Testes unitários](readme/RunTest-2024-04-15.png)

## Overview da API

### Metodo Validador

Metodo: GET /api/Validador 

Input: jwt (query)

Retorno Sucesso: Status 200(OK), Body: "verdadeiro"

Retorno Erro: Status 400(BAD_REQUEST), Body: "falso" e Response Headers com "Exception-Message" e "Exception-Type" detalhando a erro.

Os campos adicionar no Headers da resposta foi pensado para ajudar quem consome a API sem modificar a estrutura de retorno.

![Imagem Swagger](readme/swagger-2024-04-15.png)

### Casos de uso

Construa uma aplicação que exponha uma api web que recebe por parâmetros um JWT (string) e verifica se é válida, conforme regras abaixo:
- Deve ser um JWT válido
- Deve conter apenas 3 claims(Name, Role e Seed)
- A claim Name não pode ter carácter de números
- A claim Role deve conter apenas 1 dos três valores (Admin, Member e External)
- A claim Seed deve ser um número primo.
- O tamanho máximo da claim Name é de 256 caracteres.

### Caso 1:

Entrada: 

```
eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg
```

Saída: "verdadeiro"

Justificativa: Abrindo o JWT, as informações contidas atendem a descrição:

```
{
  "Role": "Admin",
  "Seed": "7841",
  "Name": "Toninho Araujo"
}
```

-------------------------

### Caso 2:

Entrada:

```
eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg
```

Saída: "falso"

Justificativa:  JWT inválido

-------------------------

### Caso 3:

Entrada:

```
eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiRXh0ZXJuYWwiLCJTZWVkIjoiODgwMzciLCJOYW1lIjoiTTRyaWEgT2xpdmlhIn0.6YD73XWZYQSSMDf6H0i3-kylz1-TY_Yt6h1cV2Ku-Qs
```
Saída: "falso"

Justificativa: Abrindo o JWT, a Claim Name possui caracter de números

```
{
  "Role": "External",
  "Seed": "88037",
  "Name": "M4ria Olivia"
}
```

-------------------------

### Caso 4:

Entrada:

```
eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiTWVtYmVyIiwiT3JnIjoiQlIiLCJTZWVkIjoiMTQ2MjciLCJOYW1lIjoiVmFsZGlyIEFyYW5oYSJ9.cmrXV_Flm5mfdpfNUVopY_I2zeJUy4EZ4i3Fea98zvY
```

Saída: "falso"

Justificativa: Abrindo o JWT, a Claim Name possui caracter de números

```
{
  "Role": "Member",
  "Org": "BR",
  "Seed": "14627",
  "Name": "Valdir Aranha"
}
```

-------------------------

### Collection Insomnia

- Importe a coleção no Insomnia [link arquivo json Insomnia](readme/Insomnia_2024-04-16.json)
  ![Coleções Insomnia](readme/Insomnia_2024-04-16.png)

- Certifique que a variavel urlBase esta apontando para a API em execução
  ![Insomnia variaveis](readme/Insomnia_urlBase.png)

### Demostrativo Insomnia (Importando collection, Verificando variaveis e teste de API)

![Demostrativo Insomnia](readme/Insomnia-2024-04-14-17-58-46.gif)

## Docker

### Pré Requisito

- Docker CLI instalado

Acesse a pasta raiz do projeto (aonde existe o docker-compose.yml) e execute o comando a seguir:

```
docker-compose up
```

Caso sucesso o sistema irá subir na porta local 8081: Exemplo  http://localhost:8081/swagger-ui/index.html

Demostação do rodando no Docker 

![Rodando API no Docker](readme/RunDocker-2024-04-14.gif)

Obs. estou usando o WSL no Windows porém e igual para o Docker CLI, segue [link para ajudar configurar o Docker no WSL](https://github.com/luamleiverton/wsl2-docker-quickstart-by-fullcycle)

## Agradecimentos

Obrigado pela oportunidade e te chegado até aqui :)