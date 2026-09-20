# Plano de TDD

## Fonte do inventário

Os documentos encontrados na inspeção foram `docs/personas.md`, `docs/prd.md`, `docs/utility-tree.md` e `docs/arquitetura.md`. O PRD contém 12 requisitos funcionais e 8 requisitos não funcionais. Não foram encontrados arquivos de código ou testes automatizados.

Este plano não cria regras novas. Quando um limite, campo ou comportamento não está definido nos documentos, o resultado esperado registra essa lacuna como pendente.

## Requisitos funcionais

### RF-01, criar conta

Origem: `docs/prd.md`, RF-01. Permitir ao leitor casual criar uma conta com nome, e-mail e senha.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Enviar nome, e-mail e senha válidos. | A conta deve ser criada. |
| Valor limite | Enviar cada campo com o menor tamanho aceito. | O tamanho mínimo não foi definido; o teste deve permanecer pendente até essa definição. |
| Entrada inválida | Omitir nome, e-mail ou senha. | O sistema deve informar erro para dados obrigatórios ausentes. |
| Conflito | Cadastrar uma conta com e-mail já existente. | O comportamento não foi definido; registrar a decisão necessária. |
| Estado proibido | Tentar criar conta com dados obrigatórios ausentes. | A conta não deve ser criada e o sistema deve informar erro. |

### RF-02, consultar e atualizar perfil

Origem: `docs/prd.md`, RF-02. Permitir ao leitor consultar e atualizar seu perfil.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Leitor autenticado consulta e altera dados válidos do próprio perfil. | O sistema deve mostrar os dados atuais e salvar a alteração. |
| Valor limite | Atualizar o perfil com o menor e o maior tamanho de campo permitido. | Os limites não foram definidos; o teste deve permanecer pendente. |
| Entrada inválida | Enviar valor de campo em formato inválido. | O comportamento e os campos válidos não foram definidos. |
| Conflito | Dois pedidos alteram o mesmo perfil em sequência concorrente. | A regra de conflito de atualização não foi definida. |
| Estado proibido | Tentar consultar ou atualizar perfil sem autenticação. | O acesso sem autenticação não é permitido pelo requisito de autenticação, mas o código de resposta não foi definido. |

### RF-03, realizar login

Origem: `docs/prd.md`, RF-03. Permitir ao leitor realizar login com e-mail e senha.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Enviar e-mail e senha de uma conta existente. | O sistema deve autenticar o leitor. |
| Valor limite | Realizar login no limite de expiração definido para a sessão. | O limite de login relacionado ao token não foi detalhado neste requisito; validar junto a RNF-03. |
| Entrada inválida | Enviar e-mail ou senha incorretos. | O sistema deve rejeitar as credenciais inválidas. |
| Conflito | Enviar dois logins simultâneos para a mesma conta. | O comportamento de múltiplas sessões não foi definido. |
| Estado proibido | Tentar acessar área autenticada sem login válido. | O acesso deve ser rejeitado; o código de resposta não foi definido. |

### RF-04, emitir token

Origem: `docs/prd.md`, RF-04. Emitir um token de sessão após login bem-sucedido.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Concluir login com credenciais válidas. | O sistema deve retornar um token válido. |
| Valor limite | Usar o token no limite de 30 minutos sem renovação. | O comportamento deve ser validado junto a RNF-03; o instante exato do limite não foi detalhado. |
| Entrada inválida | Solicitar token com credenciais inválidas. | O sistema não deve retornar token válido. |
| Conflito | Usar dois tokens emitidos para a mesma conta ao mesmo tempo. | A política de múltiplos tokens não foi definida. |
| Estado proibido | Tentar emitir token sem autenticar o leitor. | O sistema não deve emitir token válido. |

### RF-05, encerrar sessão

Origem: `docs/prd.md`, RF-05. Permitir ao leitor encerrar sua sessão.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Enviar logout com token válido. | O token usado deve ser invalidado. |
| Valor limite | Enviar logout no limite de expiração do token. | O comportamento para token no limite não foi detalhado. |
| Entrada inválida | Enviar logout com token malformado ou ausente. | O comportamento não foi definido; não presumir sucesso. |
| Conflito | Enviar dois logouts para o mesmo token. | A regra para logout repetido não foi definida. |
| Estado proibido | Usar o token depois de logout bem-sucedido. | O token deve ser inválido e não deve autorizar operação protegida. |

### RF-06, consultar plano

Origem: `docs/prd.md`, RF-06. Permitir ao leitor consultar seu plano atual.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Leitor autenticado consulta o plano gratuito ou assinante. | O sistema deve informar o plano atual. |
| Valor limite | Consultar o plano no instante de troca de plano. | O momento de efetivação da troca não foi definido. |
| Entrada inválida | Consultar plano com token inválido. | O acesso deve ser rejeitado; o código de resposta não foi definido. |
| Conflito | O leitor possui solicitações concorrentes de alteração de plano. | A resolução do conflito não foi definida. |
| Estado proibido | Tentar consultar plano sem autenticação. | O sistema não deve expor o plano sem autenticação válida. |

### RF-07, acessar livros exclusivos

Origem: `docs/prd.md`, RF-07. Permitir ao leitor assinante acessar livros exclusivos.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Leitor assinante autenticado solicita livro exclusivo. | O leitor assinante deve acessar o livro exclusivo. |
| Valor limite | Consultar o livro no instante de mudança entre plano gratuito e assinante. | A regra de efetivação da mudança não foi definida. |
| Entrada inválida | Enviar token inválido para consultar livro exclusivo. | O acesso deve ser rejeitado. |
| Conflito | A classificação do livro é alterada enquanto o leitor solicita acesso. | A ordem entre alteração e consulta não foi definida. |
| Estado proibido | Leitor gratuito tenta acessar livro exclusivo. | O sistema deve negar o acesso e não entregar o livro, conforme RF-07 e RNF-06. |

### RF-08, consultar e solicitar livro público

Origem: `docs/prd.md`, RF-08. Permitir ao leitor gratuito consultar e solicitar livros públicos.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Leitor gratuito autenticado consulta livro público disponível e solicita empréstimo. | O livro deve ser exibido e a solicitação deve ser aceita quando disponível. |
| Valor limite | Solicitar o último exemplar disponível. | A disponibilidade deve ser respeitada; quantidade de exemplares não foi definida. |
| Entrada inválida | Solicitar livro com identificador inexistente. | O comportamento não foi definido. |
| Conflito | Dois leitores solicitam o último exemplar ao mesmo tempo. | A regra de concorrência do empréstimo não foi definida. |
| Estado proibido | Leitor gratuito solicita livro exclusivo. | O acesso deve ser negado pela regra de exclusividade. |

### RF-09, consultar e solicitar livros pelo assinante

Origem: `docs/prd.md`, RF-09. Permitir ao leitor assinante consultar e solicitar livros públicos e exclusivos.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Leitor assinante autenticado solicita livro público e livro exclusivo disponíveis. | As duas solicitações devem ser aceitas quando houver disponibilidade. |
| Valor limite | Solicitar o último livro disponível de cada tipo. | A disponibilidade deve ser respeitada; quantidade de exemplares não foi definida. |
| Entrada inválida | Solicitar livro com identificador inexistente ou token inválido. | O comportamento específico não foi definido. |
| Conflito | Dois leitores solicitam o mesmo último exemplar simultaneamente. | A regra de concorrência não foi definida. |
| Estado proibido | Leitor assinante com sessão expirada solicita livro. | O sistema não deve autorizar a operação sem sessão válida. |

### RF-10, cadastrar livro

Origem: `docs/prd.md`, RF-10. Permitir ao bibliotecário cadastrar livros no acervo.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Bibliotecário autenticado envia todos os dados obrigatórios válidos. | O sistema deve criar o livro. |
| Valor limite | Enviar os campos do livro com os menores e maiores tamanhos aceitos. | Os limites e campos obrigatórios não foram definidos. |
| Entrada inválida | Omitir um dado obrigatório ou enviar formato inválido. | O sistema deve rejeitar dados inválidos; o conjunto de validações não foi detalhado. |
| Conflito | Cadastrar livro com identificador ou título já existente. | A regra de duplicidade não foi definida. |
| Estado proibido | Leitor sem autorização tenta cadastrar livro. | A operação deve exigir autenticação válida; a autorização específica do papel não foi detalhada. |

### RF-11, classificar livro

Origem: `docs/prd.md`, RF-11. Permitir ao bibliotecário definir se um livro é público ou exclusivo para assinantes.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Bibliotecário autorizado classifica um livro como público ou exclusivo. | O sistema deve salvar a classificação e aplicar a regra de acesso correspondente. |
| Valor limite | Alterar a classificação no instante de uma solicitação de acesso. | A ordem entre alteração e solicitação não foi definida. |
| Entrada inválida | Enviar classificação diferente de pública ou exclusiva. | O sistema deve rejeitar o valor; os valores aceitos são os dois descritos no requisito. |
| Conflito | Dois bibliotecários alteram a classificação do mesmo livro. | A regra de concorrência não foi definida; o histórico de alterações deve ser registrado conforme RNF-08. |
| Estado proibido | Usuário sem autorização tenta alterar classificação. | A alteração não deve ser permitida; a regra de papel e código de erro não foram detalhados. |

### RF-12, acompanhar empréstimos

Origem: `docs/prd.md`, RF-12. Permitir ao bibliotecário acompanhar os empréstimos.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Bibliotecário consulta empréstimos existentes. | O sistema deve mostrar leitor, livro, data de início e situação. |
| Valor limite | Consultar o primeiro ou último empréstimo do período disponível. | O período e seus limites não foram definidos. |
| Entrada inválida | Consultar com filtro ou identificador em formato inválido. | Filtros e validações não foram definidos. |
| Conflito | Um empréstimo muda de situação durante a consulta. | A consistência da leitura durante a alteração não foi definida. |
| Estado proibido | Leitor não autorizado tenta consultar a operação de empréstimos. | O acesso operacional deve ser restrito; a regra detalhada de autorização não foi definida. |

## Requisitos não funcionais

### RNF-01, tempo de consultas de perfil e acervo

Origem: `docs/prd.md`, RNF-01. Responder em até 500 milissegundos em pelo menos 95% das solicitações.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Medir uma carga de consultas de perfil e acervo. | Pelo menos 95% devem responder em até 500 milissegundos. |
| Valor limite | Medir exatamente 500 milissegundos e exatamente 95% das solicitações. | A interpretação de igualdade no limite não foi definida; registrar decisão. |
| Entrada inválida | Enviar consulta inválida durante a medição. | A inclusão de erros na amostra não foi definida. |
| Conflito | Executar consultas enquanto o cron processa o batch. | O impacto do batch sobre a métrica não foi definido; validar como hipótese em `docs/arquitetura.md`. |
| Estado proibido | Aceitar como aprovado um resultado abaixo de 95% ou acima de 500 milissegundos. | O resultado não atende ao RNF-01. |

### RNF-02, tempo de login e logout

Origem: `docs/prd.md`, RNF-02. Responder em até 800 milissegundos em pelo menos 95% das solicitações.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Medir login e logout válidos sob carga definida pelo teste. | Pelo menos 95% devem responder em até 800 milissegundos. |
| Valor limite | Medir exatamente 800 milissegundos e exatamente 95%. | A interpretação da igualdade não foi definida. |
| Entrada inválida | Medir login com credenciais inválidas. | A inclusão de respostas inválidas na amostra não foi definida. |
| Conflito | Medir login e logout durante o cron diário. | O impacto do batch não foi definido. |
| Estado proibido | Declarar conformidade com menos de 95% das respostas no limite. | O resultado não atende ao RNF-02. |

### RNF-03, expiração do token

Origem: `docs/prd.md`, RNF-03. Expirar o token após 30 minutos sem renovação.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Usar token antes de completar 30 minutos sem renovação. | O requisito indica que a expiração ocorre após 30 minutos; o acesso antes desse ponto deve ser validado. |
| Valor limite | Usar token exatamente aos 30 minutos e imediatamente depois. | O comportamento exato no instante limite não foi definido; deve ser esclarecido. |
| Entrada inválida | Usar token malformado ou inexistente. | O token não deve autorizar acesso; a resposta não foi definida. |
| Conflito | Renovar ou usar token em outro dispositivo antes de 30 minutos. | A renovação e a política entre dispositivos não foram definidas. |
| Estado proibido | Usar token após 30 minutos sem renovação. | O token deve estar expirado e não deve autorizar acesso. |

### RNF-04, usuários simultâneos

Origem: `docs/prd.md`, RNF-04. Suportar pelo menos 100 usuários simultâneos utilizando a API.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Executar solicitações de 100 usuários simultâneos. | A API deve suportar pelo menos 100 usuários simultâneos. |
| Valor limite | Executar exatamente 100 usuários simultâneos. | O resultado deve ser considerado conforme se atender à definição de suporte, mas essa definição operacional não foi detalhada. |
| Entrada inválida | Enviar requisições malformadas dentro da carga. | O tratamento de requisições inválidas na medição não foi definido. |
| Conflito | Executar 100 usuários simultâneos enquanto o cron processa dados. | O impacto do batch sobre a capacidade não foi definido. |
| Estado proibido | Declarar suporte sem medir 100 usuários simultâneos. | A conformidade não pode ser confirmada sem medição. |

### RNF-05, disponibilidade mensal

Origem: `docs/prd.md`, RNF-05. Disponibilizar a API em 99,5% do tempo mensal.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Medir a disponibilidade da API durante um mês. | A disponibilidade medida deve ser de pelo menos 99,5%. |
| Valor limite | Medir exatamente 99,5% no período mensal. | O resultado deve atender à meta numérica. A janela de medição não foi detalhada. |
| Entrada inválida | Monitoramento recebe resposta malformada do endpoint. | O tratamento dessa medição inválida não foi definido. |
| Conflito | Medir disponibilidade durante a execução do cron. | O impacto do cron não foi definido; deve ser medido. |
| Estado proibido | Declarar disponibilidade sem definir janela, manutenção e dependências. | A conformidade não pode ser demonstrada completamente. |

### RNF-06, bloqueio de livros exclusivos

Origem: `docs/prd.md`, RNF-06. Garantir que leitor gratuito não acesse livros exclusivos; critério de 100% das tentativas.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Executar tentativa de leitor gratuito contra livro exclusivo. | O acesso deve ser negado e o conteúdo não deve ser entregue. |
| Valor limite | Executar exatamente 100 tentativas não autorizadas. | Todas as 100 tentativas devem ser bloqueadas. |
| Entrada inválida | Enviar token inválido ou livro inexistente. | O comportamento específico não foi definido; não contar como aprovação automática. |
| Conflito | Alterar plano ou classificação enquanto a tentativa ocorre. | A ordem de avaliação não foi definida. |
| Estado proibido | Entregar qualquer dado de livro exclusivo a leitor gratuito. | O resultado viola RNF-06. |

### RNF-07, tempo de consultas públicas

Origem: `docs/prd.md`, RNF-07. Concluir consultas públicas em até 1 segundo em pelo menos 95% das solicitações.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Medir consultas públicas válidas. | Pelo menos 95% devem concluir em até 1 segundo. |
| Valor limite | Medir exatamente 1 segundo e exatamente 95%. | A interpretação de igualdade no limite não foi detalhada. |
| Entrada inválida | Enviar consulta pública com filtro inválido. | A inclusão de erro na amostra não foi definida. |
| Conflito | Medir consultas públicas durante o batch. | O impacto do batch não foi definido. |
| Estado proibido | Declarar conformidade com menos de 95% no limite de 1 segundo. | O resultado não atende ao RNF-07. |

### RNF-08, auditoria de classificação

Origem: `docs/prd.md`, RNF-08. Registrar 100% das alterações de classificação realizadas pelo bibliotecário.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Bibliotecário altera livro público para exclusivo ou exclusivo para público. | A alteração deve gerar registro de auditoria. |
| Valor limite | Realizar exatamente uma alteração de classificação. | A única alteração deve ser registrada. |
| Entrada inválida | Enviar classificação inválida. | A alteração deve ser rejeitada; não deve gerar registro de alteração válida. |
| Conflito | Dois bibliotecários alteram o mesmo livro simultaneamente. | A resolução da concorrência não foi definida; cada alteração efetivamente realizada deve ser rastreável. |
| Estado proibido | Alterar classificação sem registro de auditoria. | O resultado viola RNF-08. |

## Restrição do cron sem identificador RF ou RNF

Origem: `docs/arquitetura.md`, seção 3, e contexto do projeto. O cron deve executar diariamente ao final do dia, processar somente dados novos, como empréstimos e devoluções do dia, e atualizar indicadores internos.

| Tipo | Caso de teste | Resultado esperado |
|---|---|---|
| Caminho feliz | Executar o cron no final do dia com empréstimos e devoluções novos. | O processamento deve considerar somente dados novos e atualizar indicadores internos. |
| Valor limite | Executar com nenhum dado novo. | O comportamento dos indicadores quando não há dados novos não foi definido. |
| Entrada inválida | Encontrar dados sem os campos necessários para processamento. | Campos necessários e tratamento de dado inválido não foram definidos. |
| Conflito | Iniciar duas execuções do cron ou executar enquanto dados ainda estão sendo gravados. | Lock, concorrência e fronteira de dados são decisões propostas, não regras da fonte de verdade. |
| Estado proibido | Reprocessar dados já confirmados como novos ou atualizar indicadores com dados fora da regra definida. | Isso contrariaria a restrição de processar somente dados novos; o critério técnico de identificação ainda não foi definido. |

## Ambiguidades, lacunas e perguntas

1. `docs/prd.md`, RF-01 não define campos completos, tamanho mínimo, tamanho máximo, formato de e-mail, regra de senha ou duplicidade de conta.
2. RF-02 não define campos de perfil, limites, concorrência de atualização ou dados que o leitor pode alterar.
3. RF-06, RF-07 e RF-09 não definem criação, pagamento, renovação, cancelamento ou efetivação do plano assinante.
4. RF-08, RF-09 e RF-12 não definem quantidade de exemplares, disponibilidade, devolução, conflitos de empréstimo ou campos completos do empréstimo.
5. RF-03 a RF-05 não definem formato do token, múltiplas sessões, resposta para token inválido ou aviso antes da expiração.
6. RNF-01 e RNF-07 têm metas diferentes para consultas de acervo: 500 milissegundos e 1 segundo. A prioridade não foi definida.
7. RNF-04 não define o que significa suportar 100 usuários simultâneos nem a carga usada na medição.
8. RNF-05 não define janela de manutenção, dependências, método de medição ou impacto do cron.
9. RNF-06 trata acesso a livros exclusivos, mas não define proteção de dados pessoais dos leitores.
10. RNF-08 não define campos, retenção ou consulta dos registros de auditoria.
11. A restrição do cron não define horário exato, fuso, identificador de dado novo, watermark, tratamento de atraso, indicadores, retry, lock ou critério de publicação.
12. `docs/utility-tree.md` contém metas de recuperação e desempenho, mas algumas não possuem RF ou RNF correspondente no PRD.
13. `docs/arquitetura.md` registra watermark, idempotência, lock, retry, gates, registry, rollback, observabilidade, LGPD e custo como decisões propostas ou hipóteses, não como regras confirmadas.

## Perguntas abertas para o próximo ciclo

1. Quais são os campos e limites válidos de conta, perfil, livro, empréstimo e devolução?
2. Qual comportamento deve ocorrer para conta, livro ou empréstimo duplicado?
3. Como o plano assinante é criado, renovado, cancelado e efetivado?
4. Qual é o formato do token e como funciona a sessão entre dispositivos?
5. Qual é a regra exata para consultas no limite de expiração de 30 minutos?
6. Qual meta de tempo prevalece para consultas públicas: 500 milissegundos ou 1 segundo?
7. Como medir 100 usuários simultâneos e disponibilidade de 99,5%?
8. Quais indicadores o cron atualiza e quais dados pessoais são necessários?
9. Como identificar dados novos, dados atrasados e dados corrigidos?
10. O que fazer quando o indicador novo for pior que o anterior?
11. Quais são a política de lock, o limite de retry e o comportamento de falha parcial?
12. Quais campos e retenção são exigidos para auditoria e observabilidade?

## Matriz de rastreabilidade TDD

Cada método parametrizado executa cinco casos: caminho feliz, valor limite, entrada inválida, conflito e estado proibido. O status foi obtido após `mvn test`.

| Regra | Caso para teste | Método de teste | Status |
|---|---|---|---|
| RF-01 | 5 casos | `RF01_criarConta` | RED, 5 falhas |
| RF-02 | 5 casos | `RF02_atualizarPerfil` | RED, 5 falhas |
| RF-03 | 5 casos | `RF03_realizarLogin` | RED, 5 falhas |
| RF-04 | 5 casos | `RF04_emitirToken` | RED, 5 falhas |
| RF-05 | 5 casos | `RF05_encerrarSessao` | RED, 5 falhas |
| RF-06 | 5 casos | `RF06_consultarPlano` | RED, 5 falhas |
| RF-07 | 5 casos | `RF07_acessarLivrosExclusivos` | RED, 5 falhas |
| RF-08 | 5 casos | `RF08_solicitarLivroPublico` | RED, 5 falhas |
| RF-09 | 5 casos | `RF09_solicitarLivrosAssinante` | RED, 5 falhas |
| RF-10 | 5 casos | `RF10_cadastrarLivro` | RED, 5 falhas |
| RF-11 | 5 casos | `RF11_classificarLivro` | RED, 5 falhas |
| RF-12 | 5 casos | `RF12_acompanharEmprestimos` | RED, 5 falhas |
| RNF-01 | 5 casos | `RNF01_tempoPerfilAcervo` | RED, 5 falhas |
| RNF-02 | 5 casos | `RNF02_tempoLoginLogout` | RED, 5 falhas |
| RNF-03 | 5 casos | `RNF03_expiracaoToken` | RED, 5 falhas |
| RNF-04 | 5 casos | `RNF04_usuariosSimultaneos` | RED, 5 falhas |
| RNF-05 | 5 casos | `RNF05_disponibilidadeMensal` | RED, 5 falhas |
| RNF-06 | 5 casos | `RNF06_bloqueioLivroExclusivo` | RED, 5 falhas |
| RNF-07 | 5 casos | `RNF07_tempoConsultaPublica` | RED, 5 falhas |
| RNF-08 | 5 casos | `RNF08_auditoriaClassificacao` | RED, 5 falhas |
| Restrição do cron | 5 casos | `CRON_restricaoDadosNovos` | RED, 5 falhas |

### Regras sem teste

Nenhuma. Todas as 20 regras do PRD e a restrição do cron possuem pelo menos um teste executável, totalizando 105 execuções.

### Resultado da suíte

O comando `mvn test` executou 105 testes, com 105 falhas, 0 erros e 0 testes ignorados. Todas as falhas ocorreram porque `BibliotecaService.avaliar` ainda retorna `null`; nenhuma regra de negócio foi implementada.
