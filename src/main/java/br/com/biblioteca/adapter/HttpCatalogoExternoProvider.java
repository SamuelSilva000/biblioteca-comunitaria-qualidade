package br.com.biblioteca.adapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.biblioteca.porta.CatalogoExternoProvider;
import br.com.biblioteca.porta.MetadadosLivroExterno;

public class HttpCatalogoExternoProvider implements CatalogoExternoProvider {

    private final String baseUrl;
    private final String token;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpCatalogoExternoProvider(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public MetadadosLivroExterno buscarPorIsbn(String isbn) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/books/" + isbn))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(3))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return interpretarResposta(response);
        } catch (IOException | InterruptedException causa) {
            if (causa instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new ProvedorIndisponivelException("Falha de comunicacao com o provedor", causa);
        }
    }

    private MetadadosLivroExterno interpretarResposta(HttpResponse<String> response) {
        int status = response.statusCode();
        if (status == 200) {
            try {
                return objectMapper.readValue(response.body(), MetadadosLivroExterno.class);
            } catch (IOException causa) {
                throw new ProvedorIndisponivelException("Falha de comunicacao com o provedor", causa);
            }
        }
        if (status == 404) {
            throw new ProvedorIndisponivelException("Livro nao encontrado");
        }
        if (status >= 500 && status <= 599) {
            throw new ProvedorIndisponivelException("Provedor com falha");
        }
        throw new ProvedorIndisponivelException("Resposta inesperada");
    }
}