package br.com.alura.ScreenMatch2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDado(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe); //ver o valor json com a classe para tentar converter
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
