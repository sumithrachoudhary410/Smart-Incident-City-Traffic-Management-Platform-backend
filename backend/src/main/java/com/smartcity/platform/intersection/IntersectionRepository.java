package com.smartcity.platform.intersection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntersectionRepository extends JpaRepository<Intersection, Long> {
    Optional<Intersection> findByCode(String code);
}
