package com.poc.demo.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class MovieDto {

    private Integer year;
    private String title;
    private String studios;
    private String producers;
    private Boolean winner;

    public Boolean getWinner() {
        return (Objects.nonNull(winner)) ? winner : Boolean.FALSE;
    }
}
