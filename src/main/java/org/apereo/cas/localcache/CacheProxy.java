package org.apereo.cas.localcache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * description:
 *
 * @author : niudongjun
 * @date : 2022/5/29 17:20
 */
public class CacheProxy<K, V> implements InvocationHandler {
    private static final Map<String, Cache<Comparable<?>, ?>> CACHE_INSTANCE_MAP = new ConcurrentHashMap<>();

    private final String name;

    private final Map<String, String> conf;

    protected CacheOperate<K, V> cacheOperate;

    public CacheProxy(String name) {
        this(name, null);
    }

    public CacheProxy(String name, Map<String, String> conf) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Cache name invalid");
        }
        this.name = name;
        this.conf = conf;
        this.cacheOperate = (CacheOperate<K, V>) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{CacheOperate.class}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(obtainCache(), args);
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    private Cache<Comparable<?>, ?> obtainCache() {
        Cache<Comparable<?>, ?> cache = CACHE_INSTANCE_MAP.get(name);
        if (Objects.isNull(cache)) {
            synchronized (CACHE_INSTANCE_MAP) {
                CacheBuilder<Object, Object> build = CacheBuilder.newBuilder();
                if (MapUtils.isNotEmpty(conf)) {
                    String maxSize = conf.get("maxSize");
                    String expireAfterAccess = conf.get("expireAfterAccess");
                    String expireAfterWrite = conf.get("expireAfterWrite");
                    if (Objects.nonNull(maxSize) && NUMBER_PATTERN.matcher(maxSize).matches()) {
                        build.maximumSize(Long.parseLong(maxSize));
                    } else {
                        build.maximumSize(1000);
                    }
                    if (Objects.nonNull(expireAfterAccess) && NUMBER_PATTERN.matcher(expireAfterAccess).matches()) {
                        build.expireAfterAccess(Long.parseLong(expireAfterAccess), TimeUnit.MILLISECONDS);
                    } else {
                        build.expireAfterAccess(1, TimeUnit.SECONDS);
                    }
                    if (Objects.nonNull(expireAfterWrite) && NUMBER_PATTERN.matcher(expireAfterWrite).matches()) {
                        build.expireAfterWrite(Long.parseLong(expireAfterWrite), TimeUnit.MILLISECONDS);
                    } else {
                        build.expireAfterWrite(2, TimeUnit.SECONDS);
                    }
                }
                Cache<Comparable<?>, ?> comparableCache = build.build();
                CACHE_INSTANCE_MAP.put(name, comparableCache);
                return comparableCache;
            }
        } else {
            return cache;
        }
    }
}
