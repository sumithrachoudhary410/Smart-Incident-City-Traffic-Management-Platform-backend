package com.smartcity.platform.traffic;

import com.smartcity.platform.common.BadRequestException;
import com.smartcity.platform.intersection.Intersection;
import com.smartcity.platform.intersection.IntersectionRepository;
import com.smartcity.platform.intersection.TrafficCongestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrafficService {

    private final TrafficObservationRepository trafficObservationRepository;
    private final IntersectionRepository intersectionRepository;

    public List<TrafficObservation> getObservations() {
        return trafficObservationRepository.findAllByOrderByObservedAtDesc();
    }

    @Transactional
    public TrafficObservation createObservation(Long intersectionId, Integer vehicleCount) {
        if (vehicleCount == null || vehicleCount < 0) {
            throw new BadRequestException("Vehicle count must be zero or greater");
        }

        Intersection intersection = intersectionRepository.findById(intersectionId)
                .orElseThrow(() -> new BadRequestException("Intersection not found"));

        double density = Math.min(100.0, Math.round((vehicleCount / 200.0) * 100.0) / 1.0);
        String level = classifyCongestion(density);

        TrafficObservation observation = new TrafficObservation();
        observation.setIntersection(intersection);
        observation.setVehicleCount(vehicleCount);
        observation.setDensityScore(density);
        observation.setCongestionLevel(level);
        observation.setObservedAt(LocalDateTime.now());

        TrafficObservation saved = trafficObservationRepository.save(observation);
        intersection.setCongestionLevel(TrafficCongestion.valueOf(level));
        intersectionRepository.save(intersection);
        return saved;
    }

    private String classifyCongestion(double density) {
        if (density < 35) return "LOW";
        if (density < 60) return "MEDIUM";
        if (density < 80) return "HIGH";
        return "VERY_HIGH";
    }
}
