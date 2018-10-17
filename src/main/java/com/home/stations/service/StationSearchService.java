package com.home.stations.service;

import com.home.stations.domain.Station;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

/*
    Just trying to learn about faster searching with HibernateSearch
    TODO learn more, especially ElasticSearch! (experimental integration in HibernateSearch)
 */
@Service
public class StationSearchService {

    @Autowired
    private final EntityManager centityManager;

    @Autowired
    public StationSearchService(EntityManager entityManager) {
        super();
        this.centityManager = entityManager;
    }

    public void initializeHibernateSearch() {
        try {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(centityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // search related stuff

    @Transactional(readOnly = true)
    public List<Station> indexedSearch(String searchTerm) {
        return Collections.emptyList();
    }


    // usual CRUD stuff. could be separate service or call repo methods from controller. or i could learn about CQRS?

    @Transactional(readOnly = true)
    public List<Station> findAll() {
        return Collections.emptyList();
    }

}
