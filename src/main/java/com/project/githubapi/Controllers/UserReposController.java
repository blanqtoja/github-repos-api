package com.project.githubapi.Controllers;

import com.project.githubapi.DTO.RepositoryDTO;
import com.project.githubapi.Services.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class UserReposController {
    private final GithubService githubService;

    public UserReposController(final GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("username")
    public List<RepositoryDTO> getRepos(@RequestParam final String username) {
        return githubService.getRepos(username);
    }
}
