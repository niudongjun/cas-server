package org.apereo.cas.localcache;

import com.google.common.cache.Cache;

/**
 * description:
 *
 * @author : niudongjun
 * @date : 2022/5/29 17:15
 */
public interface CacheOperate<K, V> extends Cache<Comparable<K>, V> {
}
