# ATAM, ciclo 01

## Escopo e método

Esta é uma avaliação inicial pelo método ATAM, de Len Bass, Paul Clements e Rick Kazman. A análise usa personas, requisitos, utility tree, arquitetura, ADRs, código, testes e CI disponíveis no repositório.

Os números e regras do PRD são fatos do projeto. As conclusões sobre lacunas, riscos e prioridades são avaliação. Não foram criados requisitos novos.

Fontes principais:

1. [PRD](../prd.md), RF-01 a RF-12 e RNF-01 a RNF-08.
2. [Utility tree](../utility-tree.md), atributos e prioridades I e D.
3. [Arquitetura](../arquitetura.md), seções 3, 4, 5, 8 a 11, 12 a 23 e ADRs.
4. [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L1), implementação atual.
5. [BibliotecaServiceTest.java](../../src/test/java/br/com/biblioteca/BibliotecaServiceTest.java#L1), testes atuais.
6. [ci.yml](../../.github/workflows/ci.yml#L1), integração contínua.

## 1. Direcionadores de negócio

| Direcionador | Evidência | Implicação arquitetural |
|---|---|---|
| Permitir uso rápido pelo leitor casual | Marina usa o celular, na rua e com internet instável. Fonte: [personas.md](../personas.md#L5). | O fluxo online precisa atender as metas de resposta de RNF-01 e RNF-07. |
| Preservar o benefício do assinante | Rafael usa vários dispositivos e quer acesso ao acervo exclusivo sem expiração inesperada. Fonte: [personas.md](../personas.md#L15). | Sessão, autorização por plano e disponibilidade devem funcionar em conjunto. |
| Manter o acervo correto | Helena cadastra livros, classifica acesso e acompanha empréstimos. Fonte: [personas.md](../personas.md#L25). | Cadastro, classificação, autorização e auditoria são importantes para E4. |
| Separar acesso gratuito e assinante | RF-06 e RF-07 definem os planos e o acesso a livros exclusivos. Fonte: [prd.md](../prd.md#L14). | O plano deve ser considerado antes de entregar um livro exclusivo. |
| Processar dados novos diariamente | A arquitetura registra cron ao final do dia para empréstimos e devoluções novos e indicadores internos. Fonte: [arquitetura.md](../arquitetura.md#L68). | O batch precisa de fronteira incremental, recuperação e observabilidade. A forma não está no requisito. |
| Operar com qualidade mensurável | O PRD define resposta, expiração, concorrência, disponibilidade, segurança e auditoria. Fonte: [prd.md](../prd.md#L19). | Os testes e a arquitetura precisam demonstrar essas qualidades, não apenas retornar classificações textuais. |

## 2. Cenários de atributo de qualidade

Os cenários abaixo são derivados dos requisitos existentes e da restrição do cron. Quando a medida não existe no PRD, está marcada como lacuna ou hipótese, sem tratá-la como fato.

### C-01, proteção do acervo exclusivo

| Parte | Descrição |
|---|---|
| Fonte | Leitor gratuito autenticado. |
| Estímulo | Solicitar um livro exclusivo. |
| Artefato | API REST, sessão, plano e acervo. |
| Ambiente | Fluxo online com livro exclusivo disponível. |
| Resposta | Negar o acesso e não retornar dados do livro. |
| Medida de resposta | 100% das tentativas, conforme RNF-06 em [prd.md](../prd.md#L37). |

### C-02, tempo do fluxo online

| Parte | Descrição |
|---|---|
| Fonte | Leitor casual ou assinante. |
| Estímulo | Consultar perfil ou acervo enquanto a API atende outros usuários. |
| Artefato | API REST, sessão, assinatura e acervo. |
| Ambiente | Uso normal da API, inclusive durante o cron, quando houver execução concorrente. |
| Resposta | Atender a consulta sem ultrapassar a meta aplicável. |
| Medida de resposta | 95% em até 500 milissegundos para perfil e acervo, conforme RNF-01 em [prd.md](../prd.md#L19), e 95% em até 1 segundo para consulta pública, conforme RNF-07 em [prd.md](../prd.md#L37). A diferença entre metas permanece conflito documental. |

### C-03, processamento incremental

| Parte | Descrição |
|---|---|
| Fonte | Cron diário. |
| Estímulo | Iniciar a execução com empréstimos e devoluções novos e registros já processados. |
| Artefato | Processador batch, fonte operacional e checkpoint. |
| Ambiente | Final do dia, com dados online podendo ser gravados. |
| Resposta | Processar somente dados novos e avançar a posição apenas após atualização válida dos indicadores. |
| Medida de resposta | A regra de somente dados novos é fato arquitetural em [arquitetura.md](../arquitetura.md#L68). O formato do checkpoint e cobertura dos dados são lacunas. |

### C-04, recuperação de falha parcial

| Parte | Descrição |
|---|---|
| Fonte | Operação da biblioteca. |
| Estímulo | Falhar depois da leitura ou de uma gravação parcial do lote. |
| Artefato | Batch, indicadores, checkpoint e observabilidade. |
| Ambiente | Execução diária interrompida. |
| Resposta | Reexecutar sem perder dados nem duplicar indicadores, mantendo a posição anterior até o sucesso. |
| Medida de resposta | Recuperação em até 15 minutos é cenário da utility tree em [utility-tree.md](../utility-tree.md#L9). Zero perda e zero duplicação são hipóteses, não requisitos. |

### C-05, auditoria de classificação

| Parte | Descrição |
|---|---|
| Fonte | Bibliotecário autenticado. |
| Estímulo | Alterar um livro de público para exclusivo ou de exclusivo para público. |
| Artefato | Acervo e registro de auditoria. |
| Ambiente | Operação normal, inclusive alterações concorrentes. |
| Resposta | Salvar a classificação e registrar a alteração. |
| Medida de resposta | 100% das alterações registradas, conforme RNF-08 em [prd.md](../prd.md#L38). Campos do registro não foram definidos. |

### C-06, proteção de dados dos leitores

| Parte | Descrição |
|---|---|
| Fonte | Operador ou componente sem autorização. |
| Estímulo | Tentar acessar dados pessoais usados pelo batch. |
| Artefato | Dados operacionais, batch e indicadores. |
| Ambiente | Execução batch ou consulta operacional. |
| Resposta | Bloquear acesso não autorizado e usar somente dados necessários aos indicadores. |
| Medida de resposta | Não há medida de LGPD no PRD. Bloqueio de 100% é hipótese de avaliação, não requisito. |

## 3. Utility tree priorizada

A ordem usa primeiro Importância A e Dificuldade A, depois A e M, depois A e B, conforme a utility tree. Os cenários de batch adicionados na arquitetura são hipóteses de análise, não cenários originais do PRD.

| Prioridade | Atributo | Refinamento | Cenário | I | D | Origem |
|---|---|---|---|---|---|---|
| 1 | Segurança | Confidencialidade | Bloquear livro exclusivo para plano gratuito. | A | M | RNF-06 e utility tree original. |
| 2 | Confiabilidade | Recuperação | Recuperar falha parcial sem perda ou duplicação. | A | A | Utility tree e ADR-002 em [arquitetura.md](../arquitetura.md). Zero perda e zero duplicação são hipóteses. |
| 3 | Confiabilidade | Consistência incremental | Processar somente dados posteriores ao checkpoint. | A | A | Restrição do cron e ADR-001 em [arquitetura.md](../arquitetura.md). |
| 4 | Confiabilidade | Disponibilidade | Executar batch sem degradar a API. | A | A | RNF-05 e ADR-005 em [arquitetura.md](../arquitetura.md). Métrica do impacto do batch é lacuna. |
| 5 | Segurança | Autenticidade e proteção de dados | Bloquear acesso não autorizado a dados de leitores. | A | A | Atributo Segurança e ADR-006. LGPD não tem requisito específico. |
| 6 | Eficiência de Performance | Tempo de resposta | Manter metas online durante o cron. | A | M | RNF-01, RNF-02 e RNF-07. |
| 7 | Manutenibilidade | Analisabilidade | Registrar classificação e versão de regra. | M | M | RNF-08 e utility tree original. |
| 8 | Capacidade de Interação | Clareza de mensagens | Informar necessidade de novo login após expiração. | A | B | Utility tree original e persona Rafael. |

## 4. Abordagens e táticas observadas no código

O código atual não implementa os componentes da arquitetura descrita. As únicas abordagens observadas são de teste, não de domínio.

| Cenário de maior prioridade | Abordagem ou tática observada | Evidência de arquivo e linha | Avaliação |
|---|---|---|---|
| C-01, confidencialidade | Seleção textual de um resultado para RF-07 e RNF-06. | [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L10) e [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L25). | Não verifica plano, token, livro ou conteúdo. Não implementa confidencialidade. |
| C-02, performance online | Nenhuma tática de cache, controle de carga ou medição de tempo. | [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L5). | O método retorna uma string; não existe API REST nem caminho mensurável. |
| C-03, consistência incremental | Nenhuma implementação de watermark, checkpoint ou leitura de dados. | [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L5). | O cron não existe no código. |
| C-04, recuperação | Nenhuma transação, retry, idempotência ou persistência. | [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L5). | A recuperação documentada é promessa, não comportamento observado. |
| C-05, auditoria | O resultado textual `AUDITADO` pode ser retornado para RNF-08. | [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L26). | Não grava auditoria e não identifica bibliotecário, data ou valor anterior. |
| C-06, dados pessoais | Nenhuma proteção, minimização, pseudonimização ou controle de acesso. | [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L1). | Não há dados pessoais nem mecanismo para protegê-los. |

A única tática de teste observada é o teste parametrizado, com cinco categorias e expectativa textual por regra. Evidência: [BibliotecaServiceTest.java](../../src/test/java/br/com/biblioteca/BibliotecaServiceTest.java#L14) e [BibliotecaServiceTest.java](../../src/test/java/br/com/biblioteca/BibliotecaServiceTest.java#L296). Isso comprova cobertura do contrato textual, não implementação dos requisitos de negócio.

## 5. Pontos de sensibilidade

1. A fronteira do watermark é o ponto mais sensível do batch. Um erro pode omitir empréstimos novos ou repetir dados. Relação: C-03 e ADR-001.
2. O compartilhamento de recursos entre cron e API é sensível para RNF-01, RNF-05 e RNF-07. Relação: C-02 e ADR-005.
3. A regra de autorização por plano é sensível para RNF-06. Uma decisão errada libera acervo exclusivo. Relação: C-01.
4. O critério de registro de auditoria é sensível para RNF-08. Sem campos e retenção definidos, a conformidade não pode ser demonstrada completamente. Relação: C-05.
5. A expiração de 30 minutos é sensível para Rafael, mas o aviso prévio não está em RF ou RNF. Relação: RNF-03 e utility tree.

## 6. Pontos de trade-off

1. Isolar recursos do batch protege performance e disponibilidade, mas pode aumentar custo e complexidade. Relação: C-02, ADR-005 e custos documentados na arquitetura.
2. Validar e registrar mais dados melhora confiabilidade e análise, mas pode atrasar a atualização de indicadores. Relação: C-03, C-04 e ADR-003.
3. Minimizar ou pseudonimizar dados reduz exposição, mas pode dificultar investigação operacional. Relação: C-06 e ADR-006.
4. Uma regra de checkpoint conservadora reduz repetição, mas pode atrasar o processamento de registros atrasados. Relação: C-03 e ADR-001.

## 7. Riscos

| Risco | Direcionador relacionado | Evidência |
|---|---|---|
| API degradar durante o batch | Uso rápido e continuidade online | RNF-01, RNF-05, RNF-07 e ADR-005. |
| Perda ou duplicação após falha parcial | Processamento diário confiável | C-04 e ADR-002. A tática não existe no código. |
| Registros atrasados ficarem fora do lote | Processar somente dados novos | C-03 e ADR-001. Regra não definida. |
| Livro exclusivo ser exposto ao plano gratuito | Benefício do assinante | RNF-06 e C-01. Não implementado no código. |
| Dados pessoais de leitores vazarem | Segurança e LGPD | C-06 e ADR-006. Não há requisito específico nem controle no código. |
| Indicador piorar por regra errada | Confiança nos indicadores | Registry e rollback são propostas na arquitetura, não implementação. |
| Auditoria não permitir investigação | Correção operacional do bibliotecário | RNF-08 e C-05. |

## 8. Não riscos

1. O workflow de CI não é um risco observado neste ciclo. Ele executa checkout, Java 21, Maven e `./mvnw -B verify`, conforme [ci.yml](../../.github/workflows/ci.yml#L15), e os checks do PR foram reportados verdes. Isso não prova os requisitos de negócio.
2. Os testes não são um risco de ausência de execução: há teste parametrizado para as regras e o workflow os executa. Isso não prova que as regras reais estejam implementadas.
3. O uso de uma classe mínima não é, por si só, um risco de produção confirmado. É uma limitação clara do estágio atual, pois a arquitetura documentada ainda não está representada no código.

## 9. Temas de risco

| Tema | Pergunta de decisão |
|---|---|
| Dados novos | Qual identificador ou combinação identifica uma linha nova, atrasada ou corrigida? |
| Falha parcial | O que é sucesso parcial e quando o checkpoint pode avançar? |
| Concorrência | Como impedir jobs sobrepostos sem bloquear o job seguinte indefinidamente? |
| Dados degradados | Quais campos e níveis de erro bloqueiam a publicação? |
| Indicadores | Quais indicadores existem e o que significa piorar em relação ao período anterior? |
| Regras | Quem aprova, promove e reverte uma versão de regra? |
| Online durante batch | Qual impacto é aceitável sobre RNF-01, RNF-02, RNF-05 e RNF-07? |
| Dados pessoais | Quais dados entram no cálculo, quem acessa e por quanto tempo são retidos? |
| Custos | Qual orçamento limita isolamento, observabilidade, retries e armazenamento? |

## 10. Promessas da arquitetura contra o código

| Promessa documentada | Evidência documental | O que o código faz | Divergência |
|---|---|---|---|
| API REST de conta, sessão, assinatura e acervo | PRD RF-01 a RF-12 e arquitetura, contexto e limites. | Não há controlador, endpoint ou modelo de domínio. | Divergência total. Existe apenas `BibliotecaService`. |
| Autenticação, token, logout e expiração | RF-03 a RF-05 e RNF-03. | `avaliar` retorna strings de teste e não mantém sessão ou token. Evidência: [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L5). | Divergência total. |
| Autorização de livro exclusivo por plano | RF-07 e RNF-06. | Retorna `ACESSO_CONCEDIDO` ou `ACESSO_NEGADO` conforme nome textual do caso. Evidência: [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L16). | Não há leitor, plano, livro ou bloqueio real. |
| Cadastro, classificação e auditoria do acervo | RF-10, RF-11, RF-12 e RNF-08. | Retorna status textual, sem acervo ou auditoria. Evidência: [BibliotecaService.java](../../src/main/java/br/com/biblioteca/BibliotecaService.java#L19). | Divergência total. |
| Cron incremental com checkpoint | Arquitetura, seção 3 e ADR-001. | Não há cron, fonte operacional, checkpoint ou indicadores. | Divergência total. |
| Idempotência, lock, retry e gates | Arquitetura, seção 11 e ADR-002 e ADR-003. | Não há persistência, concorrência, retry ou validação de lote. | Divergência total. |
| Registry, promoção, rollback e observabilidade | Arquitetura, seção 11 e ADR-004. | Não há registry, versões, rollback, logs ou métricas. | Divergência total. |
| Segurança e LGPD | Arquitetura, seção 11 e ADR-006. | Não há dados, autenticação, autorização, minimização ou retenção. | Divergência total. |
| Qualidade medida | RNF-01 a RNF-08 e utility tree. | Os testes comparam strings; não medem tempo, disponibilidade, usuários simultâneos ou auditoria. Evidência: [BibliotecaServiceTest.java](../../src/test/java/br/com/biblioteca/BibliotecaServiceTest.java#L14). | O CI está verde, mas a cobertura é de um contrato artificial. |

## 11. Ações priorizadas para o próximo ciclo

| Prioridade | Ação | Motivo | Evidência de conclusão |
|---|---|---|---|
| 1 | Definir e testar o modelo mínimo de conta, sessão, plano, livro e empréstimo. | Sem domínio não há API REST real nem validação dos RF-01 a RF-12. | Classes, testes de comportamento e contratos de API rastreáveis ao PRD. |
| 2 | Resolver a divergência entre RNF-01 e RNF-07 e definir como medir RNF-04 e RNF-05. | Hoje há metas sobrepostas e ausência de método operacional de medição. | Requisito ou decisão aprovada e teste mensurável. |
| 3 | Implementar C-01 com autorização real por plano e teste de não vazamento. | Segurança é o direcionador prioritário e RNF-06 é explícito. | Teste que verifica ausência de conteúdo, não somente status textual. |
| 4 | Definir watermark e política para registros atrasados ou corrigidos. | Evita perda ou repetição no cron. | Cenários de dados novos e atrasados aprovados. |
| 5 | Implementar transação, idempotência, lock e retry do batch. | Reduz risco de falha parcial e sobreposição. | Experimentos C-03 e C-04 verdes com persistência real. |
| 6 | Definir gates de qualidade, indicadores, registry, promoção e rollback. | Sem essas regras o resultado do batch não é auditável nem reproduzível. | ADRs aprovados e testes de indicadores. |
| 7 | Definir controles de acesso, minimização, retenção e avaliação LGPD. | O PRD não cobre dados pessoais, mas o batch pode processá-los. | Decisão documentada e testes de autorização e retenção. |
| 8 | Medir impacto e custo do batch no online. | Evita escolher isolamento sem evidência ou custo conhecido. | Experimento com RNF-01, RNF-02, RNF-05 e RNF-07 durante o batch. |
| 9 | Substituir o contrato artificial de `avaliar` por testes de comportamento de componentes reais. | Os 105 testes atuais passam, mas não demonstram os requisitos de negócio. | Testes rastreáveis ao domínio e remoção somente após cobertura equivalente. |
| 10 | Manter o CI e publicar relatórios úteis de Surefire e Failsafe. | O workflow já executa verify, mas Failsafe não é usado atualmente. | Runs verdes com relatórios reais dos testes de integração. |

## Conclusão do ciclo 01

O maior achado ATAM é a divergência entre a arquitetura documentada e o sistema implementado. A documentação descreve API, segurança, acervo, cron incremental e controles de dados; o código atual somente traduz nomes de casos em strings.

O CI está operacional e os testes estão verdes, mas isso valida apenas o contrato artificial de `BibliotecaService.avaliar`. O próximo ciclo deve priorizar o modelo de domínio, a autorização real e a definição do processamento incremental antes de considerar os atributos de qualidade arquiteturalmente demonstrados.
