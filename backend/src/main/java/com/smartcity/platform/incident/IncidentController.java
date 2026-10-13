package com.smartcity.platform.incident;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    @GetMapping
    public ResponseEntity<List<Incident>> getAll() {
        return ResponseEntity.ok(incidentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getById(@PathVariable Long id) {
        return ResponseEntity.ok(incidentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Incident> create(@Valid @RequestBody IncidentRequest request, Authentication authentication) {
        return ResponseEntity.ok(incidentService.createIncident(request, authentication.getName()));
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Incident> assignResource(@PathVariable Long id, @RequestParam Long resourceId) {
        return ResponseEntity.ok(incidentService.assignResource(id, resourceId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Incident> updateStatus(@PathVariable Long id,
                                               @RequestParam IncidentStatus status,
                                               @RequestParam(required = false) String resolutionNotes) {
        return ResponseEntity.ok(incidentService.updateStatus(id, status, resolutionNotes));
    }
}
