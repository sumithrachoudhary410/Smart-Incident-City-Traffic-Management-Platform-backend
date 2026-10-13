package com.smartcity.platform.traffic;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traffic")
@RequiredArgsConstructor
public class TrafficController {

    private final TrafficService trafficService;

    @GetMapping("/observations")
    public ResponseEntity<List<TrafficObservation>> getObservations() {
        return ResponseEntity.ok(trafficService.getObservations());
    }

    @PostMapping("/observations")
    public ResponseEntity<TrafficObservation> createObservation(@RequestParam Long intersectionId,
                                                              @Valid @RequestParam Integer vehicleCount) {
        return ResponseEntity.ok(trafficService.createObservation(intersectionId, vehicleCount));
    }
}
