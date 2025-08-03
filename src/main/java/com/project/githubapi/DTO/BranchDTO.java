package com.project.githubapi.DTO;

public class BranchDTO {
    private String name;
    private String lastCommitSHA;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastCommitSHA() {
        return lastCommitSHA;
    }

    public void setLastCommitSHA(String lastCommitSHA) {
        this.lastCommitSHA = lastCommitSHA;
    }

    public BranchDTO(String name, String lastCommitSHA) {
        this.name = name;
        this.lastCommitSHA = lastCommitSHA;
    }
}
