package com.project.githubapi.GithubMapping;

public record RepositoryResponse(
        String name,
        OwnerResponse owner,
        boolean fork
) {
    public boolean isFork() {
        return fork;
    }
}
