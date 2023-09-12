package com.example.demo.util;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {


    public static String generateToken(){
        return RandomUtil.randomString(32);
    }

    public static void main(String[] args) {
        System.out.println(generateToken());
    }

    //Object转Map
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();
            Object value = field.get(obj);
            if (value == null)
                value = "";
            map.put(keyName, value);
        }
        return map;
    }

    //Map转Object
    public static Object getMapToObject(Map<Object, Object> map, Class<?> cla) throws IllegalAccessException, InstantiationException {
        if (map == null)
            return null;
        Object obj = cla.newInstance();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }
}
