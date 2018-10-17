package com.home.stations.repository;

import com.home.stations.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// https://www.baeldung.com/spring-data-repositories (new to me)
public interface StationRepository extends JpaRepository<Station, Long> { // inherits PagingAndSortingRepository

    List<Station> findByHdEnabled(Boolean enabled);
}
