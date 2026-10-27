package com.victorvaladez.projectmanagement.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ProjectControllerTest {

    @Test
    void shouldReturnHealthMessage() {
        ProjectService projectService = mock(ProjectService.class);

        ProjectController controller = new ProjectController(projectService);

        String response = controller.health();

        assertEquals("Project API is running!", response);
    }
}
