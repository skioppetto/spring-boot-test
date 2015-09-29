package isa95.service;

import isa95.Utils.ReflectionUtils;
import isa95.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractCollection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 9/29/15.
 */
public class ExtendedPropertyService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void addOrUpdateExtendedProperties (String baseKey, final List<AbstractExtendedProperty> properties) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        // find base key for indexes: es "productDefinition"
        String idxBaseKey = baseKey.substring(0,baseKey.indexOf(':'));
        for (AbstractExtendedProperty property : properties) {
            Map<String, String> objectMap = ReflectionUtils.getStringPropertiesMap(property);
            String propertyKey = baseKey.concat(":extProperty:").concat(property.getName());
            String idxKey = idxBaseKey.concat(":extProperty:").concat(property.getName()).concat(":idx");
            switch (property.getType()) {
                case Value:
                    // store value in map as string, add to idx only if is number
                    String value = ((ExtendedPropertyValue) property).getValue();
                    if (ExtendedPropertyFormatEnum.Decimal.equals(property.getFormat()) ||
                            ExtendedPropertyFormatEnum.Number.equals(property.getFormat())) {
                        Double convertedValue = Double.valueOf(value);
                        redisTemplate.opsForZSet().add(idxKey, baseKey, convertedValue);
                    }
                    objectMap.put("value", value);
                    break;
                case Set:
                    // store values in new set "<baseKey>:extProperty:<propName>:values",
                    // add them to idx only if is number
                    Set<String> valueSet = ((ExtendedPropertySet) property).getValueSet();
                    redisTemplate.delete(propertyKey.concat(":values"));
                    redisTemplate.opsForSet().add(propertyKey.concat(":values"), valueSet);
                    if (ExtendedPropertyFormatEnum.Decimal.equals(property.getFormat()) ||
                            ExtendedPropertyFormatEnum.Number.equals(property.getFormat())) {
                        //TODO:clean previous indexed values
                        int count = 0;
                        for (String s : valueSet) {
                            Double convertedValue = Double.valueOf(s);
                            redisTemplate.opsForZSet().add(idxKey, baseKey.concat(":").concat(String.valueOf(count++)), convertedValue);
                        }
                    }
                        break;
                        case Ranges:
                            // remove old ranges
                            Set<String> propRanges = redisTemplate.keys(propertyKey.concat(":ranges:*"));
                            redisTemplate.delete(propRanges);
                            // add new ranges
                            List<ExtendedPropertyRangeDescriptor> ranges = ((ExtendedPropertyRange) property).getValueRanges();
                            for (int i = 0; i < ranges.size(); i++)
                                redisTemplate.opsForHash().putAll(propertyKey.concat("ranges:").concat(String.valueOf(i)), ReflectionUtils.getStringPropertiesMap(ranges.get(i)));
                            break;
                    }
                    redisTemplate.opsForHash().putAll(propertyKey, objectMap);
            }

    }

}
