package com.smartcity.platform.traffic;

import com.smartcity.platform.intersection.Intersection;
import com.smartcity.platform.intersection.IntersectionRepository;
import com.smartcity.platform.intersection.TrafficCongestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrafficServiceTest {

    @Mock
    private TrafficObservationRepository trafficObservationRepository;

    @Mock
    private IntersectionRepository intersectionRepository;

    @InjectMocks
    private TrafficService trafficService;

    @Test
    void shouldClassifyHighCongestionForHeavyTraffic() {
        Intersection intersection = new Intersection();
        intersection.setId(1L);
        intersection.setName("Main Junction");
        intersection.setCongestionLevel(TrafficCongestion.LOW);

        when(intersectionRepository.findById(1L)).thenReturn(Optional.of(intersection));
        when(trafficObservationRepository.save(any(TrafficObservation.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(intersectionRepository.save(any(Intersection.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TrafficObservation observation = trafficService.createObservation(1L, 150);

        assertEquals("HIGH", observation.getCongestionLevel());
        assertEquals(TrafficCongestion.HIGH, intersection.getCongestionLevel());
    }
}
