package com.project.githubapi.Services;

import com.project.githubapi.DTO.BranchDTO;
import com.project.githubapi.DTO.RepositoryDTO;
import com.project.githubapi.Exceptions.UserNotFoundException;
import com.project.githubapi.GithubMapping.BranchResponse;
import com.project.githubapi.GithubMapping.RepositoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubService {
    private final RestClient restClient;

    public GithubService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.github.com")
                .build();
    }

    public List<RepositoryDTO> getRepos(final String username) {
        try {
            final RepositoryResponse[] repositories = restClient.get()
                    .uri("/users/{username}/repos", username)
                    .retrieve()
                    .body(RepositoryResponse[].class);

            if (repositories == null) {
                return List.of();
            }

            return Arrays.stream(repositories)
                    .filter(repo -> !repo.isFork())
                    .map(repo -> {
                        final List<BranchDTO> branches = fetchBranches(repo.name(), repo.owner().login());
                        return new RepositoryDTO(repo.name(), repo.owner().login(), branches);
                    })
                    .toList();
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not found");
        }
    }

    private List<BranchDTO> fetchBranches(final String repo, final String owner) {
        final BranchResponse[] branches = restClient.get()
                .uri("/repos/{owner}/{repo}/branches", owner, repo)
                .retrieve()
                .body(BranchResponse[].class);

        if (branches == null) {
            return List.of();
        }

        return Arrays.stream(branches)
                .map(branch -> new BranchDTO(branch.name(), branch.commit().sha()))
                .toList();
    }
}
