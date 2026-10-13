package com.smartcity.platform.civicissue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CivicIssueRepository extends JpaRepository<CivicIssue, Long> {
    List<CivicIssue> findAllByOrderByReportedAtDesc();
    List<CivicIssue> findByCategory(IssueCategory category);
    List<CivicIssue> findByStatus(IssueStatus status);
    long countByStatus(IssueStatus status);
}
