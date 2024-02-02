package br.com.alura.ScreenMatch2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//Converter dados da API após leitura deles pelo Record
public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();//Como se fosse a atribuição toJson() ->Gson

    @Override
    public <T> T obterDado(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe); //tenta ler e retorna para um classe que ele passou
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
