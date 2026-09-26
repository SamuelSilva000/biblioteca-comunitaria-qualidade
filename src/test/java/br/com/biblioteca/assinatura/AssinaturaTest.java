package br.com.biblioteca.assinatura;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import br.com.biblioteca.assinatura.Assinatura.Plano;

class AssinaturaTest {

    private static final Instant AGORA = Instant.parse("2026-10-05T12:00:00Z");
    private static final Clock RELOGIO = Clock.fixed(AGORA, ZoneOffset.UTC);

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
        Assinatura assinatura = new Assinatura(Plano.PREMIUM, AGORA, false);

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
}