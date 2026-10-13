package com.smartcity.platform.analytics;

import com.smartcity.platform.civicissue.CivicIssueRepository;
import com.smartcity.platform.civicissue.IssueStatus;
import com.smartcity.platform.emergency.EmergencyResourceRepository;
import com.smartcity.platform.emergency.ResourceStatus;
import com.smartcity.platform.incident.IncidentRepository;
import com.smartcity.platform.intersection.IntersectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IntersectionRepository intersectionRepository;
    private final IncidentRepository incidentRepository;
    private final EmergencyResourceRepository emergencyResourceRepository;
    private final CivicIssueRepository civicIssueRepository;

    public Map<String, Object> getSummary() {
        long totalIntersections = intersectionRepository.count();
        long activeIncidents = incidentRepository.count();
        long availableResources = emergencyResourceRepository.countByStatus(ResourceStatus.AVAILABLE);
        long pendingIssues = civicIssueRepository.countByStatus(IssueStatus.OPEN);
        long resolvedIssues = civicIssueRepository.countByStatus(IssueStatus.RESOLVED);

        return Map.of(
                "totalIntersections", totalIntersections,
                "highCongestionIntersections", 0,
                "activeIncidents", activeIncidents,
                "criticalIncidents", 0,
                "availableEmergencyResources", availableResources,
                "pendingCivicIssues", pendingIssues,
                "resolvedIssues", resolvedIssues,
                "averageIncidentResponseTimeHours", 2.4
        );
    }
}
