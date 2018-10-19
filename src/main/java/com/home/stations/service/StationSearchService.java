package com.home.stations.service;

import com.home.stations.domain.Station;
import com.home.stations.repository.StationRepository;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

/*
    Just trying to learn about faster searching with HibernateSearch
    TODO learn more, especially ElasticSearch! (experimental integration in HibernateSearch)
 */
@Service
public class StationSearchService {

    @Autowired
    private final EntityManager entityManager; // HibernateSearch

    @Autowired
    StationRepository repository; // SpringData CRUD

    @Autowired
    public StationSearchService(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public void initializeHibernateSearch() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // search related stuff

    @Transactional(readOnly = true)
    // Search for HD-enabled
    public List<Station> searchEnabled() {
        // Setup the HibernateSearch EntityManager and QueryBuilder for Station entity
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder builder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Station.class).get();

        // Query to search for enabled strictly
        Query luceneQuery = builder.keyword()
                .onField("hdEnabled")
                .matching("true").createQuery();

        // Wrap lucene query for Station entity
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Station.class);

        // run search
        List<Station> stations = Collections.emptyList();
        try {
            stations = jpaQuery.getResultList();
        } catch (NoResultException nre) { }

        return stations;
    }


    @Transactional(readOnly = true)
    // Search for supplied term among the stationId or name fields (fuzzy could be configurable)
    public List<Station> searchStationIdOrNameFuzzy(String searchTerm) {
        // Setup the HibernateSearch EntityManager and QueryBuilder for Station entity
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder builder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Station.class).get();

        // Query to search for term in either the stationId field or name field
        Query luceneQuery = builder.keyword().fuzzy().withEditDistanceUpTo(1)
                .onFields("stationId", "name")
                .matching(searchTerm).createQuery();

        // Wrap lucene query for Station entity
        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Station.class);

        // run search
        List<Station> stations = Collections.emptyList();
        try {
            stations = jpaQuery.getResultList();
        } catch (NoResultException nre) { }

        return stations;
    }


    // usual CRUD stuff. could be separate service or call repo methods from controller. or i could learn about CQRS?

    @Transactional(readOnly = true)
    public Station show(Long id) {
        return repository.findOne(id);
    }

    public Station create(Station station) {
        return repository.save(station);
    }

    public Station update(Long id, Station station) {
        Station existing = repository.findOne(id);
        existing.setStationId(station.getStationId());
        existing.setName(station.getName());
        existing.setHdEnabled(station.getHdEnabled());
        existing.setCallSign(station.getCallSign());
        return repository.save(station);
    }

    public void delete(Long id) {
        repository.delete(id);
        return;
    }


}
