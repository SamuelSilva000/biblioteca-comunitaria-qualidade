package br.com.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BibliotecaServiceTest {

    private final BibliotecaService service = new BibliotecaService();

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf01Casos")
    void RF01_criarConta(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-01", nome));
    }

    static Stream<Arguments> rf01Casos() {
        return casos("RF-01", "CRIADA", "LIMITE_PENDENTE", "ERRO", "DUPLICIDADE_PENDENTE", "ERRO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf02Casos")
    void RF02_atualizarPerfil(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-02", nome));
    }

    static Stream<Arguments> rf02Casos() {
        return casos("RF-02", "ATUALIZADO", "LIMITE_PENDENTE", "VALIDACAO_PENDENTE", "CONFLITO_PENDENTE", "NEGADO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf03Casos")
    void RF03_realizarLogin(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-03", nome));
    }

    static Stream<Arguments> rf03Casos() {
        return casos("RF-03", "AUTENTICADO", "LIMITE_PENDENTE", "REJEITADO", "MULTIPLAS_SESSOES_PENDENTE", "REJEITADO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf04Casos")
    void RF04_emitirToken(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-04", nome));
    }

    static Stream<Arguments> rf04Casos() {
        return casos("RF-04", "TOKEN_EMITIDO", "EXPIRACAO_PENDENTE", "SEM_TOKEN", "MULTIPLOS_TOKENS_PENDENTE", "SEM_TOKEN");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf05Casos")
    void RF05_encerrarSessao(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-05", nome));
    }

    static Stream<Arguments> rf05Casos() {
        return casos("RF-05", "INVALIDADO", "LIMITE_PENDENTE", "VALIDACAO_PENDENTE", "REPETICAO_PENDENTE", "INVALIDADO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf06Casos")
    void RF06_consultarPlano(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-06", nome));
    }

    static Stream<Arguments> rf06Casos() {
        return casos("RF-06", "PLANO_INFORMADO", "EFETIVACAO_PENDENTE", "REJEITADO", "CONFLITO_PENDENTE", "NAO_EXPOSTO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf07Casos")
    void RF07_acessarLivrosExclusivos(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-07", nome));
    }

    static Stream<Arguments> rf07Casos() {
        return casos("RF-07", "ACESSO_CONCEDIDO", "EFETIVACAO_PENDENTE", "REJEITADO", "ORDEM_PENDENTE", "ACESSO_NEGADO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf08Casos")
    void RF08_solicitarLivroPublico(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-08", nome));
    }

    static Stream<Arguments> rf08Casos() {
        return casos("RF-08", "EMPRESTIMO_ACEITO", "DISPONIBILIDADE_PENDENTE", "VALIDACAO_PENDENTE", "CONCORRENCIA_PENDENTE", "ACESSO_NEGADO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf09Casos")
    void RF09_solicitarLivrosAssinante(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-09", nome));
    }

    static Stream<Arguments> rf09Casos() {
        return casos("RF-09", "EMPRESTIMOS_ACEITOS", "DISPONIBILIDADE_PENDENTE", "VALIDACAO_PENDENTE", "CONCORRENCIA_PENDENTE", "SESSAO_EXPIRADA");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf10Casos")
    void RF10_cadastrarLivro(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-10", nome));
    }

    static Stream<Arguments> rf10Casos() {
        return casos("RF-10", "LIVRO_CRIADO", "LIMITES_PENDENTES", "REJEITADO", "DUPLICIDADE_PENDENTE", "ACESSO_NEGADO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf11Casos")
    void RF11_classificarLivro(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-11", nome));
    }

    static Stream<Arguments> rf11Casos() {
        return casos("RF-11", "CLASSIFICADO", "ORDEM_PENDENTE", "REJEITADO", "CONCORRENCIA_PENDENTE", "ALTERACAO_NEGADA");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rf12Casos")
    void RF12_acompanharEmprestimos(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RF-12", nome));
    }

    static Stream<Arguments> rf12Casos() {
        return casos("RF-12", "EMPRESTIMOS_EXIBIDOS", "LIMITES_PENDENTES", "VALIDACAO_PENDENTE", "CONSISTENCIA_PENDENTE", "ACESSO_NEGADO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rnf01Casos")
    void RNF01_tempoPerfilAcervo(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RNF-01", nome));
    }

    static Stream<Arguments> rnf01Casos() {
        return casos("RNF-01", "CONFORME", "LIMITE_PENDENTE", "AMOSTRA_PENDENTE", "IMPACTO_BATCH_PENDENTE", "NAO_CONFORME");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rnf02Casos")
    void RNF02_tempoLoginLogout(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RNF-02", nome));
    }

    static Stream<Arguments> rnf02Casos() {
        return casos("RNF-02", "CONFORME", "LIMITE_PENDENTE", "AMOSTRA_PENDENTE", "IMPACTO_BATCH_PENDENTE", "NAO_CONFORME");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rnf03Casos")
    void RNF03_expiracaoToken(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RNF-03", nome));
    }

    static Stream<Arguments> rnf03Casos() {
        return casos("RNF-03", "VALIDO_ANTES_DE_30_MINUTOS", "LIMITE_PENDENTE", "NAO_AUTORIZADO", "RENOVACAO_PENDENTE", "EXPIRADO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rnf04Casos")
    void RNF04_usuariosSimultaneos(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RNF-04", nome));
    }

    static Stream<Arguments> rnf04Casos() {
        return casos("RNF-04", "SUPORTADO", "LIMITE_PENDENTE", "AMOSTRA_PENDENTE", "IMPACTO_BATCH_PENDENTE", "CONFORMIDADE_PENDENTE");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rnf05Casos")
    void RNF05_disponibilidadeMensal(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RNF-05", nome));
    }

    static Stream<Arguments> rnf05Casos() {
        return casos("RNF-05", "DISPONIVEL", "CONFORME", "MEDICAO_PENDENTE", "IMPACTO_CRON_PENDENTE", "CONFORMIDADE_PENDENTE");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rnf06Casos")
    void RNF06_bloqueioLivroExclusivo(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RNF-06", nome));
    }

    static Stream<Arguments> rnf06Casos() {
        return casos("RNF-06", "ACESSO_NEGADO", "TODAS_BLOQUEADAS", "VALIDACAO_PENDENTE", "ORDEM_PENDENTE", "VIOLACAO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rnf07Casos")
    void RNF07_tempoConsultaPublica(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RNF-07", nome));
    }

    static Stream<Arguments> rnf07Casos() {
        return casos("RNF-07", "CONFORME", "LIMITE_PENDENTE", "AMOSTRA_PENDENTE", "IMPACTO_BATCH_PENDENTE", "NAO_CONFORME");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("rnf08Casos")
    void RNF08_auditoriaClassificacao(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("RNF-08", nome));
    }

    static Stream<Arguments> rnf08Casos() {
        return casos("RNF-08", "AUDITADO", "AUDITADO", "REJEITADO", "RASTREAVEL", "VIOLACAO");
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("cronCasos")
    void CRON_restricaoDadosNovos(String nome, String esperado) {
        assertEquals(esperado, service.avaliar("CRON", nome));
    }

    static Stream<Arguments> cronCasos() {
        return casos("CRON", "INDICADORES_ATUALIZADOS", "SEM_DADOS_PENDENTE", "DADOS_INVALIDOS_PENDENTE", "CONCORRENCIA_PENDENTE", "REPROCESSAMENTO_PROIBIDO");
    }

    private static Stream<Arguments> casos(String regra, String feliz, String limite, String invalido, String conflito, String proibido) {
        return Stream.of(
                Arguments.of(regra + " caminho feliz", feliz),
                Arguments.of(regra + " valor limite", limite),
                Arguments.of(regra + " entrada inválida", invalido),
                Arguments.of(regra + " conflito", conflito),
                Arguments.of(regra + " estado proibido", proibido));
    }
}
