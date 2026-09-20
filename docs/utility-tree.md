# Utility Tree

## Raiz: Utilidade

| Atributo | Refinamento | Cenário | I | D | Justificativa |
|---|---|---|---|---|---|
| Eficiência de Performance | Tempo de resposta | 95% das consultas de livros públicos devem responder em menos de 1 segundo. | A | M | I: A resposta rápida é essencial para o leitor casual em redes instáveis. D: Exige otimização da API, do banco de dados e dos testes de carga. |
| Eficiência de Performance | Capacidade | A API deve suportar até 500 empréstimos simultâneos sem erro. | M | A | I: O volume afeta a operação, mas não ocorre o tempo todo. D: A concorrência exige testes e controle adequado de recursos. |
| Confiabilidade | Disponibilidade | A API deve alcançar disponibilidade de 99,5% por mês. | A | M | I: Indisponibilidade impede leitores e bibliotecários de usar o sistema. D: Monitoramento, redundância e manutenção planejada são necessários. |
| Confiabilidade | Recuperação | O sistema deve recuperar o serviço em até 15 minutos após uma falha crítica. | A | M | I: A recuperação rápida reduz a interrupção dos empréstimos. D: São necessários backup, monitoramento e procedimento de restauração. |
| Segurança | Autenticidade | 100% das operações de cadastro e alteração de livros devem exigir autenticação válida. | A | B | I: Impede alterações indevidas no acervo. D: O controle pode ser implementado com autenticação e autorização padrão. |
| Segurança | Confidencialidade | 100% dos livros exclusivos devem ser bloqueados para leitores gratuitos. | A | M | I: A regra protege o benefício pago do leitor assinante. D: Exige validação de plano em todas as rotas de acesso ao acervo. |
| Capacidade de Interação | Clareza das mensagens | 100% dos erros de sessão expirada devem informar ao leitor que é necessário fazer login novamente. | A | B | I: Evita que o assinante fique sem entender por que perdeu o acesso. D: A mensagem pode ser definida diretamente no contrato da API. |
| Manutenibilidade | Analisabilidade | 100% das alterações de classificação dos livros devem registrar usuário, data e valor anterior. | M | M | I: O registro ajuda o bibliotecário a investigar erros de classificação. D: Exige armazenamento de auditoria e integração com as operações do acervo. |

## Critério de priorização

A priorização começa pelos cenários com I A e D A, pois são muito importantes e difíceis de alcançar. Depois, trata os cenários com I A e D B. Em seguida, trata os cenários com I B e D A. Por último, trata os cenários com I B e D B.
