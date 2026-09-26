package br.com.biblioteca.assinatura;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import br.com.biblioteca.assinatura.Assinatura.Plano;

class AssinaturaTest {

    private static final String INSTANTE_REFERENCIA = "2026-10-05T12:00:00Z";
    private static final Clock RELOGIO = relogioEm(INSTANTE_REFERENCIA);

    @Test
    void premiumVigenteConcedeAcessoExclusivo() {
        Assinatura assinatura = new Assinatura(Plano.PREMIUM, Instant.parse("2026-10-05T12:00:01Z"), false);

        assertEquals(true, assinatura.podeAcessarExclusivo(RELOGIO));
    }

    @Test
    void premiumVencidaNaoConcedeAcesso() {
        Assinatura assinatura = new Assinatura(Plano.PREMIUM, Instant.parse("2026-10-05T11:59:59Z"), false);

        assertEquals(false, assinatura.podeAcessarExclusivo(RELOGIO));
    }

    @Test
    void instanteExatoDoVencimentoNaoConcedeAcesso() {
        Assinatura assinatura = new Assinatura(Plano.PREMIUM, Instant.parse(INSTANTE_REFERENCIA), false);

        assertEquals(false, assinatura.podeAcessarExclusivo(RELOGIO));
    }

    @Test
    void planoGratuitoNaoConcedeAcesso() {
        Assinatura assinatura = new Assinatura(Plano.FREE, null, false);

        assertEquals(false, assinatura.podeAcessarExclusivo(RELOGIO));
    }

    @Test
    void assinaturaCanceladaNaoConcedeAcesso() {
        Assinatura assinatura = new Assinatura(Plano.PREMIUM, Instant.parse("2026-10-05T12:30:00Z"), true);

        assertEquals(false, assinatura.podeAcessarExclusivo(RELOGIO));
    }

    @Test
    void premiumVencimentoUmSegundoDepoisDoAgoraConcedeAcesso() {
        Clock relogio = relogioEm(INSTANTE_REFERENCIA);
        Assinatura assinatura = new Assinatura(Plano.PREMIUM, Instant.parse("2026-10-05T12:00:01Z"), false);

        assertEquals(true, assinatura.podeAcessarExclusivo(relogio));
    }

    @Test
    void premiumVencimentoUmSegundoAntesDoAgoraNaoConcedeAcesso() {
        Clock relogio = relogioEm(INSTANTE_REFERENCIA);
        Assinatura assinatura = new Assinatura(Plano.PREMIUM, Instant.parse("2026-10-05T11:59:59Z"), false);

        assertEquals(false, assinatura.podeAcessarExclusivo(relogio));
    }

    @Test
    void planoPremiumSemVencimentoNaoConcedeAcesso() {
        Clock relogio = relogioEm(INSTANTE_REFERENCIA);
        Assinatura assinatura = new Assinatura(Plano.PREMIUM, null, false);

        assertEquals(false, assinatura.podeAcessarExclusivo(relogio));
    }

    private static Clock relogioEm(String instanteIso) {
        return Clock.fixed(Instant.parse(instanteIso), ZoneOffset.UTC);
    }
}