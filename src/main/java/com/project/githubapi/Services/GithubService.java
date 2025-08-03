package com.project.githubapi.Services;

import com.project.githubapi.DTO.BranchDTO;
import com.project.githubapi.DTO.RepositoryDTO;
import com.project.githubapi.Exceptions.UserNotFoundException;
import com.project.githubapi.GithubMapping.BranchResponse;
import com.project.githubapi.GithubMapping.RepositoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<RepositoryDTO> getRepos(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        ResponseEntity<RepositoryResponse[]> response;

        try {
            response = restTemplate.getForEntity(url, RepositoryResponse[].class);
        }
        catch (HttpClientErrorException.NotFound exception) {
            throw new UserNotFoundException("User not found");
        }

        if (response.getBody() == null || response.getBody().length == 0) {
            return List.of();
        }

        return Arrays.stream(response.getBody())
                .filter(r -> !r.isFork()) // filter out forks
                .map(r -> {
                    // for each repo fetch branch details
                    List<BranchDTO> branches = fetchBranches(r.getName(), r.getOwner().getLogin());
                    return new RepositoryDTO(r.getName(), r.getOwner().getLogin(), branches);
                })
                .collect(Collectors.toList());
    }

    private List<BranchDTO> fetchBranches(String repo, String owner) {
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/branches";
        ResponseEntity<BranchResponse[]> response = restTemplate.getForEntity(url, BranchResponse[].class);

        if (response.getBody() == null || response.getBody().length == 0) {
            return List.of();
        }
        // return array of branches mapped to DTO
        return Arrays.stream(response.getBody())
                .map(b -> new BranchDTO(b.getName(), b.getCommit().getSha()))
                .collect(Collectors.toList());
    }
}
