package isa95.service;

import isa95.Utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Created by user on 29/09/15.
 */
@Component
public class CommonRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**try to remove all entries with key formatted as "baseKey:incr" where incr>=0 while key exists*/
    public int removeIncrKeys (String baseKey){
        boolean exists = true;
        int countDel = 0;
        while (exists) {
            String key = RedisKeyUtils.pushLevel(baseKey, countDel);
            exists = redisTemplate.hasKey(key);
            if (exists){
                redisTemplate.delete(key);
                countDel++;
            }
        }
        return  countDel;
    }

    /**try to remove from Sorted Set with key "setKey" all keys formatted as "baseKey:incr" where incr>=0*/
    public int removeIncrKeysFromSortedSet (String setKey, String baseKey){
        long responseDel = 1;
        int countDel = 0;
        while (responseDel > 0) {
            String keyIndex = RedisKeyUtils.pushLevel(baseKey, countDel++);
            responseDel = redisTemplate.opsForSet().remove(setKey, keyIndex);
        }
        return countDel;
    }

    /**add to Sorted Set with key "setKey" a new item for each "score" with key "baseKey:scores.indexOf(score)"*/
    public int addIncrKeysInSortedSet (String setKey, String baseKey, Collection<? extends Number> scores){
        int countIns = 0;
        for (Number s : scores) {
            Double idxScoreValue = s.doubleValue();
            String idxItemKey = RedisKeyUtils.pushLevel(baseKey, countIns++);
            redisTemplate.opsForZSet().add(setKey, idxItemKey, idxScoreValue);
        }
        return countIns;
    }

}

