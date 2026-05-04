package org.truje.jflix.integration.tmdb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MovieDiscoveryResponse(
	Integer page,
	List<MovieDiscoveryResult> results,
	Integer totalPages,
	Integer totalResults) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record MovieDiscoveryResult(
	    Boolean adult,
	    String backdropPath,
	    List<Integer> genreIds,
	    Long id,
	    String title,
	    String originalLanguage,
	    String originalTitle,
	    String overview,
	    Double popularity,
	    String posterPath,
	    String releaseDate,
	    Boolean softcore,
	    Boolean video,
	    Double voteAverage,
	    Integer voteCount) {}
}
