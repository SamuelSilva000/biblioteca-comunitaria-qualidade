# Cenários e casos de teste

A utility tree atual não possui três cenários com notas (A, A). Por isso, os três cenários abaixo foram acrescentados para completar essa combinação de prioridade. Todos têm importância alta e dificuldade alta.

## Cenário AA-01: Login rápido para o leitor casual

Este cenário complementa o refinamento Tempo de resposta do atributo Eficiência de Performance.

| Parte | Descrição |
|---|---|
| Fonte do estímulo | Leitor casual Marina Souza |
| Estímulo | Enviar uma solicitação de login com credenciais válidas |
| Ambiente | API em execução, rede móvel instável e carga de até 100 usuários simultâneos |
| Artefato | Endpoint de login da API |
| Resposta | Autenticar o leitor e retornar um token de sessão |
| Medida da resposta | 95% de 100 solicitações devem responder em menos de 800 milissegundos |

### Caso de teste CT-001

| Campo | Conteúdo |
|---|---|
| ID | CT-001 |
| Título | Autenticar leitor casual dentro do tempo esperado |
| Pré-condições | A API está disponível; a conta `marina.souza@example.com` existe; a senha cadastrada é `Marina@2026`; a ferramenta de teste registra o tempo de cada resposta |
| Dados de entrada | E-mail: `marina.souza@example.com`; senha: `Marina@2026`; quantidade de solicitações: 100 |
| Passos numerados e reproduzíveis | 1. Iniciar a API. 2. Enviar 100 solicitações POST para `/login` com o e-mail e a senha informados. 3. Registrar o código HTTP e o tempo de resposta de cada solicitação. 4. Contar as respostas concluídas em menos de 800 milissegundos. |
| Resultado esperado | As 100 solicitações devem retornar código HTTP 200; pelo menos 95 respostas devem ocorrer em menos de 800 milissegundos; cada resposta válida deve conter um token. |
| Rastreabilidade | Cenário AA-01: Login rápido para o leitor casual |
| Prioridade | A, A |

## Cenário AA-02: Bloqueio de livro exclusivo para leitor gratuito

Este cenário complementa o refinamento Confidencialidade do atributo Segurança.

| Parte | Descrição |
|---|---|
| Fonte do estímulo | Leitor casual Marina Souza |
| Estímulo | Tentar consultar um livro exclusivo usando um token de plano gratuito |
| Ambiente | API em execução, leitor autenticado e livro exclusivo disponível no acervo |
| Artefato | Endpoint de consulta de livro exclusivo |
| Resposta | Recusar o acesso sem retornar o conteúdo protegido |
| Medida da resposta | 100% de 100 tentativas devem retornar código HTTP 403 e zero conteúdo exclusivo |

### Caso de teste CT-002

| Campo | Conteúdo |
|---|---|
| ID | CT-002 |
| Título | Impedir acesso gratuito a livro exclusivo |
| Pré-condições | A API está disponível; `marina.souza@example.com` possui plano gratuito; o livro `LIV-0007` está marcado como exclusivo; existe um token válido para Marina |
| Dados de entrada | Token: token válido de Marina; identificador do livro: `LIV-0007`; quantidade de solicitações: 100 |
| Passos numerados e reproduzíveis | 1. Iniciar a API. 2. Enviar 100 solicitações GET para `/livros/LIV-0007` usando o token de Marina. 3. Registrar o código HTTP e o corpo de cada resposta. 4. Verificar se algum corpo contém o título, o autor ou o conteúdo do livro exclusivo. |
| Resultado esperado | As 100 solicitações devem retornar código HTTP 403; nenhuma resposta deve conter dados do livro exclusivo. |
| Rastreabilidade | Cenário AA-02: Bloqueio de livro exclusivo para leitor gratuito |
| Prioridade | A, A |

## Cenário AA-03: Disponibilidade mensal da API

Este cenário complementa o refinamento Disponibilidade do atributo Confiabilidade.

| Parte | Descrição |
|---|---|
| Fonte do estímulo | Leitor assinante Rafael Almeida e bibliotecária Helena Costa |
| Estímulo | Enviar solicitações de login, consulta de acervo e acompanhamento de empréstimos durante o uso normal |
| Ambiente | API em produção durante um mês de 30 dias, com monitoramento ativo |
| Artefato | Serviço da API REST e seus endpoints de conta, sessão e acervo |
| Resposta | Processar as solicitações e manter o serviço acessível |
| Medida da resposta | Disponibilidade mínima de 99,5% em 30 dias, equivalente a no máximo 216 minutos de indisponibilidade |

### Caso de teste CT-003

| Campo | Conteúdo |
|---|---|
| ID | CT-003 |
| Título | Verificar disponibilidade mensal da API |
| Pré-condições | A API está publicada em ambiente de produção; o monitoramento executa uma verificação a cada 1 minuto; o período de medição tem 30 dias completos |
| Dados de entrada | Período: 30 dias; intervalo de verificação: 1 minuto; endpoints monitorados: `/login`, `/livros` e `/emprestimos`; limite de indisponibilidade: 216 minutos |
| Passos numerados e reproduzíveis | 1. Iniciar o monitoramento antes do primeiro minuto do período. 2. Enviar uma solicitação válida a cada minuto para cada endpoint. 3. Registrar cada falha de conexão ou resposta fora do serviço. 4. Somar os minutos de indisponibilidade ao final dos 30 dias. 5. Calcular a disponibilidade mensal. |
| Resultado esperado | A indisponibilidade total deve ser de no máximo 216 minutos e a disponibilidade calculada deve ser de pelo menos 99,5%. |
| Rastreabilidade | Cenário AA-03: Disponibilidade mensal da API |
| Prioridade | A, A |

## Ponto de sensibilidade

O tempo de resposta de 800 milissegundos é um ponto de sensibilidade. Pequeno aumento nesse tempo afeta diretamente o uso do leitor casual em rede instável e pode fazer o sistema parecer indisponível.

## Ponto de tradeoff

A validação de autorização em todas as consultas aumenta a segurança, mas pode aumentar o tempo de resposta. O sistema precisa verificar o plano do leitor sem ultrapassar o limite de 800 milissegundos.

## Não risco

Usar verificações a cada 1 minuto é uma decisão adequada para medir a disponibilidade hoje. Essa decisão depende da premissa de que o monitoramento permanecerá ativo e registrará corretamente todas as falhas durante os 30 dias.
