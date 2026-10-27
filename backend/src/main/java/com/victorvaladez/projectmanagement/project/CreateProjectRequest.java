package com.victorvaladez.projectmanagement.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(
    @NotBlank
    @Size(max = 255)
    String name,
    String description
) {

}