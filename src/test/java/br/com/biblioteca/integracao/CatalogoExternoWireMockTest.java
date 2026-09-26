package br.com.biblioteca.integracao;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

import br.com.biblioteca.adapter.HttpCatalogoExternoProvider;
import br.com.biblioteca.adapter.ProvedorIndisponivelException;
import br.com.biblioteca.porta.MetadadosLivroExterno;

class CatalogoExternoWireMockTest {

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();

    private HttpCatalogoExternoProvider provider;

    @BeforeEach
    void configurarProvider() {
        provider = new HttpCatalogoExternoProvider(wireMock.baseUrl(), "token-secreto-de-teste");
    }

    @Test
    void INT01_200JsonValido_RetornaMetadadosMapeados() {
        wireMock.stubFor(get(urlEqualTo("/books/9780000000001"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"isbn\":\"9780000000001\",\"titulo\":\"Dom Casmurro\","
                                + "\"autor\":\"Machado de Assis\",\"editora\":\"Exemplo\"}")));

        MetadadosLivroExterno metadados = provider.buscarPorIsbn("9780000000001");

        assertEquals(new MetadadosLivroExterno("9780000000001", "Dom Casmurro", "Machado de Assis", "Exemplo"),
                metadados);
    }

    @Test
    void INT02_404_NaoExpoeDetalhesDoProvedor() {
        wireMock.stubFor(get(urlEqualTo("/books/9780000000002"))
                .willReturn(aResponse().withStatus(404).withBody("detalhe interno do provedor")));

        ProvedorIndisponivelException excecao = assertThrows(ProvedorIndisponivelException.class,
                () -> provider.buscarPorIsbn("9780000000002"));

        assertEquals("Livro nao encontrado", excecao.getMessage());
        assertFalse(excecao.getMessage().contains("detalhe interno"));
    }

    @Test
    void INT03_500_RetornaErroControlado() {
        wireMock.stubFor(get(urlEqualTo("/books/9780000000003"))
                .willReturn(aResponse().withStatus(500).withBody("falha interna detalhada")));

        ProvedorIndisponivelException excecao = assertThrows(ProvedorIndisponivelException.class,
                () -> provider.buscarPorIsbn("9780000000003"));

        assertEquals("Provedor com falha", excecao.getMessage());
    }

    @Test
    void INT04_AtrasoMaiorQueTimeout_InterrompeRapido() {
        wireMock.stubFor(get(urlEqualTo("/books/9780000000004"))
                .willReturn(aResponse().withStatus(200).withFixedDelay(5000)
                        .withBody("{\"isbn\":\"9780000000004\"}")));

        assertTimeoutPreemptively(Duration.ofSeconds(4), () -> assertThrows(ProvedorIndisponivelException.class,
                () -> provider.buscarPorIsbn("9780000000004")));
    }

    @Test
    void INT05_200JsonMalformado_ErroControlado() {
        wireMock.stubFor(get(urlEqualTo("/books/9780000000005"))
                .willReturn(aResponse().withStatus(200).withBody("{json malformado")));

        ProvedorIndisponivelException excecao = assertThrows(ProvedorIndisponivelException.class,
                () -> provider.buscarPorIsbn("9780000000005"));

        assertEquals("Falha de comunicacao com o provedor", excecao.getMessage());
    }

    @Test
    void INT06_RequestRecebido_HeaderObrigatorioPresente() {
        wireMock.stubFor(get(urlEqualTo("/books/9780000000006"))
                .willReturn(aResponse().withStatus(200).withBody("{\"isbn\":\"9780000000006\"}")));

        provider.buscarPorIsbn("9780000000006");

        wireMock.verify(getRequestedFor(urlEqualTo("/books/9780000000006"))
                .withHeader("Authorization", equalTo("Bearer token-secreto-de-teste"))
                .withHeader("Accept", equalTo("application/json")));
    }
}