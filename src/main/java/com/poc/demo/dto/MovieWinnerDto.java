package com.poc.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieWinnerDto {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}