package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainMovies {

    public static Map<String, BigDecimal> sortByValue(HashMap<String, BigDecimal> hm) {
        return hm.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static void main(String[] args) throws IOException {
        // CSV content - List
        List<String> moviesData = Files
                .readAllLines(Paths.get("movies.csv"))
                .stream()
                .skip(1)
                .toList();

        // List of movies
        List<String> movies = moviesData
                .stream()
                .map(l -> l.split(",")[0])
                .toList();
        System.out.println(movies);

        // List of ratings => BigDecimal
        List<BigDecimal> ratings = moviesData
                .stream()
                .map(l -> l.split(",")[1])
                .map(BigDecimal::new)
                .toList();

        // Calculating average rating
        Optional<BigDecimal> ratingSum = ratings
                .stream()
                .reduce(BigDecimal::add)
                .map(e -> e.divide(BigDecimal.valueOf(ratings.size()), RoundingMode.HALF_UP));
        ratingSum.ifPresent(System.out::println);

        // Creating HashMap from created lists key = movie name, value = movie rating
        HashMap<String, BigDecimal> moviesDataMap = IntStream.range(0, movies.size())
                .collect(HashMap::new,
                        (m, i) -> m.put(movies.get(i), ratings.get(i)),
                        Map::putAll
                );

        // Sorting by value --> rating
        Map<String, BigDecimal> sortedMoviesByRating = sortByValue(moviesDataMap);

        // Creating list of sorted movie names
        List<String> sortedMovieNames = new ArrayList<>(sortedMoviesByRating.keySet());

        System.out.println("Best Movie: " + sortedMovieNames.get(sortedMovieNames.size() - 1));

        System.out.println("Worst Movie: " + sortedMovieNames.get(0));
    }
}

