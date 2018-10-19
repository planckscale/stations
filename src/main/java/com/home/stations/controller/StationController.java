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
import java.util.stream.IntStream;

@RestController
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationSearchService searchService;


    // CRUD mappings

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Station create(@RequestBody Station station) {
        return searchService.create(station);
    }

    @GetMapping("{id}")
    public Station show(@PathVariable long id) {
        Station found = searchService.show(id);
        return found;
    }

    @PutMapping("{id}")
    public Station update(@PathVariable long id, @RequestBody Station station) {
        return searchService.update(id, station);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        searchService.delete(id);
        return;
    }


    // Search mappings


    // /search/enabled
    @GetMapping("enabled") // TODO implement
    public List<Station> searchEnabled() {
        List<Station> searchResults = Collections.emptyList();
        searchResults = searchService.searchEnabled();
        return searchResults;
    }

    // /search?term=nameValue|stationIdValue
    @GetMapping("/search")
    public List<Station> searchStationIdOrNameFuzzy(@RequestParam(name = "term") String searchTerm) {
        List<Station> searchResults = Collections.emptyList();
        searchResults = searchService.searchStationIdOrNameFuzzy(searchTerm);
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
                    searchService.create(station);
                }
        );
    }
}
