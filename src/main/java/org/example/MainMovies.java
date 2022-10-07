package org.example;

import org.example.pojo.Movie;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MainMovies {
    public static void main(String[] args) throws IOException {
        // Creating list of movie obj.
        List<Movie> moviesData = Files
                .readAllLines(Paths.get("movies.csv"))
                .stream()
                .skip(1)
                .map(l -> l.split(","))
                .map(x -> Movie.builder()
                        .movieName(x[0])
                        .rating(new BigDecimal(x[1]))
                        .build())
                .toList();

        // List of movie names
        List<String> movieNames = moviesData
                .stream()
                .map(Movie::getMovieName)
                .toList();
        System.out.println(movieNames);

        // Sum of ratings
        Optional<BigDecimal> ratingSum = moviesData
                .stream()
                .map(Movie::getRating)
                .reduce(BigDecimal::add);

        // Dunno... ASK
        Optional<Object> avgRating = ratingSum
                .map(e -> e.divide(BigDecimal.valueOf(moviesData.size()), RoundingMode.HALF_UP));
        avgRating.ifPresent(System.out::println);

        Optional<Movie> bestMovie = moviesData
                .stream()
                .max(Comparator.comparing(Movie::getRating));
        bestMovie.ifPresent(System.out::println);

        Optional<Movie> worstMovie = moviesData
                .stream()
                .max(Comparator.comparing(Movie::getRating));
        worstMovie.ifPresent(System.out::println);
    }
}

