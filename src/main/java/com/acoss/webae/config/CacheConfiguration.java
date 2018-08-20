package com.acoss.webae.config;

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
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
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
            cm.createCache(com.acoss.webae.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.acoss.webae.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.Taux.class.getName(), jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.UserDevice.class.getName(), jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.LastConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.Demande.class.getName(), jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.PreferenceNotif.class.getName(), jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.UserPreference.class.getName(), jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.UserPreference.class.getName() + ".userDevices", jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.UserPreference.class.getName() + ".lastConnections", jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.UserPreference.class.getName() + ".demandes", jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.UserPreference.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(com.acoss.webae.domain.UserPreference.class.getName() + ".preferenceNotifs", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
