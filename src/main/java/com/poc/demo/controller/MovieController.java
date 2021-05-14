package com.poc.demo.controller;

import com.poc.demo.dto.ProducerWinnerMovieDto;
import com.poc.demo.enumerator.StatusLog;
import com.poc.demo.model.Movie;
import com.poc.demo.service.MovieService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
public class MovieController {

    private final MovieService movieService;

    @PostMapping(value = "/importar")
    @ApiOperation(value = "Executar integração entre arquivo .csv com base de dados")
    @Transactional(readOnly = true)
    public ResponseEntity<Void> importCSVMovieFile() {
        movieService.importCSVMovieFile();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/consultar")
    @ApiOperation(value = "Obter consulta geral de registros da base de dados", response = Movie.class, responseContainer = "List")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Movie>> getAll() {
        List<Movie> lstMovies = movieService.getAll();
        return new ResponseEntity<>(lstMovies, HttpStatus.OK);
    }

    @GetMapping(value = "/consultar/{id}")
    @ApiOperation(value = "Obter consulta de registro na base de dados", response = Movie.class/*, responseContainer = "Object"*/)
    @Transactional(readOnly = true)
    public ResponseEntity<Optional<Movie>> getById(@PathVariable final Long id) {
        Optional<Movie> movie = movieService.getById(id);

        if (movie.isPresent())
            return new ResponseEntity<>(movie, HttpStatus.OK);
        else
            return new ResponseEntity<>(movie, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/consultar/periodo")
    @ApiOperation(value = "Obter consulta geral de registros em um período de tempo", response = Movie.class, responseContainer = "List")
    @Transactional(readOnly = true)
    public Page<Movie> getAllPeriod(@RequestParam("startDate") final Integer startYear,
                                    @RequestParam("FinishDate") final Integer finishYear,
                                    @RequestParam("pageNumber") final Integer pageNumber,
                                    @RequestParam("pageSize") final Integer pageSize) {
        Pageable pageable = PageRequest.of((pageNumber > 0) ? pageNumber - 1 : pageNumber, pageSize, Sort.by(Sort.Order.asc("year")));
        return movieService.getAllPeriod(startYear, finishYear, pageable);
    }

    @PostMapping(value = "/inserir")
    @ApiOperation(value = "Executar a inserção de registro na base de dados")
    public ResponseEntity<Movie> insert(@RequestBody(required = true) final Movie movie) {
        Movie movieInserted = movieService.insert(movie);
        return new ResponseEntity<>(movieInserted, HttpStatus.CREATED);
    }

    @PutMapping(value = "/atualizar/{id}")
    @ApiOperation(value = "Executar a atualização do registro na base de dados")
    public ResponseEntity<Movie> update(@PathVariable final Long id, @RequestBody(required = true) final Movie updMovie) {
        Movie movie = movieService.update(id, updMovie);

        if (Optional.ofNullable(movie).isPresent())
            return new ResponseEntity<>(movie, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Executar a exclusão do registro da base de dados")
    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final Long id) {
        Boolean isDeletedSuccess = movieService.deleteById(id);

        if (isDeletedSuccess) {
            log.info("method={}, status={}", "deleteById", StatusLog.SUCCESS);
            return ResponseEntity.ok().build();
        } else {
            log.warn("method={}, status={}", "deleteById", StatusLog.FAILED);
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Executar a exclusão completa dos registros na base de dados")
    @DeleteMapping(value = "/deletar")
    public ResponseEntity<Void> deleteAll() {
        Boolean isDeletedSuccess = movieService.deleteAll();

        if (isDeletedSuccess) {
            log.info("method={}, status={}", "deleteAll", StatusLog.SUCCESS);
            return ResponseEntity.ok().build();
        } else {
            log.warn("method={}, status={}", "deleteAll", StatusLog.FAILED);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/consultar-premios")
    @ApiOperation(value = "Obter intervalo de prêmios", response = ProducerWinnerMovieDto.class, responseContainer = "ProducerWinnerMovieDto")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProducerWinnerMovieDto>> getWinnerMoviesProductor() {
        List<ProducerWinnerMovieDto> lstMovies = movieService.getWinnerMoviesProductor();
        return new ResponseEntity<>(lstMovies, HttpStatus.OK);
    }
}