package com.smartcity.platform.incident;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IncidentRequest(
        @NotNull(message = "Incident type is required") IncidentType type,
        @NotBlank(message = "Description is required") String description,
        Double latitude,
        Double longitude,
        @NotNull(message = "Severity is required") IncidentSeverity severity
) {
}
