package com.project.githubapi.Controllers;

import com.project.githubapi.Exceptions.UserNotFoundException;
import com.project.githubapi.Services.GithubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class UserReposController {
    private final GithubService githubService;

    public UserReposController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("username")
    public ResponseEntity<?> getRepos(@RequestParam String username) {
        try {
            // return list of users repositories, only non forks
            return ResponseEntity.ok(githubService.getRepos(username));
        }
        catch (UserNotFoundException exception) {
            // if user not found, return status 404 and message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", 404,
                    "message", exception.getMessage()
            ));
        }
    }
}
