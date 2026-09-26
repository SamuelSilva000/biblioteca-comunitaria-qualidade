# Aula 08: WireMock e integracao previsivel

## Objetivo

Validar a integracao HTTP com um catalogo externo de metadados de livros sem depender da internet. O WireMock simula respostas previsiveis para sucesso, falha, atraso, JSON invalido e verificacao de cabecalhos.

## Componentes

| Componente | Responsabilidade |
| --- | --- |
| `CatalogoExternoProvider` | Porta que define a busca de metadados por ISBN. |
| `HttpCatalogoExternoProvider` | Adapter HTTP que conhece URL, token, endpoint, cabecalhos e formato JSON externo. |
| `MetadadosLivroExterno` | DTO record com ISBN, titulo, autor e editora. |
| `LivroService` | Servico que delega a busca para a porta, sem conhecer HTTP. |

## Rastreabilidade

| Teste | Comportamento | Codigo relacionado |
| --- | --- | --- |
| INT-01 | Resposta 200 com JSON valido e mapeamento dos metadados. | `HttpCatalogoExternoProvider.buscarPorIsbn` |
| INT-02 | Resposta 404 com erro controlado sem detalhes externos. | `ProvedorIndisponivelException` |
| INT-03 | Resposta 500 com erro controlado. | `HttpCatalogoExternoProvider.interpretarResposta` |
| INT-04 | Atraso de 5 segundos interrompido pelo timeout de leitura. | `HttpRequest.timeout` |
| INT-05 | JSON malformado convertido em erro controlado. | `ObjectMapper` no adapter HTTP |
| INT-06 | Presenca dos cabecalhos Authorization e Accept. | Construcao de `HttpRequest` |

## Seguranca e previsibilidade

- Timeout de conexao de 2 segundos.
- Timeout de leitura de 3 segundos por requisicao.
- O token nao e impresso em log nem incluido em mensagens de excecao.
- O corpo externo nao e incluido em mensagens de excecao.
- Nao existe retry automatico.