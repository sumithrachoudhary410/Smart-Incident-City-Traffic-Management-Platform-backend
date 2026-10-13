package com.smartcity.platform.intersection;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intersections")
@RequiredArgsConstructor
public class IntersectionController {

    private final IntersectionService intersectionService;

    @GetMapping
    public ResponseEntity<List<Intersection>> getAll() {
        return ResponseEntity.ok(intersectionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Intersection> getById(@PathVariable Long id) {
        return ResponseEntity.ok(intersectionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Intersection> create(@Valid @RequestBody IntersectionRequest request) {
        return ResponseEntity.ok(intersectionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Intersection> update(@PathVariable Long id, @Valid @RequestBody IntersectionRequest request) {
        return ResponseEntity.ok(intersectionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        intersectionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
