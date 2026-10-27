package com.victorvaladez.projectmanagement.project;

import com.victorvaladez.projectmanagement.user.User;
import com.victorvaladez.projectmanagement.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Project createProject(String name, String description, Long ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Project project = new Project(name, description, owner);

        return projectRepository.save(project);
    }
}