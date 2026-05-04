package org.truje.jflix.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.truje.jflix.integration.tmdb.model.MovieDiscoveryResponse;
import org.truje.jflix.service.MovieDiscoveryService;

@RestController
@RequestMapping("public/api/v1/discover")
public class DiscoverController {

    private final MovieDiscoveryService discoveryService;

    public DiscoverController(MovieDiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    @GetMapping("/movies")
    public ResponseEntity<MovieDiscoveryResponse> movies() {
        return ResponseEntity.ok(discoveryService.discoverMovies());
    }
}
