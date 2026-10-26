package com.victorvaladez.projectmanagement.project;

import com.victorvaladez.projectmanagement.user.User;
import com.victorvaladez.projectmanagement.user.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class ProjectRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17");

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void shouldFindProjectsByOwnerId() {

        User owner = new User("John Doe", "john@example.com", "hashedPassword");

        owner = userRepository.save(owner);

        Project project = new Project("Project Management System", "A full-stack project management application", owner);

        projectRepository.save(project);

        List<Project> projects = projectRepository.findByOwnerId(owner.getId());

        assertEquals(1, projects.size());
        assertEquals("Project Management System", projects.get(0).getName());
        assertEquals(owner.getId(), projects.get(0).getOwner().getId());
    }
}