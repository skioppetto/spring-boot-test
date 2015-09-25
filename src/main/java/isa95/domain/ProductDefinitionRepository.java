package isa95.domain;

import isa95.Utils.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by user on 9/24/15.
 */
@Component
public class ProductDefinitionRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    public void addOrUpdateProductDefinition (final ProductDefinition pd) throws RepositoryException{
        // define productDefinition base key: productDefinition:6758FRERRR980
        final String baseKey = "productDefinition:".concat(pd.getProductDefinitionID());
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        // check extended types
        for (AbstractExtendedProperty prop : pd.getExtendedProperties())
                if (prop.getType() == null || !Arrays.asList(ExtendedPropertyTypeEnum.values()).contains(prop.getType()))
                    throw new RepositoryException();
        // start transaction
        Object txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                try {
                    operations.multi();
                    // calculate current version
                    Long version = operations.opsForValue().increment(baseKey.concat(":currentVersion"), 1);
                    String key = baseKey.concat(":version:").concat(pd.getVersion());
                    // update pd with current version
                    pd.setVersion(version.toString());
                    // update pd with current date
                    pd.setPublishedDate(sdf.format(new Date()));
                    // write on db
                    operations.opsForHash().putAll(key, ReflectionUtils.getStringPropertiesMap(pd));
                    // for each extended property build a new hash value, include value only if type==VALUE
                    for (AbstractExtendedProperty property : pd.getExtendedProperties()) {
                        Map<String, String> objectMap = ReflectionUtils.getStringPropertiesMap(property);
                        String propertyKey = key.concat(":property:").concat(property.getName());
                        switch(property.getType()){
                            case Value:
                                objectMap.put("value", ((ExtendedPropertyValue) property).getValue());
                                break;
                            case Set:
                                operations.delete(propertyKey.concat(":values"));
                                operations.opsForSet().add(propertyKey.concat(":values"), ((ExtendedPropertySet) property).getValueSet());
                                break;
                            case Ranges:
                                // remove old ranges
                                Set<String> propRanges = operations.keys(propertyKey.concat(":ranges:*"));
                                for (String propRange : propRanges)
                                    operations.delete(propRange);
                                // add new ranges
                                List<ExtendedPropertyRangeDescriptor> ranges = ((ExtendedPropertyRange) property).getValueRanges();
                                for (int i=0; i< ranges.size(); i++)
                                    operations.opsForHash().putAll(propertyKey.concat("ranges:").concat(String.valueOf(i)), ReflectionUtils.getStringPropertiesMap(ranges.get(i)));
                                break;
                        }
                        operations.opsForHash().putAll(propertyKey, objectMap);
                    }
                    return operations.exec();
                } catch (IntrospectionException e) {
                    operations.discard();
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    operations.discard();
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    operations.discard();
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void removeProductDefinition (String productDefinitionID){

    }

    public ProductDefinition getProductDefinition (String productDefinitionID){
        return null;
    }



}
