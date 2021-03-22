package com.poc.demo.repository;

import com.poc.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.isWinner = 1 ORDER BY m.producer")
    List<Movie> findByWinner();
}