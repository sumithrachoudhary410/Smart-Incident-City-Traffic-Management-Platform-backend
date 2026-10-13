package com.smartcity.platform.intersection;

import com.smartcity.platform.common.BadRequestException;
import com.smartcity.platform.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IntersectionService {

    private final IntersectionRepository intersectionRepository;

    public List<Intersection> findAll() {
        return intersectionRepository.findAll();
    }

    public Intersection findById(Long id) {
        return intersectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intersection not found with id: " + id));
    }

    @Transactional
    public Intersection create(IntersectionRequest request) {
        if (intersectionRepository.findByCode(request.code()).isPresent()) {
            throw new BadRequestException("Intersection code already exists");
        }

        Intersection intersection = new Intersection();
        intersection.setCode(request.code());
        intersection.setName(request.name());
        intersection.setLatitude(request.latitude());
        intersection.setLongitude(request.longitude());
        intersection.setCongestionLevel(TrafficCongestion.LOW);
        return intersectionRepository.save(intersection);
    }

    @Transactional
    public Intersection update(Long id, IntersectionRequest request) {
        Intersection intersection = findById(id);
        intersection.setName(request.name());
        intersection.setLatitude(request.latitude());
        intersection.setLongitude(request.longitude());
        if (!intersection.getCode().equals(request.code())) {
            if (intersectionRepository.findByCode(request.code()).isPresent()) {
                throw new BadRequestException("Intersection code already exists");
            }
            intersection.setCode(request.code());
        }
        return intersectionRepository.save(intersection);
    }

    @Transactional
    public void delete(Long id) {
        Intersection intersection = findById(id);
        intersectionRepository.delete(intersection);
    }
}
