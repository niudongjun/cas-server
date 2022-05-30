package org.apereo.cas.localcache;

/**
 * description:
 *
 * @author : niudongjun
 * @date : 2022/5/29 18:05
 */
public class SimpleCache extends CacheProxy<String, String> {
    public SimpleCache(String name) {
        super(name);
    }

    public void put(String key, String value) {
        this.cacheOperate.put(key, value);
    }

    public String get(String key) {
        return this.cacheOperate.getIfPresent(key);
    }

    public void remove(String key) {
        this.cacheOperate.invalidate(key);
    }
}
