package isa95.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by user on 9/28/15.
 */
public class VersioningService {

    @Autowired
    private RedisTemplate redisTemplate;

    public String createNextVersionedKey (String baseKey){
        Object version = redisTemplate.opsForValue().increment(baseKey.concat(":currentVersion"), 1);
        return VersioningService.appendVersionToKey(baseKey, version.toString());
    }

    /**return null if no versioned keys exists*/
    public String getCurrentVersionedKey (String baseKey){
        Object currentVersion = redisTemplate.opsForValue().get(baseKey.concat(":currentVersion"));
        return (currentVersion != null)?VersioningService.appendVersionToKey(baseKey, currentVersion.toString()):null;
    }

    private static String appendVersionToKey (String baseKey, String version){
        return baseKey.concat(":version:").concat(version);
    }


}
