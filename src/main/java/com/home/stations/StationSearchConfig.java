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
    private EntityManager bentityManager;

    @Bean
    StationSearchService stationSearchService() {
        StationSearchService stationSearchService = new StationSearchService(bentityManager);
        stationSearchService.initializeHibernateSearch();
        return stationSearchService;
    }
}

