# Biblioteca Comunitária Qualidade

## Descrição

A Biblioteca Comunitária Qualidade é uma API REST para gerenciamento de uma biblioteca comunitária. O sistema oferece acesso a livros para leitores com plano gratuito ou plano assinante.

## Escopo do sistema

### E1 Conta

Abrange o cadastro de leitores, o gerenciamento de credenciais e a consulta e atualização do perfil.

### E2 Sessão

Abrange login, geração e validação de token, controle de expiração da sessão e logout.

### E3 Assinatura

Disponibiliza dois planos de leitor: plano gratuito e plano assinante. O plano define os recursos e os livros que podem ser acessados.

### E4 Acervo

Gerencia o catálogo de livros públicos e de livros exclusivos para assinantes, incluindo a consulta das informações do acervo.

## Funcionalidades principais

1. Cadastrar leitores.
2. Autenticar leitores.
3. Gerenciar perfil e credenciais.
4. Criar e validar sessões por token.
5. Encerrar sessões por logout.
6. Controlar a expiração dos tokens.
7. Consultar o plano atual do leitor.
8. Disponibilizar livros públicos para leitores do plano gratuito.
9. Disponibilizar livros exclusivos para leitores assinantes.
10. Consultar o acervo da biblioteca.

## Tecnologias previstas

1. Java
2. Spring Boot
3. JUnit 5
4. JaCoCo
5. SonarCloud
6. JMeter

## Instalação

### Pré-requisitos

1. Java instalado na versão definida pelo projeto.
2. Maven instalado ou Maven Wrapper configurado.
3. Git instalado.

### Procedimento provisório

Clone o repositório e acesse a pasta do projeto:

```bash
git clone <URL_DO_REPOSITORIO>
cd biblioteca-comunitaria-qualidade
```

Quando o projeto Spring Boot estiver configurado, instale as dependências com:

```bash
./mvnw clean install
```

No Linux, se necessário, conceda permissão de execução ao Maven Wrapper:

```bash
chmod +x mvnw
```

## Execução

Execute a aplicação com:

```bash
./mvnw spring-boot:run
```

A API ficará disponível, provisoriamente, em `http://localhost:8080`.

Para executar os testes:

```bash
./mvnw test
```

## Estrutura de pastas prevista

```text
biblioteca-comunitaria-qualidade/
├── docs/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ...
│   │   └── resources/
│   └── test/
│       └── java/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

A estrutura poderá ser ajustada conforme a implementação dos módulos de conta, sessão, assinatura e acervo.
