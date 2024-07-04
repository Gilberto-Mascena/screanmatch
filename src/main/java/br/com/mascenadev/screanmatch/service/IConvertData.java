package br.com.mascenadev.screanmatch.service;

public interface IConvertData {

    <T> T getData(String json, Class<T> clazz);
}