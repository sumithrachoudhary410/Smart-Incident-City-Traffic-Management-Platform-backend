package com.smartcity.platform.emergency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyResourceRepository extends JpaRepository<EmergencyResource, Long> {
    List<EmergencyResource> findAllByOrderByNameAsc();
    List<EmergencyResource> findByStatus(ResourceStatus status);
    long countByStatus(ResourceStatus status);
}
