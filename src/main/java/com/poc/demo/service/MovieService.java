package com.poc.demo.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.poc.demo.dto.MovieDto;
import com.poc.demo.dto.MovieWinnerDto;
import com.poc.demo.dto.ProducerWinnerMovieDto;
import com.poc.demo.enumerator.StatusLog;
import com.poc.demo.model.Movie;
import com.poc.demo.repository.IMovieRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class MovieService {

    private final IMovieRepository movieRepository;

    public void importCSVMovieFile() {
        log.info("method={}, status={}", "readCSVFile", StatusLog.RUNNING);

        if (getAll().size() > 0)
            deleteAll();

        try {
            List<MovieDto> lstMoviesDto = readCSVFile();

            List<Movie> lstMovies = new ArrayList<>();
            for (MovieDto movieDto : lstMoviesDto)
                lstMovies.add(Movie.builder()
                        .year(movieDto.getYear())
                        .title(movieDto.getTitle())
                        .studio(movieDto.getStudios())
                        .producer(movieDto.getProducers())
                        .isWinner(movieDto.getWinner()).build());

            insertAll(lstMovies);

        } catch (IOException | URISyntaxException e) {
            log.info("method={}, status={}, info={}", "importCSVMovieFile", StatusLog.FAILED, "Problemas na leitura do arquivo .csv!!!");
            e.printStackTrace();
        }
    }

    /**
     * Efetua a leitura do arquivo .csv e retorna uma lista de filmes
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    private List<MovieDto> readCSVFile() throws IOException, URISyntaxException {

        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("static/movielist.csv").toURI()));

        CsvToBean<MovieDto> csvToBean = new CsvToBeanBuilder(reader)
                .withSeparator(';')
                .withType(MovieDto.class)
                .build();

        return csvToBean.parse();
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie insert(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> insertAll(List<Movie> lstMovies) {
        log.info("method={}, status={}, info={}", "insertAllMovies", StatusLog.RUNNING, lstMovies);
        List<Movie> lstMovieSaved = movieRepository.saveAll(lstMovies);
        movieRepository.flush();
        return lstMovieSaved;
    }

    public Movie update(Long id, Movie updtMovie) {
        return getById(id)
                .map(movie -> {
                    movie.setYear(updtMovie.getYear());
                    movie.setTitle(updtMovie.getTitle());
                    movie.setStudio(updtMovie.getStudio());
                    movie.setProducer(updtMovie.getProducer());
                    movie.setIsWinner(updtMovie.getIsWinner());
                    return movieRepository.save(movie);
                }).orElse(null);
    }

    public Boolean deleteById(Long id) {
        try {
            movieRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (NoSuchElementException nsee) {
            return Boolean.FALSE;
        } catch (EmptyResultDataAccessException ersae) {
            return Boolean.FALSE;
        }
    }

    public Boolean deleteAll() {
        try {
            movieRepository.deleteAll();
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    /**
     * Obtêm a lista de produtores que ganharam prêmios com seus filmes
     * e seus respectivos intervalos consecutivos
     *
     * @return
     */
    public List<ProducerWinnerMovieDto> getWinnerMoviesProductor() {
        List<MovieWinnerDto> lstProducersWinMoreThanOneTime = getWinnerProductorMoreThanOneTime(movieRepository.findByWinner());

        List<ProducerWinnerMovieDto> lstMovieAndProductorAward = new ArrayList<>() {{
            add(ProducerWinnerMovieDto.builder()
                    .min(getMoviesWithIntervalMin(lstProducersWinMoreThanOneTime))
                    .max(getMoviesWithIntervalMax(lstProducersWinMoreThanOneTime))
                    .build());
        }};

        return lstMovieAndProductorAward;
    }

    private List<MovieWinnerDto> getWinnerProductorMoreThanOneTime(List<Movie> lstWinnerMovies) {
        List<MovieWinnerDto> lstOnlyMoviesWin = new ArrayList<>();

        for (Movie winnerMovies : lstWinnerMovies) {

            List<Movie> lstMoviesWinMoreThanOneTime = lstWinnerMovies.stream()
                    .filter((movie -> movie.getProducer().contains(winnerMovies.getProducer())))
                    .collect(Collectors.toList());

            int qtdMoviesProducerWin = lstWinnerMovies.stream()
                    .filter((movie -> movie.getProducer().contains(winnerMovies.getProducer())))
                    .collect(Collectors.toList()).size();

            if (qtdMoviesProducerWin > 1) {
                String nameFirst = lstMoviesWinMoreThanOneTime.get(0).getProducer();
                String nameSec = lstMoviesWinMoreThanOneTime.get(1).getProducer();
                String name = (nameFirst.length() < nameSec.length()) ? nameFirst : nameSec;
                lstOnlyMoviesWin.add(mapperWinnerDto(name, lstMoviesWinMoreThanOneTime.get(0).getYear(), lstMoviesWinMoreThanOneTime.get(1).getYear()));
            }
        }

        return lstOnlyMoviesWin.stream().distinct().collect(Collectors.toList());
    }

    private List<MovieWinnerDto> getMoviesWithIntervalMin(List<MovieWinnerDto> lstProducersWinMoreThanOneTime) {
        return lstProducersWinMoreThanOneTime.stream()
                .filter(winnerMovie -> winnerMovie.getInterval().equals(getIntevalMin(lstProducersWinMoreThanOneTime)))
                .collect(Collectors.toList());
    }

    private List<MovieWinnerDto> getMoviesWithIntervalMax(List<MovieWinnerDto> lstProducersWinMoreThanOneTime) {
        return lstProducersWinMoreThanOneTime.stream()
                .filter(winnerMovie -> winnerMovie.getInterval().equals(getIntevalMax(lstProducersWinMoreThanOneTime)))
                .collect(Collectors.toList());
    }

    private MovieWinnerDto mapperWinnerDto(String producer, Integer previousWin, Integer followingWin) {
        return MovieWinnerDto.builder()
                .producer(producer)
                .interval(followingWin - previousWin)
                .previousWin(previousWin)
                .followingWin(followingWin)
                .build();
    }

    private int getIntevalMin(List<MovieWinnerDto> lstProducersWinMoreThanOneTime) {
        return lstProducersWinMoreThanOneTime.stream()
                .mapToInt(MovieWinnerDto::getInterval)
                .min().getAsInt();
    }

    private int getIntevalMax(List<MovieWinnerDto> lstProducersWinMoreThanOneTime) {
        return lstProducersWinMoreThanOneTime.stream()
                .mapToInt(MovieWinnerDto::getInterval)
                .max().getAsInt();
    }
}