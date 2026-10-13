package com.smartcity.platform.emergency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResourceRequest(
        @NotNull(message = "Resource type is required") EmergencyResourceType type,
        @NotBlank(message = "Resource name is required") String name,
        String currentLocation,
        Double latitude,
        Double longitude,
        ResourceStatus status
) {
}
