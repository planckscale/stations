package com.home.stations.controller;

import com.home.stations.domain.Station;
import com.home.stations.repository.StationRepository;
import com.home.stations.service.StationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationSearchService searchService;

    @Autowired
    // probably use in a service but convenient for now for CRUD
    private StationRepository stationRepository;


    // CRUD mappings

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Station create(@RequestBody Station station) {
        return stationRepository.save(station);
    }

    @GetMapping("{id}")
    public Station show(@PathVariable long id) {
        return stationRepository.findOne(id);
    }

    @PutMapping("{id}")
    public Station update(@PathVariable long id, @RequestBody Station station) {
        Station existing = stationRepository.getOne(id);
        existing.setStationId(station.getStationId());
        existing.setName(station.getName());
        existing.setHdEnabled(station.getHdEnabled());
        existing.setCallSign(station.getCallSign());
        return stationRepository.save(station);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        stationRepository.delete(id);
        return;
    }


    // Finders mappings

    @GetMapping("enabled")
    public List<Station> showEnabled() {
        return stationRepository.findByHdEnabled(true);
    }


    // Search mapping

    // e.g. /search?stationId=777&name=xyz  ; not sure the best way; maybe post in an object key
    @GetMapping("/search") // TODO implement
    public List<Station> search(@RequestParam Map<String, String> searchQuery) {
        List<Station> searchResults = Collections.emptyList();
        return searchResults;
    }



    @PostConstruct // TODO delete me; sample data
    public void todo() {
        IntStream.range(1, 10).forEach(
                i -> { Station station = new Station();
                    station.setStationId(String.valueOf(i));
                    station.setHdEnabled(true);
                    station.setName(String.format("Station %s", String.valueOf(i)));
                    station.setCallSign(String.format("Call Sign %s", String.valueOf(i)));
                    stationRepository.save(station);
                    System.out.println(i + " => " + station);
                }
        );
    }
}
