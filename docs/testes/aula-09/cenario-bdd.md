# Cenario BDD: vigencia da assinatura

## Regra

Uma assinatura concede acesso a livros exclusivos se e somente se:
1. O plano for PREMIUM.
2. A assinatura nao estiver cancelada.
3. O instante atual for estritamente anterior ao vencimento.

## Cenario 1: assinatura premium vigente

Dado que Ana possui plano PREMIUM com vencimento em 2026-10-05T12:00:01Z
E a assinatura nao esta cancelada
Quando o instante atual for 2026-10-05T12:00:00Z
Entao o acesso a livros exclusivos deve ser concedido

## Cenario 2: assinatura premium vencida

Dado que Ana possui plano PREMIUM com vencimento em 2026-10-05T11:59:59Z
E a assinatura nao esta cancelada
Quando o instante atual for 2026-10-05T12:00:00Z
Entao o acesso a livros exclusivos deve ser negado

## Cenario 3: instante exato do vencimento

Dado que Ana possui plano PREMIUM com vencimento em 2026-10-05T12:00:00Z
E a assinatura nao esta cancelada
Quando o instante atual for exatamente 2026-10-05T12:00:00Z
Entao o acesso deve ser negado, pois o vencimento nao e estritamente posterior

## Cenario 4: plano gratuito

Dado que Ana possui plano FREE
Quando o instante atual for 2026-10-05T12:00:00Z
Entao o acesso a livros exclusivos deve ser negado

## Cenario 5: assinatura cancelada

Dado que Ana possui plano PREMIUM com vencimento em 2026-10-05T12:30:00Z
E a assinatura esta cancelada
Quando o instante atual for 2026-10-05T12:00:00Z
Entao o acesso a livros exclusivos deve ser negado

## Rastreabilidade

Requisito: RF-13 em docs/prd.md.