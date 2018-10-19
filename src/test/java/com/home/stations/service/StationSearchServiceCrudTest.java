package com.home.stations.service;

import com.home.stations.StationSearchConfig;
import com.home.stations.domain.Station;
import com.home.stations.repository.StationRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { StationSearchConfig.class }, loader = AnnotationConfigContextLoader.class)
public class StationSearchServiceCrudTest {

    @MockBean
    StationRepository repository;

    @Autowired
    //@InjectMocks
    StationSearchService service;

    private List<Station> stations;

    @Before
    public void setupTestData() { }


    @Test
    public void create() throws Exception {
        Station station =  Mockito.mock(Station.class);
        service.create(station);
        verify(repository, times(1)).save(station);
    }

    @Test
    public void show() throws Exception {
        Station station =  Mockito.mock(Station.class);
        service.show(station.getId());
        verify(repository, times(1)).findOne(station.getId());
    }

    @Test
    public void update() throws Exception {
        Station station = Mockito.mock(Station.class);
        given(repository.findOne(station.getId())).willReturn(station);
        service.update(station.getId(), station);
        verify(repository, times(1)).save(station);
    }

    @Test
    public void delete() throws Exception {
        Station station = Mockito.mock(Station.class);
        service.delete(station.getId());
        verify(repository, times(1)).delete(station.getId());
    }


    private Station mockStation() {
        Station station = Mockito.mock(Station.class);
        station.setId(RandomUtils.nextLong()); // note exists after persisted only
        station.setStationId(RandomStringUtils.randomAlphanumeric(5));
        station.setHdEnabled(true);
        station.setName(RandomStringUtils.randomAlphabetic(10));
        station.setCallSign(RandomStringUtils.randomAlphabetic(10));
        return station;
    }

}
