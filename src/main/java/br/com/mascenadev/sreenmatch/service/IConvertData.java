package br.com.mascenadev.sreenmatch.service;

public interface IConvertData {

    <T> T convertData(String json, Class<T> clazz);
}
