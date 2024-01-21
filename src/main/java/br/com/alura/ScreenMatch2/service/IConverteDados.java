package br.com.alura.ScreenMatch2.service;

public interface IConverteDados {
    <T> T obterDado(String json, Class<T> classe); //devolve algum generico (<T> T)
}
