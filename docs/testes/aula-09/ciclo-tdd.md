# Ciclo TDD completo da Aula 09

## Regra
Vigencia de assinatura premium para acesso a livros exclusivos (RF-13).

## Cenario BDD
Ver docs/testes/aula-09/cenario-bdd.md.

## Ciclos

### RED
Commit com os cinco testes falhando por UnsupportedOperationException. Evidencia em evidencia-red.txt.

### GREEN
Implementacao minima com Clock injetado. 119 testes verdes. Evidencia em evidencia-green.txt.

### REFACTOR
Extracao de constante, metodo auxiliar e nomes claros. Comportamento inalterado. Evidencia em evidencia-refactor.txt.

## Rastreabilidade

| Requisito | Cenario BDD | Testes | Ciclo TDD |
|---|---|---|---|
| RF-13 | Cenario 1 a 5 em cenario-bdd.md | 8 testes em AssinaturaTest | RED, GREEN, REFACTOR |

## Decisao de design

O metodo podeAcessarExclusivo recebe Clock em vez de consultar Instant.now internamente. Isso torna o teste deterministico e o dominio testavel. A fronteira exata do vencimento (instante atual estritamente anterior) esta fixada pelo cenario 3.