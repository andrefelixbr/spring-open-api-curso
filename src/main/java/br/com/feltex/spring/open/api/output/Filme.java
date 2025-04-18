package br.com.feltex.spring.open.api.output;

public record Filme (int ano, String titulo,
                     String genero, String diretor, String atores, String tituloEmIngles) {
    }
