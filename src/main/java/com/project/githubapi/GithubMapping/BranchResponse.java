package com.project.githubapi.GithubMapping;

public class BranchResponse {
    private String name;
    private CommitResponse commit;

    public BranchResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommitResponse getCommit() {
        return commit;
    }

    public void setCommit(CommitResponse commit) {
        this.commit = commit;
    }
}
