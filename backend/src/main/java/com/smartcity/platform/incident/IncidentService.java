package com.smartcity.platform.incident;

import com.smartcity.platform.common.BadRequestException;
import com.smartcity.platform.common.ResourceNotFoundException;
import com.smartcity.platform.emergency.EmergencyResource;
import com.smartcity.platform.emergency.EmergencyResourceService;
import com.smartcity.platform.emergency.EmergencyResourceType;
import com.smartcity.platform.user.User;
import com.smartcity.platform.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;
    private final EmergencyResourceService emergencyResourceService;

    public List<Incident> findAll() {
        return incidentRepository.findAllByOrderByReportedTimeDesc();
    }

    public Incident findById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));
    }

    @Transactional
    public Incident createIncident(IncidentRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Incident incident = new Incident();
        incident.setType(request.type());
        incident.setDescription(request.description());
        incident.setLatitude(request.latitude());
        incident.setLongitude(request.longitude());
        incident.setSeverity(request.severity());
        incident.setReportedTime(LocalDateTime.now());
        incident.setStatus(IncidentStatus.REPORTED);
        incident.setReportedBy(user);
        return incidentRepository.save(incident);
    }

    @Transactional
    public Incident assignResource(Long incidentId, Long resourceId) {
        Incident incident = findById(incidentId);
        EmergencyResource resource = emergencyResourceService.findById(resourceId);
        if (resource.getStatus() != com.smartcity.platform.emergency.ResourceStatus.AVAILABLE) {
            throw new BadRequestException("Selected resource is not available");
        }

        resource.setStatus(com.smartcity.platform.emergency.ResourceStatus.ASSIGNED);
        incident.setAssignedResource(resource);
        incident.setAssignedTeam(resource.getType().name());
        incident.setStatus(IncidentStatus.ASSIGNED);
        return incidentRepository.save(incident);
    }

    @Transactional
    public Incident updateStatus(Long incidentId, IncidentStatus status, String resolutionNotes) {
        Incident incident = findById(incidentId);
        incident.setStatus(status);
        if (resolutionNotes != null && !resolutionNotes.isBlank()) {
            incident.setResolutionNotes(resolutionNotes);
        }
        return incidentRepository.save(incident);
    }

    public EmergencyResourceType getSuggestedResourceType(IncidentType type) {
        return switch (type) {
            case MEDICAL -> EmergencyResourceType.AMBULANCE;
            case FIRE -> EmergencyResourceType.FIRE_ENGINE;
            case ACCIDENT, ROAD_BLOCKAGE -> EmergencyResourceType.POLICE_UNIT;
            default -> EmergencyResourceType.RESPONSE_TEAM;
        };
    }
}
