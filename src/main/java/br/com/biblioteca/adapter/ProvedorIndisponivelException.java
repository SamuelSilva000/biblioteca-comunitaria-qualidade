package br.com.biblioteca.adapter;

public class ProvedorIndisponivelException extends RuntimeException {

    public ProvedorIndisponivelException(String mensagem) {
        super(mensagem);
    }

    public ProvedorIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}