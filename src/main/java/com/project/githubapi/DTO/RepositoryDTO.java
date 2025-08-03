package com.project.githubapi.DTO;

import java.util.List;

public class RepositoryDTO {
    private String name;
    private String ownerLogin;
    private List<BranchDTO> branches;

    public RepositoryDTO(String name, String ownerLogin, List<BranchDTO> branches) {
        this.name = name;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BranchDTO> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchDTO> branches) {
        this.branches = branches;
    }
}
