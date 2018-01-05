package it.ec.quest.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(it.ec.quest.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(it.ec.quest.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.Schema1.class.getName(), jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.Schema1.class.getName() + ".fields", jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.Schema1.class.getName() + ".answers", jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.SchemaField.class.getName(), jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.SchemaAnswer.class.getName(), jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.SchemaAnswer.class.getName() + ".answerFields", jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.SchemaAnswerField.class.getName(), jcacheConfiguration);
            cm.createCache(it.ec.quest.domain.Person.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
