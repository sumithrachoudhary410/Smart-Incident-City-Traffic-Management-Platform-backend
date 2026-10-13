package com.smartcity.platform.incident;

import com.smartcity.platform.emergency.EmergencyResource;
import com.smartcity.platform.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@Getter
@Setter
@NoArgsConstructor
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentType type;

    @Column(nullable = false, length = 1000)
    private String description;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentSeverity severity;

    @Column(name = "reported_time", nullable = false)
    private LocalDateTime reportedTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus status = IncidentStatus.REPORTED;

    @Column(name = "assigned_team")
    private String assignedTeam;

    @Column(name = "resolution_notes", length = 2000)
    private String resolutionNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_id")
    private User reportedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_resource_id")
    private EmergencyResource assignedResource;

    @PrePersist
    public void prePersist() {
        if (reportedTime == null) {
            reportedTime = LocalDateTime.now();
        }
    }
}
