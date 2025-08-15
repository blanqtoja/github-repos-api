package com.project.githubapi.DTO;

import java.util.List;

public record RepositoryDTO(
        String name,
        String ownerLogin,
        List<BranchDTO> branches
) {}
