package org.rajman.common.scores.service;

import org.rajman.ScoreCacheDTO;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ScoreService {
    private final static String SCORE_MAP_NAME = "score-player_score";
    private final static String SCORE_FILL_SCORE_CACHE_QUEUE_NAME = "score.fill_score_cache";
    private RMap<Integer, ScoreCacheDTO> map;
    private final RedissonClient redissonClient;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ScoreService(RedissonClient redissonClient, RabbitTemplate rabbitTemplate) {
        this.redissonClient = redissonClient;
        this.rabbitTemplate = rabbitTemplate;
    }


    @PostConstruct
    private void postConstruct() {
        map = this.redissonClient.getMap(SCORE_MAP_NAME);
    }

    public ScoreCacheDTO getScore(Integer userId) {
        if (map.isEmpty() || !map.containsKey(userId)) {
            rabbitTemplate.convertAndSend(SCORE_FILL_SCORE_CACHE_QUEUE_NAME, userId);
            return null;
        }
        return map.get(userId);
    }
}
