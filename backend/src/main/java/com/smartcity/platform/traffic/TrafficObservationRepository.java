package com.smartcity.platform.traffic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrafficObservationRepository extends JpaRepository<TrafficObservation, Long> {
    List<TrafficObservation> findAllByOrderByObservedAtDesc();
}
