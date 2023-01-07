package org.rajman.common.scores.service;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    private final String SCORE_MAP_NAME = "score-player_score";
    private final String SCORE_FILL_SCORE_CACHE_QUEUE_NAME = "score.fill_score_cache";
    private final RedissonClient redissonClient;
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    public ScoreService(RedissonClient redissonClient, RabbitTemplate rabbitTemplate) {
        this.redissonClient = redissonClient;
        this.rabbitTemplate=rabbitTemplate;
    }

    public Object getScore(Integer userId) {
        try {

            RMap<Integer, Object> map = this.redissonClient.getMap(SCORE_MAP_NAME);
            if (map.isEmpty() || !map.containsKey(userId)) {
                rabbitTemplate.convertAndSend(SCORE_FILL_SCORE_CACHE_QUEUE_NAME,userId);
                return null;
            } else {
                return map.get(userId);
            }
        } catch (AmqpException ex){
            System.out.println("not connect to redisson ");
        }
        catch (Exception ex) {
            System.out.println("not connect to redisson ");
        }
        return -1;
    }
}
