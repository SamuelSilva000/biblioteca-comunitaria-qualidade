package br.com.biblioteca;

import br.com.biblioteca.porta.CatalogoExternoProvider;
import br.com.biblioteca.porta.MetadadosLivroExterno;

public class LivroService {

    private final CatalogoExternoProvider catalogoExternoProvider;

    public LivroService(CatalogoExternoProvider catalogoExternoProvider) {
        this.catalogoExternoProvider = catalogoExternoProvider;
    }

    public MetadadosLivroExterno buscarMetadados(String isbn) {
        return catalogoExternoProvider.buscarPorIsbn(isbn);
    }
}