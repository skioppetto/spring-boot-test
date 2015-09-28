package isa95.service.tests;

import isa95.service.VersioningService;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.lang.reflect.Field;

/**
 * Created by user on 9/28/15.
 */
public class VersioningTest extends EasyMockSupport {

    private RedisTemplate redisCollaborator;
    private ValueOperations opsMock;
    private final VersioningService serviceUnderTest = new VersioningService();

    @Before
    public void mockOpsForValueBefore () throws NoSuchFieldException, IllegalAccessException {
        opsMock = mock(ValueOperations.class);
        redisCollaborator = mock(RedisTemplate.class);
        EasyMock.expect(redisCollaborator.opsForValue()).andStubReturn(opsMock);

        Field field = serviceUnderTest.getClass().getDeclaredField("redisTemplate");
        field.setAccessible(true);
        field.set(serviceUnderTest, redisCollaborator);
    }

    @Test
    public void testCreateNextVersionedKeyFirstCreated (){
        EasyMock.expect(opsMock.increment("basekey:currentVersion", 1))
        .andReturn(0l);
        replayAll();
        String key = serviceUnderTest.createNextVersionedKey("basekey");
        Assert.assertEquals("basekey:version:0", key);
        verifyAll();
    }

    @Test
    public void testGetCurrentVersionedKey (){
        EasyMock.expect(opsMock.get("basekey:currentVersion"))
                .andReturn(0l);
        replayAll();
        String key = serviceUnderTest.getCurrentVersionedKey("basekey");
        Assert.assertEquals("basekey:version:0", key);
        verifyAll();
    }

    @Test
    public void testGetCurrentVersionedKeyNotExist (){
        EasyMock.expect(opsMock.get("basekey:currentVersion"))
                .andReturn(null);
        replayAll();
        String key = serviceUnderTest.getCurrentVersionedKey("basekey");
        Assert.assertEquals(null, key);
        verifyAll();
    }

}
