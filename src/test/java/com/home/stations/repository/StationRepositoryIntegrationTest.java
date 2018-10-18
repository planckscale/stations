package com.home.stations.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.stations.domain.Station;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = StationRepository.class)
@EntityScan(basePackageClasses = Station.class)
// sanity check for the generated SpringData methods we actually use
public class StationRepositoryIntegrationTest {

    @Autowired
    StationRepository dao;

    @Test
    public void save() {
        // given
        Station station = createStation();

        // when
        assertThat(station.getId() == null);

        // then
        Station persisted = dao.save(station);
        assertThat(persisted.getId() != null);
        assertThat(persisted.getStationId().equals(station.getStationId()));
    }


    @Test
    public void findOne() {
        // given
        Station station = createStation();
        dao.save(station);

        // when
        assertThat(station.getId() != null);

        // then
        Station found = dao.findOne(station.getId());
        assertThat(found.getId().longValue() == station.getId().longValue());
        assertThat(found.getStationId().equals(station.getStationId()));
    }

    @Test
    public void update() {
        // given
        Station station = createStation();
        dao.save(station);

        // when
        String newStationId = randomAlphanumeric(5);
        station.setStationId(newStationId);
        dao.save(station);

        // then
        Station updated = dao.findOne(station.getId());
        assertThat(updated.getId().longValue() == station.getId().longValue());
        assertThat(!updated.getStationId().equals(station.getStationId()));
        assertThat(updated.getStationId().equals(newStationId));
    }

    @Test
    public void delete() {
        // given
        Station station = createStation();
        dao.save(station);

        // when
        dao.delete(station);

        // then
        Station notFound = dao.findOne(station.getId());
        assertThat(notFound == null);
    }



    private Station createStation() {
        Station station = new Station();
        //station.setId(RandomUtils.nextLong()); // note exists after persisted only
        station.setStationId(randomAlphanumeric(5));
        station.setHdEnabled(true);
        station.setName(randomAlphabetic(10));
        station.setCallSign(randomAlphabetic(10));
        return station;
    }

}

