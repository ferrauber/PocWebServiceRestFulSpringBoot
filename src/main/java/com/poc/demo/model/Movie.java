package com.poc.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MOVIES")
public class Movie {

    @Id
    @Column(name = "ID_MOVIE", updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOVIE")
    @SequenceGenerator(sequenceName = "SEQ_MOVIE", name = "SEQ_MOVIE", allocationSize = 1)
    @JsonIgnore
    private Long idMovie;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "studios", length = 500, nullable = false)
    private String studio;

    @Column(name = "producers", length = 500, nullable = false)
    private String producer;

    @Column(name = "winner", columnDefinition = "number(1)", nullable = false)
    private Boolean isWinner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getIdMovie().equals(movie.getIdMovie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdMovie());
    }
}