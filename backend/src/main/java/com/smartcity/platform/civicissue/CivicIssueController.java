package com.smartcity.platform.civicissue;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/civic-issues")
@RequiredArgsConstructor
public class CivicIssueController {

    private final CivicIssueService civicIssueService;

    @GetMapping
    public ResponseEntity<List<CivicIssue>> getAll() {
        return ResponseEntity.ok(civicIssueService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CivicIssue> getById(@PathVariable Long id) {
        return ResponseEntity.ok(civicIssueService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CivicIssue> create(@Valid @RequestBody CivicIssueRequest request) {
        return ResponseEntity.ok(civicIssueService.create(request));
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<CivicIssue> assignDepartment(@PathVariable Long id, @RequestParam Long departmentId) {
        return ResponseEntity.ok(civicIssueService.assignDepartment(id, departmentId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CivicIssue> updateStatus(@PathVariable Long id,
                                                  @RequestParam IssueStatus status,
                                                  @RequestParam(required = false) String resolutionNotes) {
        return ResponseEntity.ok(civicIssueService.updateStatus(id, status, resolutionNotes));
    }
}
