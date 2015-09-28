package isa95.domain.tests;

import isa95.domain.ProductDefinitionRepository;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by user on 9/28/15.
 */
public class ProductDefinitionRepositoryTest extends EasyMockSupport{


    @Mock
    private RedisTemplate redisCollaborator;

    @TestSubject
    private final ProductDefinitionRepository underTest = new ProductDefinitionRepository();

    public void testAddProductDefinition (){
        //TODO: before need to encapusalte untestable code
    }


}
