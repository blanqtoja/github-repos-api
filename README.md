# github-repos-api
Solution for displaying user's GitHub repositories form api.github.com. Github-repos-api fetch repositories, filtering out forks and give branch details.

## Features
- Fetches non-fork repositories for a given GitHub username
- Displays repository name, owner login, and branches
- Shows last commit SHA for each branch and its name
- Error handling when user not existing

## Tech Stack
- Java 21
- Spring Boot 3.5.4

## API endpoint
Get user repositories:
GET /api/github/username?username={username}

Example response:
```json
[
  {
    "name": "0daysoflaptops.github.io",
    "ownerLogin": "mojombo",
    "branches": [
      {
        "name": "gh-pages",
        "lastCommitSHA": "52ea8096a319125a2118393894a421de7893d1d4"
      }
    ]
  }
]
```

Error response - user not found:
```json
{
"status": 404,
"message": "User not found"
}
```

## Running the application
1. Clone reposiotry
2. Run with Maven:
   ```bash
    mvn spring-boot:run
   ```
   or build then run:

    ```bash
    mvn clean install
    java -jar target/githubapi-0.0.1-SNAPSHOT.jar
   ```
    
