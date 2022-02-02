package com.coderhouse.cache;

import com.coderhouse.config.AplicationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheUserImpl <T> implements CacheUser<T>{

    private final RedisTemplate<String, T> redisTemplate;
    private final AplicationProperties properties;
    private HashOperations<String, String, String> hashOperations;
    private final ObjectMapper mapper;

    @PostConstruct
    void setHashOperations() {
        hashOperations = this.redisTemplate.opsForHash();
        this.redisTemplate.expire("mensaje-map", Duration.ofMillis(properties.getTimeOfLife()));
    }

    @Override
    public T save(String key, T data) {
        try {
            hashOperations.put("user-map", key, serializeItem(data));
            return data;
        } catch (JsonProcessingException e) {
            log.error("Error converting user to string", e);
        }
        return data;
    }

    @Override
    public T recover(String key, Class<T> classValue) {
        try {
            var item = hashOperations.get("user-map", key);
            if (item == null) return null;
            return deserializeItem(item, classValue);
        } catch (JsonProcessingException e) {
            log.error("Error converting user to Restaurante", e);
        }
        return null;
    }


    @Override
    public void delete(String id){
        hashOperations.delete("user-map",id);
    }

    private String serializeItem(T item) throws JsonProcessingException {
        var serializeItem = mapper.writeValueAsString(item);
        log.info("User en formato String: {}", serializeItem);
        return serializeItem;
    }

    private T deserializeItem(String jsonInput, Class<T> classValue) throws JsonProcessingException {
        return mapper.readValue(jsonInput, classValue);
    }
}
