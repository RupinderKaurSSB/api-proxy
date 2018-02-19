package io.descoped.client.cache;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import java.util.Iterator;

import static javax.cache.expiry.Duration.ONE_HOUR;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;


/**
 * @author Greg Luck
 */
public class CacheTest {

    private static final Logger log = LoggerFactory.getLogger(CacheTest.class);

    public void dump(Cache<?, ?> cache) {
        Iterator<? extends Cache.Entry<?, ?>> allCacheEntries = cache.iterator();
        while (allCacheEntries.hasNext()) {
            Cache.Entry<?, ?> currentEntry = allCacheEntries.next();
            log.trace("{}: {}", currentEntry.getKey(), currentEntry.getValue());
        }
    }

    @Test
    public void simpleAPITypeEnforcement() {
        //resolve a cache manager
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        //configure the cache
        MutableConfiguration<String, Integer> config = new MutableConfiguration<>();
        //uses store by reference
        config.setStoreByValue(false)
                .setTypes(String.class, Integer.class)
                .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
                .setStatisticsEnabled(true);

        //create the cache
        cacheManager.createCache("simpleOptionalCache", config);

        //... and then later to get the cache
        Cache<String, Integer> cache = Caching.getCache("simpleOptionalCache", String.class, Integer.class);

        //use the cache
        String key = "key";
        Integer value1 = 1;
        cache.put("key", value1);
        Integer value2 = cache.get(key);
        assertEquals(value1, value2);

        dump(cache);

        cache.remove("key");
        assertNull(cache.get("key"));

    }

}
