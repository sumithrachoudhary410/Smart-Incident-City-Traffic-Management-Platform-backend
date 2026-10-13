package com.smartcity.platform.emergency;

import com.smartcity.platform.common.BadRequestException;
import com.smartcity.platform.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmergencyResourceService {

    private final EmergencyResourceRepository emergencyResourceRepository;

    public List<EmergencyResource> findAll() {
        return emergencyResourceRepository.findAllByOrderByNameAsc();
    }

    public List<EmergencyResource> findAvailable() {
        return emergencyResourceRepository.findByStatus(ResourceStatus.AVAILABLE);
    }

    @Transactional
    public EmergencyResource create(ResourceRequest request) {
        EmergencyResource resource = new EmergencyResource();
        resource.setType(request.type());
        resource.setName(request.name());
        resource.setCurrentLocation(request.currentLocation());
        resource.setLatitude(request.latitude());
        resource.setLongitude(request.longitude());
        resource.setStatus(request.status() == null ? ResourceStatus.AVAILABLE : request.status());
        return emergencyResourceRepository.save(resource);
    }

    public List<String> calculateShortestPath(String source, String target, Map<String, Map<String, Double>> graph) {
        if (graph == null || graph.isEmpty()) {
            return List.of(source, target);
        }

        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::distance));
        Set<String> visited = new HashSet<>();

        for (String node : graph.keySet()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(source, 0.0);
        queue.add(new NodeDistance(source, 0.0));

        while (!queue.isEmpty()) {
            NodeDistance current = queue.poll();
            if (visited.contains(current.node())) {
                continue;
            }
            visited.add(current.node());

            if (current.node().equals(target)) {
                break;
            }

            for (Map.Entry<String, Double> neighbor : graph.getOrDefault(current.node(), Map.of()).entrySet()) {
                double candidate = distances.get(current.node()) + neighbor.getValue();
                if (candidate < distances.getOrDefault(neighbor.getKey(), Double.POSITIVE_INFINITY)) {
                    distances.put(neighbor.getKey(), candidate);
                    previous.put(neighbor.getKey(), current.node());
                    queue.add(new NodeDistance(neighbor.getKey(), candidate));
                }
            }
        }

        if (!distances.containsKey(target) || distances.get(target) == Double.POSITIVE_INFINITY) {
            throw new BadRequestException("No route found between source and target");
        }

        LinkedList<String> path = new LinkedList<>();
        String cursor = target;
        while (cursor != null) {
            path.addFirst(cursor);
            cursor = previous.get(cursor);
        }
        return path;
    }

    public EmergencyResource findById(Long id) {
        return emergencyResourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency resource not found with id: " + id));
    }

    public List<EmergencyResource> findSuitableResources(EmergencyResourceType type) {
        return emergencyResourceRepository.findByStatus(ResourceStatus.AVAILABLE).stream()
                .filter(resource -> resource.getType() == type)
                .toList();
    }

    private record NodeDistance(String node, double distance) {}
}
