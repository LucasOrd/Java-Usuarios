package com.coderhouse.cache;


public interface CacheUser <T>{
    T save(String key, T data);
    T recover(String key, Class<T> classValue);
    void delete(String id);
}
