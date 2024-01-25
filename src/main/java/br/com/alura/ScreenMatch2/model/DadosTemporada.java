package br.com.alura.ScreenMatch2.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") Integer numero,@JsonAlias("Episodes") List<DadosEpisodio> episodios) {
} //Busca o numero de temporadas e seus respcetivos episódios
