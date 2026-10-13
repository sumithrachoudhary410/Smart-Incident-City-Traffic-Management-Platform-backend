package com.smartcity.platform.intersection;

import com.smartcity.platform.traffic.Road;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "intersections")
@Getter
@Setter
@NoArgsConstructor
public class Intersection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private Double latitude;
    private Double longitude;

    @Column(name = "congestion_level")
    @Enumerated(EnumType.STRING)
    private TrafficCongestion congestionLevel = TrafficCongestion.LOW;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "intersection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Road> roads = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
