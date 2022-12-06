package study.springaws.global.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/*
* 캐시 인터페이스 설정
* */
@Configuration
public class CacheConfig {

    /*
       - EhCache 팩터리 빈 등록
   */
    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactoryBean(){
        return new EhCacheManagerFactoryBean();
    }

    /*
        - Ehcache 등록
          - 메모리 <-> 성능 트레이드 오프를 고려할때 1)레이아웃 캐싱과 2)매핑 비용이 비싼 rss정도만 캐시처리
            - 캐시 폐기는 6시간마다
    */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(){

        CacheConfiguration layoutCacheConfiguration = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(3600)
                .timeToLiveSeconds(3600)
                .maxEntriesLocalHeap(5)
                .memoryStoreEvictionPolicy("LRU")
                .name("layoutCaching");

        Cache layoutCache = new net.sf.ehcache.Cache(layoutCacheConfiguration);
        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(layoutCache);

        // 2. 레이아웃에 필요한 동적 리스트반환 메서드용 캐시
        CacheConfiguration recentPostCacheConfiguration = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(3600)
                .timeToLiveSeconds(3600)
                .maxEntriesLocalHeap(100)
                .memoryStoreEvictionPolicy("LRU")
                .name("layoutRecentArticleCaching");

        Cache recentPostCache = new net.sf.ehcache.Cache(recentPostCacheConfiguration);
        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(recentPostCache);

        // 3. 레이아웃에 필요한 리스트 반환 메서드용 캐시, 유지보수를 위해 분리
        CacheConfiguration recentCommentCacheConfiguration = new CacheConfiguration()
                .eternal(false)
                .timeToIdleSeconds(3600)
                .timeToLiveSeconds(3600)
                .maxEntriesLocalHeap(5)
                .memoryStoreEvictionPolicy("LRU")
                .name("layoutRecentCommentCaching");

        Cache recentCommentCache = new net.sf.ehcache.Cache(recentCommentCacheConfiguration);
        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(recentCommentCache);

        return new EhCacheCacheManager(Objects.requireNonNull(cacheManagerFactoryBean().getObject()));
    }
}
