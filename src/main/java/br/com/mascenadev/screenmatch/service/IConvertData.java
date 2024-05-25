package br.com.mascenadev.screenmatch.service;

public interface IConvertData {

    <T> T convertData(String json, Class<T> clazz);
}
