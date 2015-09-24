package isa95.domain;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by user on 9/24/15.
 */
@Component
public class ProductDefinitionRepository {

    @Resource(name="redisTemplate")
    private HashOperations<String,String,String> redisHashOperations;

    public void addOrUpdateProductDefinition (ProductDefinition pd) {

        try {
            //TODO: manage version update in case exists
            //TODO: manage extended properties creation on sets, hashes and ext properties
            String key = "productDefinition:".concat(pd.getProductDefinitionID());
            Map<String, String> isaMap = DomainUtils.toHashMapProperties(pd);



        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProductDefinition (String productDefinitionID){

    }

    public ProductDefinition getProductDefinition (String productDefinitionID){
        return null;
    }



}
