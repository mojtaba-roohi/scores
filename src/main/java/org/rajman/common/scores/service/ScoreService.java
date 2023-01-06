package org.rajman.common.scores.service;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    private RedissonClient redissonClient;

    private final String mapKey = "score";

    @Autowired
    public ScoreService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public Integer getScore(String field) {
        try {

            RMap<String, Integer> map = this.redissonClient.getMap(mapKey);
            if (map.isEmpty() || !map.containsKey(field)) {
                return -1;
            } else {
                return map.get(field);
            }
        } catch (Exception ex) {
            System.out.println("not connect to redisson ");
        }
        return -1;
    }
}
