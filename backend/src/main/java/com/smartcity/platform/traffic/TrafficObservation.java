package com.smartcity.platform.traffic;

import com.smartcity.platform.intersection.Intersection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "traffic_observations")
@Getter
@Setter
@NoArgsConstructor
public class TrafficObservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intersection_id", nullable = false)
    private Intersection intersection;

    @Column(nullable = false)
    private Integer vehicleCount;

    @Column(name = "density_score")
    private Double densityScore;

    @Column(name = "congestion_level")
    private String congestionLevel;

    @Column(name = "observed_at", nullable = false)
    private LocalDateTime observedAt;
}
