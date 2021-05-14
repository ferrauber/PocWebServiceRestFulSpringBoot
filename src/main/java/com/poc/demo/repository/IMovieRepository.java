package com.poc.demo.repository;

import com.poc.demo.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.isWinner = 1 ORDER BY m.year")
    List<Movie> findByWinner();

    @Query("SELECT m FROM Movie m WHERE m.isWinner = 1 AND m.year >= :startYear AND m.year <= :finishYear ORDER BY m.year")
    Page<Movie> findByPeriod(final Integer startYear, final Integer finishYear, Pageable pageable);
}