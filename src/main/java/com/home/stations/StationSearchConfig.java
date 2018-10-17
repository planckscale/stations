package com.home.stations;

import javax.persistence.EntityManager;

import com.home.stations.service.StationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
public class StationSearchConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    StationSearchService stationSearchService() {
        StationSearchService stationSearchService = new StationSearchService(entityManager);
        stationSearchService.initializeHibernateSearch();
        return stationSearchService;
    }
}

