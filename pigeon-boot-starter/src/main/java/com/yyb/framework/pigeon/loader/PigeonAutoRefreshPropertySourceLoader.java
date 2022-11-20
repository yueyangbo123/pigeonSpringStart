package com.yyb.framework.pigeon.loader;

import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/20
 * @description
 */

public class PigeonAutoRefreshPropertySourceLoader implements PropertySourceLoader {

    private final Map<String, Object> properties = new ConcurrentHashMap<>(16);

    @Override
    public String[] getFileExtensions() {
        return new String[]{"json"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        properties.put("yyb.name" , "y");
     /*   PropertySource pigeon_application_conf = new OriginTrackedMapPropertySource(
                "pigeon_application_conf" , Collections.unmodifiableMap(properties), true);*/
        PropertySource pigeon_application_conf = new OriginTrackedMapPropertySource(
                name , Collections.unmodifiableMap(properties), true);
        List<PropertySource<?>> list = new ArrayList<>();
        list.add(pigeon_application_conf);
        return list;

    }
}
