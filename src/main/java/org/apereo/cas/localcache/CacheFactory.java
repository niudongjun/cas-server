package org.apereo.cas.localcache;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description:
 *
 * @author : niudongjun
 * @date : 2022/5/29 17:56
 */
public class CacheFactory {
    private static final Map<String, CacheProxy> CACHE_INSTANCE_MAP = new ConcurrentHashMap<>();

    public static SimpleCache getSimpleCache(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("");
        }
        SimpleCache cacheProxy = (SimpleCache) CACHE_INSTANCE_MAP.get(name);
        if (Objects.isNull(cacheProxy)) {
            synchronized (CACHE_INSTANCE_MAP) {
                SimpleCache simpleCache = new SimpleCache(name);
                CACHE_INSTANCE_MAP.put(name, simpleCache);
                return simpleCache;
            }
        }
        return cacheProxy;
    }
}
