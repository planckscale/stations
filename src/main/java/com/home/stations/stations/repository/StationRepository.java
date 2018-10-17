package com.home.stations.stations.repository;

import com.home.stations.stations.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

// https://www.baeldung.com/spring-data-repositories
// https://docs.jboss.org/hibernate/search/5.6/reference/en-US/html/ch04.html#indexed-annotation
public interface StationRepository extends JpaRepository<Station, Long> { // inhereits PagingAndSortingRepository

}
