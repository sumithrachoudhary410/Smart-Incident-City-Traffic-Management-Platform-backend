package com.smartcity.platform.intersection;

import jakarta.validation.constraints.NotBlank;

public record IntersectionRequest(
        @NotBlank(message = "Intersection code is required") String code,
        @NotBlank(message = "Intersection name is required") String name,
        Double latitude,
        Double longitude
) {
}
