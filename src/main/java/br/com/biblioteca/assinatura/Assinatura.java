package br.com.biblioteca.assinatura;

import java.time.Clock;
import java.time.Instant;

public class Assinatura {

    public enum Plano {
        FREE,
        PREMIUM
    }

    private final Plano plano;
    private final Instant vencimento;
    private final boolean cancelada;

    public Assinatura(Plano plano, Instant vencimento, boolean cancelada) {
        this.plano = plano;
        this.vencimento = vencimento;
        this.cancelada = cancelada;
    }

    public boolean podeAcessarExclusivo(Clock relogio) {
        if (plano != Plano.PREMIUM || cancelada || vencimento == null) {
            return false;
        }

        return relogio.instant().isBefore(vencimento);
    }
}