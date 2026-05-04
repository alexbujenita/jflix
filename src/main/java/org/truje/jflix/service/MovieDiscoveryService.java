package org.truje.jflix.service;

import org.springframework.stereotype.Service;
import org.truje.jflix.integration.tmdb.TmdbClient;
import org.truje.jflix.integration.tmdb.model.MovieDiscoveryResponse;

@Service
public class MovieDiscoveryService {

    private final TmdbClient tmdbClient;

    public MovieDiscoveryService(TmdbClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public MovieDiscoveryResponse discoverMovies() {
        return tmdbClient.discoverMovies();
    }
}
