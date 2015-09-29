package isa95.service;

import isa95.Utils.RedisKeyUtils;
import isa95.Utils.ReflectionUtils;
import isa95.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by user on 9/29/15.
 */
public class ExtendedPropertyService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommonRedisService commonService;

    public void addOrUpdateExtendedProperties (String baseKey, final List<AbstractExtendedProperty> properties) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        // find base key for indexes removing the code: es "productDefinition:<prod_code> -> productDefinition"
        String idxBaseKey = RedisKeyUtils.popLevel(baseKey);
        for (AbstractExtendedProperty property : properties) {
            Map<String, String> objectMap = ReflectionUtils.getStringPropertiesMap(property);
            String propertyKey = RedisKeyUtils.pushLevels(baseKey, "extProperty", property.getName());
            String idxKey = RedisKeyUtils.pushLevels(idxBaseKey, "extProperty", property.getName(), "idx");
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
                    // add them to idx only if are numbers
                    Set<String> valueSet = ((ExtendedPropertySet) property).getValueSet();
                    String valuesKey = RedisKeyUtils.pushLevel(propertyKey, "values");
                    redisTemplate.delete(valuesKey);
                    redisTemplate.opsForSet().add(valuesKey, valueSet);
                    if (ExtendedPropertyFormatEnum.Decimal.equals(property.getFormat()) ||
                            ExtendedPropertyFormatEnum.Number.equals(property.getFormat())) {
                        commonService.removeIncrKeysFromSortedSet(idxKey, baseKey);
                        Set<Double> valueScores = new HashSet<Double>();
                        valueSet.stream().forEach(d -> valueScores.add(Double.valueOf(d)));
                        commonService.addIncrKeysInSortedSet(idxKey, idxBaseKey, valueScores);
                    }
                    break;
                case Ranges:
                    // create an hash for each range with min and max value with key <propertyKey>:ranges:incr with incr>=0,
                    // than add to sorted set weighted by min and sorted set weighted by max the <propertyKey>:ranges:incr key
                    String rangeKey = RedisKeyUtils.pushLevel(propertyKey, "ranges");
                    String idxRangeMinKey = RedisKeyUtils.pushLevel(idxKey, "min");
                    String idxRangeMaxKey = RedisKeyUtils.pushLevel(idxKey, "max");
                    // clean hash and sorted sets
                    commonService.removeIncrKeys(rangeKey);
                    commonService.removeIncrKeysFromSortedSet(idxRangeMinKey, rangeKey);
                    commonService.removeIncrKeysFromSortedSet(idxRangeMaxKey, rangeKey);
                    // add new ranges
                    List<ExtendedPropertyRangeDescriptor> ranges = ((ExtendedPropertyRange) property).getValueRanges();
                    for (int i=0; i< ranges.size(); i++) {
                        String rangeIKey = RedisKeyUtils.pushLevel(rangeKey, i);
                        redisTemplate.opsForHash().putAll(rangeIKey, ReflectionUtils.getStringPropertiesMap(ranges.get(i)));
                        redisTemplate.opsForZSet().add(idxRangeMinKey, rangeIKey, Double.valueOf(ranges.get(i).getValueMin()));
                        redisTemplate.opsForZSet().add(idxRangeMaxKey, rangeIKey, Double.valueOf(ranges.get(i).getValueMax()));
                    }
                    break;
                    }
                    redisTemplate.opsForHash().putAll(propertyKey, objectMap);
            }

    }

}
