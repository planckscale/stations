package com.home.stations.repository;

import com.home.stations.domain.Station;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = StationRepository.class)
@EntityScan(basePackageClasses = Station.class)
// warm up, sort of pointless; could be useful has H2 and rollback
public class StationRepositoryIntegrationTest {

    @Autowired
    StationRepository dao;

    @Test
    public void saveAndFindSanityCheckItsBakedIn() {
        Station station = new Station();
        station.setStationId("abc123");
        station.setHdEnabled(true);
        station.setName("Station 777");
        station.setCallSign("Call sign 777");
        dao.save(station);

        List<Station> stations = dao.findAll();
        assertThat(stations.size() == 1);
        assertThat(stations.get(0).getId() != null);
        assertThat(stations.get(0).getStationId().equals("abc123"));
    }

}

