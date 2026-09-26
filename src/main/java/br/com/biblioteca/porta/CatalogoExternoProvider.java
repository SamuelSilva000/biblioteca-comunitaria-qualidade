package br.com.biblioteca.porta;

public interface CatalogoExternoProvider {

    MetadadosLivroExterno buscarPorIsbn(String isbn);
}