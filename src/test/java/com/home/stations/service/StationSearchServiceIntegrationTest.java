package com.home.stations.service;

import com.home.stations.StationSearchConfig;
import com.home.stations.domain.Station;
import com.home.stations.repository.StationRepository;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { StationSearchConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@TestPropertySource("spring.jpa.properties.hibernate.search.default.directory_provider=ram")
public class StationSearchServiceIntegrationTest {

    @Autowired
    StationSearchService service;


    private List<Station> stations = new ArrayList<>(5);

    @Before
    public void setup() {
        setupTestData();
    }


    @Test
    @Commit
    public void entityManagerPersistAndCheck() {
        for (Station s : stations) {
            service.create(s);
        }
    }

    @Test
    public void persistedEntitiesAreIndexed() throws InterruptedException {
        FullTextEntityManager fullTextEntityManager = service.initializeHibernateSearch();
        int indexSize = fullTextEntityManager.getSearchFactory()
                .getStatistics()
                .getNumberOfIndexedEntities(Station.class.getName());
        assertEquals(stations.size(), indexSize);
    }

    @Test
    public void searchEnabled() throws Exception {
        List<Station> expected = stations.stream().filter(s -> s.getHdEnabled()).collect(Collectors.toList());
        List<Station> searched = service.searchEnabled();
        assertEquals("Expected 1 disabled", expected.size(), searched.size());
        assertEquals("Expected 1 disabled", stations.size()-1, searched.size());
        // TODO implement equals / hashcode to check actual objects as values:)
    }

    @Test
    @Ignore("In progress, failing unexpectedly. For coverage")
    public void searchTerm() throws Exception {
        String searchStationId = stations.get(3).getStationId();
        List<Station> results = service.searchStationIdOrNameFuzzy(searchStationId);
        assertEquals("Expected 1 result for search", searchStationId, results.stream().filter(s -> s.getStationId().equals(searchStationId)).collect(Collectors.reducing((a, b) -> null)));
    }



    private Station createStation() {
        Station station = new Station();
        //station.setId(RandomUtils.nextLong()); // note exists after persisted only
        station.setStationId(randomAlphanumeric(5));
        station.setHdEnabled(true);
        station.setName(randomAlphabetic(10));
        station.setCallSign(randomAlphabetic(10));
        System.out.print("Making: " + station);
        return station;
    }


    private void setupTestData() {
        for(int i=0; i<5; i++) {
            System.out.print("Create in loop: "+i);
            Station station = createStation();
            stations.add(station);
        }
        // disable one
        Station disabled = stations.get(0);
        disabled.setHdEnabled(false);
        stations.set(0, disabled);
    }
}