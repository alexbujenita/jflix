package org.truje.jflix.integration.tmdb;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import org.truje.jflix.integration.tmdb.model.MovieDiscoveryResponse;

@Repository
public class TmdbClient {

    private static final String DISCOVER_PATH = "/discover/movie";

    private final RestClient client;

    public TmdbClient(
            RestClient.Builder builder,
            @Value("${tmdb.base-url}") String tmdbApiUrl,
            @Value("${tmdb.api-key}") String tmdbApiKey) {

        this.client = builder.baseUrl(tmdbApiUrl)
                .defaultHeaders(headers -> {
                    headers.setBearerAuth(tmdbApiKey);
                    headers.setAccept(List.of(APPLICATION_JSON));
                })
                .build();
    }

    public MovieDiscoveryResponse discoverMovies() {
        return client.get().uri(DISCOVER_PATH).retrieve().body(MovieDiscoveryResponse.class);
    }
}
