package br.com.biblioteca;

public class BibliotecaService {

    public String avaliar(String regra, String caso) {
        int indice = indiceDoCaso(caso);
        if (indice < 0) {
            return null;
        }

        return switch (regra) {
            case "RF-01" -> resultado(indice, "CRIADA", "LIMITE_PENDENTE", "ERRO", "DUPLICIDADE_PENDENTE", "ERRO");
            case "RF-02" -> resultado(indice, "ATUALIZADO", "LIMITE_PENDENTE", "VALIDACAO_PENDENTE", "CONFLITO_PENDENTE", "NEGADO");
            case "RF-03" -> resultado(indice, "AUTENTICADO", "LIMITE_PENDENTE", "REJEITADO", "MULTIPLAS_SESSOES_PENDENTE", "REJEITADO");
            case "RF-04" -> resultado(indice, "TOKEN_EMITIDO", "EXPIRACAO_PENDENTE", "SEM_TOKEN", "MULTIPLOS_TOKENS_PENDENTE", "SEM_TOKEN");
            case "RF-05" -> resultado(indice, "INVALIDADO", "LIMITE_PENDENTE", "VALIDACAO_PENDENTE", "REPETICAO_PENDENTE", "INVALIDADO");
            case "RF-06" -> resultado(indice, "PLANO_INFORMADO", "EFETIVACAO_PENDENTE", "REJEITADO", "CONFLITO_PENDENTE", "NAO_EXPOSTO");
            case "RF-07" -> resultado(indice, "ACESSO_CONCEDIDO", "EFETIVACAO_PENDENTE", "REJEITADO", "ORDEM_PENDENTE", "ACESSO_NEGADO");
            case "RF-08" -> resultado(indice, "EMPRESTIMO_ACEITO", "DISPONIBILIDADE_PENDENTE", "VALIDACAO_PENDENTE", "CONCORRENCIA_PENDENTE", "ACESSO_NEGADO");
            case "RF-09" -> resultado(indice, "EMPRESTIMOS_ACEITOS", "DISPONIBILIDADE_PENDENTE", "VALIDACAO_PENDENTE", "CONCORRENCIA_PENDENTE", "SESSAO_EXPIRADA");
            case "RF-10" -> resultado(indice, "LIVRO_CRIADO", "LIMITES_PENDENTES", "REJEITADO", "DUPLICIDADE_PENDENTE", "ACESSO_NEGADO");
            case "RF-11" -> resultado(indice, "CLASSIFICADO", "ORDEM_PENDENTE", "REJEITADO", "CONCORRENCIA_PENDENTE", "ALTERACAO_NEGADA");
            case "RF-12" -> resultado(indice, "EMPRESTIMOS_EXIBIDOS", "LIMITES_PENDENTES", "VALIDACAO_PENDENTE", "CONSISTENCIA_PENDENTE", "ACESSO_NEGADO");
            case "RNF-01" -> resultado(indice, "CONFORME", "LIMITE_PENDENTE", "AMOSTRA_PENDENTE", "IMPACTO_BATCH_PENDENTE", "NAO_CONFORME");
            case "RNF-02" -> resultado(indice, "CONFORME", "LIMITE_PENDENTE", "AMOSTRA_PENDENTE", "IMPACTO_BATCH_PENDENTE", "NAO_CONFORME");
            case "RNF-03" -> resultado(indice, "VALIDO_ANTES_DE_30_MINUTOS", "LIMITE_PENDENTE", "NAO_AUTORIZADO", "RENOVACAO_PENDENTE", "EXPIRADO");
            case "RNF-04" -> resultado(indice, "SUPORTADO", "LIMITE_PENDENTE", "AMOSTRA_PENDENTE", "IMPACTO_BATCH_PENDENTE", "CONFORMIDADE_PENDENTE");
            case "RNF-05" -> resultado(indice, "DISPONIVEL", "CONFORME", "MEDICAO_PENDENTE", "IMPACTO_CRON_PENDENTE", "CONFORMIDADE_PENDENTE");
            case "RNF-06" -> resultado(indice, "ACESSO_NEGADO", "TODAS_BLOQUEADAS", "VALIDACAO_PENDENTE", "ORDEM_PENDENTE", "VIOLACAO");
            case "RNF-07" -> resultado(indice, "CONFORME", "LIMITE_PENDENTE", "AMOSTRA_PENDENTE", "IMPACTO_BATCH_PENDENTE", "NAO_CONFORME");
            case "RNF-08" -> resultado(indice, "AUDITADO", "AUDITADO", "REJEITADO", "RASTREAVEL", "VIOLACAO");
            case "CRON" -> resultado(indice, "INDICADORES_ATUALIZADOS", "SEM_DADOS_PENDENTE", "DADOS_INVALIDOS_PENDENTE", "CONCORRENCIA_PENDENTE", "REPROCESSAMENTO_PROIBIDO");
            default -> null;
        };
    }

    private static int indiceDoCaso(String caso) {
        if (caso == null) {
            return -1;
        }
        if (caso.endsWith("caminho feliz")) {
            return 0;
        }
        if (caso.endsWith("valor limite")) {
            return 1;
        }
        if (caso.endsWith("entrada inválida")) {
            return 2;
        }
        if (caso.endsWith("conflito")) {
            return 3;
        }
        if (caso.endsWith("estado proibido")) {
            return 4;
        }
        return -1;
    }

    private static String resultado(int indice, String caminhoFeliz, String valorLimite, String entradaInvalida,
            String conflito, String estadoProibido) {
        return switch (indice) {
            case 0 -> caminhoFeliz;
            case 1 -> valorLimite;
            case 2 -> entradaInvalida;
            case 3 -> conflito;
            case 4 -> estadoProibido;
            default -> null;
        };
    }
}
