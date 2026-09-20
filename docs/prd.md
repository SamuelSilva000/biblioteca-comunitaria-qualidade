# Requisitos do produto

## Requisitos funcionais e não funcionais

| ID | descrição | tipo | escopo | critério de aceite |
|---|---|---|---|---|
| RF-01 | Permitir ao leitor casual criar uma conta com nome, e-mail e senha. | RF | E1 | O sistema deve criar a conta quando os dados forem válidos e informar erro quando houver dados obrigatórios ausentes. |
| RF-02 | Permitir ao leitor consultar e atualizar seu perfil. | RF | E1 | O leitor deve visualizar seus dados atuais e conseguir alterá-los após autenticação. |
| RF-03 | Permitir ao leitor realizar login com e-mail e senha. | RF | E2 | O sistema deve autenticar credenciais válidas e rejeitar credenciais inválidas. |
| RF-04 | Emitir um token de sessão após o login bem-sucedido. | RF | E2 | O sistema deve retornar um token válido somente após autenticar o leitor. |
| RF-05 | Permitir ao leitor encerrar sua sessão. | RF | E2 | O logout deve invalidar o token usado na solicitação. |
| RF-06 | Permitir ao leitor consultar seu plano atual. | RF | E3 | O sistema deve informar se o leitor está no plano gratuito ou no plano assinante. |
| RF-07 | Permitir ao leitor assinante acessar livros exclusivos. | RF | E3 | Um leitor assinante autenticado deve acessar livros exclusivos, e um leitor gratuito deve receber resposta de acesso não autorizado. |
| RF-08 | Permitir ao leitor gratuito consultar e solicitar livros públicos. | RF | E4 | O leitor gratuito deve visualizar livros públicos e conseguir solicitar o empréstimo de um livro disponível. |
| RF-09 | Permitir ao leitor assinante consultar e solicitar livros públicos e exclusivos. | RF | E4 | O leitor assinante deve visualizar os dois tipos de livro e solicitar o empréstimo quando houver disponibilidade. |
| RF-10 | Permitir ao bibliotecário cadastrar livros no acervo. | RF | E4 | O sistema deve criar o livro quando o bibliotecário informar os dados obrigatórios. |
| RF-11 | Permitir ao bibliotecário definir se um livro é público ou exclusivo para assinantes. | RF | E4 | O sistema deve salvar a classificação escolhida e aplicar a regra de acesso correspondente. |
| RF-12 | Permitir ao bibliotecário acompanhar os empréstimos. | RF | E4 | O bibliotecário deve consultar os empréstimos com leitor, livro, data de início e situação. |
| RNF-01 | Responder às consultas de perfil e acervo em até 500 milissegundos em pelo menos 95% das solicitações. | RNF | E1 | 500 milissegundos e 95%. |
| RNF-02 | Responder às operações de login e logout em até 800 milissegundos em pelo menos 95% das solicitações. | RNF | E2 | 800 milissegundos e 95%. |
| RNF-03 | Expirar o token de sessão após 30 minutos sem renovação. | RNF | E2 | 30 minutos. |
| RNF-04 | Suportar pelo menos 100 usuários simultâneos utilizando a API. | RNF | E2 | 100 usuários simultâneos. |
| RNF-05 | Disponibilizar a API em 99,5% do tempo mensal. | RNF | E3 | 99,5% ao mês. |
| RNF-06 | Garantir que um leitor gratuito não acesse livros exclusivos sem autorização. | RNF | E3 | 100% das tentativas. |
| RNF-07 | Concluir consultas públicas do acervo em até 1 segundo em pelo menos 95% das solicitações. | RNF | E4 | 1 segundo e 95%. |
| RNF-08 | Registrar 100% das alterações de classificação realizadas pelo bibliotecário. | RNF | E4 | 100% das alterações. |
