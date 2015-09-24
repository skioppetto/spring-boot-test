package isa95.domain;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 9/24/15.
 */
public final class DomainUtils {

    public static Map<String, String> toHashMapProperties(Object pd) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        Map<String, String> objectAsMap = new HashMap<String, String>();
        BeanInfo info = Introspector.getBeanInfo(pd.getClass());
        for (PropertyDescriptor descriptor : info.getPropertyDescriptors()){
            Method reader = descriptor.getReadMethod();
            if (String.class.equals(descriptor.getPropertyType()) && null != reader)
                objectAsMap.put("isa".concat(descriptor.getName()), (String) reader.invoke(pd));
        }
        return objectAsMap;
    }
}
