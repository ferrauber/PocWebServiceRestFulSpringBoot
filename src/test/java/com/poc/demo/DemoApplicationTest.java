package com.poc.demo;

import com.poc.demo.model.Movie;
import com.poc.demo.service.MovieService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(value = SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DemoApplicationTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void integrationTest() {

        Movie movieBuildInsert = mapperMovie();
        Movie movieBuildUpdt = mapperMovieUpd();

        Movie movieInsert = movieService.insert(movieBuildInsert);
        Assert.assertEquals(movieBuildInsert.getTitle(), movieInsert.getTitle());

        Optional<Movie> movieConsulta = movieService.getById(movieInsert.getIdMovie());
        Assert.assertNotNull(movieConsulta);
        Assert.assertEquals(movieInsert.getTitle(), movieConsulta.get().getTitle());

        Movie movieUpd = movieService.update(movieInsert.getIdMovie(), movieBuildUpdt);
        Assert.assertEquals(movieBuildUpdt.getTitle(), movieUpd.getTitle());

        Boolean isDelete = movieService.deleteById(movieInsert.getIdMovie());
        Assert.assertTrue(isDelete);

        Optional<Movie> movieVerify = movieService.getById(movieInsert.getIdMovie());
        Assert.assertTrue(movieVerify.isEmpty());
    }

    private Movie mapperMovie() {
        return Movie.builder()
                .year(1980)
                .title("Rambo IV")
                .studio("Lionsgate")
                .producer("Kevin")
                .isWinner(true)
                .build();
    }

    private Movie mapperMovieUpd() {
        return Movie.builder()
                .year(1980)
                .title("Rambo V")
                .studio("Lionsgate")
                .producer("Kevin")
                .isWinner(true)
                .build();
    }
}