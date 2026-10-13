package com.smartcity.platform.civicissue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CivicIssueRequest(
        @NotBlank(message = "Issue title is required") String title,
        @NotBlank(message = "Issue description is required") String description,
        @NotNull(message = "Issue category is required") IssueCategory category,
        @NotBlank(message = "Location is required") String location,
        @NotNull(message = "Priority is required") IssuePriority priority,
        String imageUrl,
        String assignedTo,
        Long departmentId
) {
}
