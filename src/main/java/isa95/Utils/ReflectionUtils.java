package isa95.Utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 9/25/15.
 */
public class ReflectionUtils {

    public static Map<String, String> getStringPropertiesMap (Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, String> objectAsMap = new HashMap<String, String>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null && String.class.equals(pd.getPropertyType()))
                objectAsMap.put(pd.getName(), (String) reader.invoke(obj));
        }
        return objectAsMap;
    }

}
