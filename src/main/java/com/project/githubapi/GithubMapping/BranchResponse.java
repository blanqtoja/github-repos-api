package com.project.githubapi.GithubMapping;

public record BranchResponse(
        String name,
        CommitResponse commit
) {}
