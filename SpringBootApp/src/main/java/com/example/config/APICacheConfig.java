package com.example.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

@Configuration
@EnableCaching
public class APICacheConfig extends CachingConfigurerSupport {

	/*
	 * @Bean public APIFilter aPIFilter() { return new APIFilter(); }
	 */

	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
		CacheConfiguration EmpCache = new CacheConfiguration();
		EmpCache.setName("Emp-Cache");
		EmpCache.setMemoryStoreEvictionPolicy("LFU");
		//EmpCache.setMaxElementsInMemory(500);
		EmpCache.setEternal(true);
		EmpCache.setMaxEntriesLocalHeap(1000);
		EmpCache.setTimeToLiveSeconds(3600);
		EmpCache.setTimeToIdleSeconds(1200);
		//EmpCache.setOverflowToDisk(true);
		//EmpCache.setDiskPersistent(true);
		EmpCache.setDiskExpiryThreadIntervalSeconds(1);
		EmpCache.persistence(new PersistenceConfiguration().strategy(net.sf.ehcache.config.PersistenceConfiguration.Strategy.LOCALTEMPSWAP));
		
		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.diskStore(new DiskStoreConfiguration().path("C:\\Users\\punsahoo\\test"));
		config.addCache(EmpCache);
		return net.sf.ehcache.CacheManager.newInstance(config);
	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}
}
