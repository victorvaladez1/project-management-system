package com.victorvaladez.projectmanagement.project;

import com.victorvaladez.projectmanagement.user.User;
import com.victorvaladez.projectmanagement.user.UserRepository;
import com.victorvaladez.projectmanagement.user.UserRepository;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void shouldCreateProjectForExistingUser() {
        User owner = new User("John Doe", "john@example.com", "hashedPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));

        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project result = projectService.createProject("Website Redesign", "Redesign the company website", 1L);

        assertEquals("Website Redesign", result.getName());
        assertEquals("Redesign the company website", result.getDescription());
        assertSame(owner, result.getOwner());

        verify(userRepository).findById(1L);
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void shouldThrowExceptionWhenOwnerDoesNotExist() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> projectService.createProject("Website Redesign", "Redesign the company website", 999L));

        assertEquals("User not found", exception.getMessage());

        verify(userRepository).findById(999L);
        verify(projectRepository, never()).save(any(Project.class));
    }
}

