package com.smartcity.platform.civicissue;

import com.smartcity.platform.common.BadRequestException;
import com.smartcity.platform.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CivicIssueService {

    private final CivicIssueRepository civicIssueRepository;
    private final DepartmentRepository departmentRepository;

    public List<CivicIssue> findAll() {
        return civicIssueRepository.findAllByOrderByReportedAtDesc();
    }

    public CivicIssue findById(Long id) {
        return civicIssueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Civic issue not found with id: " + id));
    }

    @Transactional
    public CivicIssue create(CivicIssueRequest request) {
        CivicIssue issue = new CivicIssue();
        issue.setTitle(request.title());
        issue.setDescription(request.description());
        issue.setCategory(request.category());
        issue.setLocation(request.location());
        issue.setPriority(request.priority());
        issue.setImageUrl(request.imageUrl());
        issue.setAssignedTo(request.assignedTo());
        issue.setStatus(IssueStatus.OPEN);
        if (request.departmentId() != null) {
            Department department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.departmentId()));
            issue.setDepartment(department);
        }
        return civicIssueRepository.save(issue);
    }

    @Transactional
    public CivicIssue assignDepartment(Long issueId, Long departmentId) {
        CivicIssue issue = findById(issueId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        issue.setDepartment(department);
        issue.setStatus(IssueStatus.ASSIGNED);
        return civicIssueRepository.save(issue);
    }

    @Transactional
    public CivicIssue updateStatus(Long issueId, IssueStatus status, String resolutionNotes) {
        CivicIssue issue = findById(issueId);
        if (status == null) {
            throw new BadRequestException("Status is required");
        }
        issue.setStatus(status);
        if (status == IssueStatus.RESOLVED || status == IssueStatus.CLOSED) {
            issue.setResolvedAt(LocalDateTime.now());
        }
        if (resolutionNotes != null && !resolutionNotes.isBlank()) {
            issue.setResolutionNotes(resolutionNotes);
        }
        return civicIssueRepository.save(issue);
    }
}
