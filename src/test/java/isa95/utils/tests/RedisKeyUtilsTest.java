package isa95.utils.tests;

import isa95.Utils.RedisKeyUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by user on 29/09/15.
 */
public class RedisKeyUtilsTest {

    @Test
    public void testGetRoot (){
        String key = "levelA:levelB:levelC";
        String root = RedisKeyUtils.getRoot(key);
        Assert.assertEquals("levelA", root);
    }

    @Test
    public void testGetRootOnlyOneLevel (){
        String key = "levelA";
        String root = RedisKeyUtils.getRoot(key);
        Assert.assertEquals("levelA", root);
    }

    @Test
    public void testGetRootOnlyNull (){
        String key = null;
        String root = RedisKeyUtils.getRoot(key);
        Assert.assertEquals(null, root);
    }

    @Test
    public void testPushLevel (){
        String key = "levelA:levelB";
        String keyAfterPush = RedisKeyUtils.pushLevel(key, "levelC");
        Assert.assertEquals("levelA:levelB:levelC", keyAfterPush);
    }

    @Test
    public void testPushLevels (){
        String key = "levelA:levelB";
        String keyAfterPush = RedisKeyUtils.pushLevels(key, "levelC", "levelD", "levelE");
        Assert.assertEquals("levelA:levelB:levelC:levelD:levelE", keyAfterPush);
    }

}
