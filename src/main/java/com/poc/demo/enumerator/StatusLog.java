package com.poc.demo.enumerator;

public enum StatusLog {
    RUNNING("Processando"),
    FAILED("Falha"),
    SUCCESS("Sucesso!!!");

    public String description;

    StatusLog(String description) {
        this.description = description;
    }
}