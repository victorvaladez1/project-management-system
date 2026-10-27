package com.victorvaladez.projectmanagement.project;

import java.time.OffsetDateTime;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        Long ownerId,
        OffsetDateTime createdAt
) {

    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner().getId(),
                project.getCreatedAt()
        );
    }
}
