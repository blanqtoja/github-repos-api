package com.project.githubapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GithubApiIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void shouldReturnNonForkReposWithBranches() throws JsonProcessingException {
		// given: owner login
		// it is guaranteed to have at least one repository which is not fork
		String username = "mojombo";

		// when: making api request to our endpoint
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/api/github/username?username=" + username, String.class
		);

		// then: verify buisness requirepents
		assertEquals(HttpStatus.OK, response.getStatusCode());
		String body = response.getBody();
		// check if body exist
		assertNotNull(body);

		// parse string to
		ObjectMapper objectMapper = new ObjectMapper();
		List<Map<String, Object>> repos = objectMapper.readValue(body, new TypeReference<>() {});

		// at least one repo
		assertFalse(repos.isEmpty());

		// for each repo check details
		for (Map<String, Object> repo : repos) {
			assertNotNull(repo.get("name"));
			assertEquals(username, repo.get("ownerLogin"));

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> branches = (List<Map<String, Object>>) repo.get("branches");
			assertNotNull(branches);
			assertFalse(branches.isEmpty());

			for (Map<String, Object> branch : branches) {
				assertNotNull(branch.get("name"));
				assertNotNull(branch.get("lastCommitSHA"));
			}
		}
	}
}