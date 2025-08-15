package com.project.githubapi;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@WireMockTest(httpPort = 8089)
class GithubapiApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnNonForkReposWithBranches() {
        // given
        final String username = "testuser";
        
        stubFor(get(urlEqualTo("/users/" + username + "/repos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"name\":\"test-repo\",\"owner\":{\"login\":\"testuser\"},\"fork\":false}]"
                        )));
        
        stubFor(get(urlEqualTo("/repos/testuser/test-repo/branches"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"name\":\"main\",\"commit\":{\"sha\":\"abc123\"}}]"
                        )));

        // when & then
        webTestClient.get()
                .uri("/api/github/username?username=" + username)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo("test-repo")
                .jsonPath("$[0].ownerLogin").isEqualTo("testuser")
                .jsonPath("$[0].branches[0].name").isEqualTo("main")
                .jsonPath("$[0].branches[0].lastCommitSHA").isEqualTo("abc123");
    }

    @Test
    void shouldReturn404WhenUserNotFound() {
        // given
        final String username = "nonexistentuser";
        
        stubFor(get(urlEqualTo("/users/" + username + "/repos"))
                .willReturn(aResponse().withStatus(404)));

        // when & then
        webTestClient.get()
                .uri("/api/github/username?username=" + username)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.message").isEqualTo("User not found");
    }
}