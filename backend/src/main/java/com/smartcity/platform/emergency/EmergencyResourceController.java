package com.smartcity.platform.emergency;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class EmergencyResourceController {

    private final EmergencyResourceService emergencyResourceService;

    @GetMapping
    public ResponseEntity<List<EmergencyResource>> getAll() {
        return ResponseEntity.ok(emergencyResourceService.findAll());
    }

    @GetMapping("/available")
    public ResponseEntity<List<EmergencyResource>> getAvailable() {
        return ResponseEntity.ok(emergencyResourceService.findAvailable());
    }

    @PostMapping
    public ResponseEntity<EmergencyResource> create(@Valid @RequestBody ResourceRequest request) {
        return ResponseEntity.ok(emergencyResourceService.create(request));
    }

    @GetMapping("/route")
    public ResponseEntity<List<String>> route(@RequestParam String source,
                                            @RequestParam String target,
                                            @RequestParam(required = false) Map<String, Map<String, Double>> graph) {
        Map<String, Map<String, Double>> routeGraph = graph == null ? Map.of(
                "A", Map.of("B", 5.0, "C", 10.0),
                "B", Map.of("C", 2.0, "D", 6.0),
                "C", Map.of("D", 3.0),
                "D", Map.of()
        ) : graph;
        return ResponseEntity.ok(emergencyResourceService.calculateShortestPath(source, target, routeGraph));
    }
}
