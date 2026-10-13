package com.smartcity.platform.traffic;

import com.smartcity.platform.intersection.Intersection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roads")
@Getter
@Setter
@NoArgsConstructor
public class Road {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intersection_id", nullable = false)
    private Intersection intersection;

    private Double distanceKm;
    private Integer vehicleCount;
    private Double densityScore;
    private String congestionLevel;
}
