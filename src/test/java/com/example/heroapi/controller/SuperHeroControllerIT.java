package com.example.heroapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.example.heroapi.AbstractSuperHeroTest;
import com.example.heroapi.configuration.DockerComposeTestConfig;
import com.example.heroapi.dto.SuperHeroDTO;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = { DockerComposeTestConfig.Initializer.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperHeroControllerIT extends AbstractSuperHeroTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/superheroes";
    }

    @Test
    void testSaveAndGetDeleteSuperHero() {

        // given
        SuperHeroDTO testHeroDTO = createTestHeroDTO(0, "Superman1");

        // when

        // save
        ResponseEntity<SuperHeroDTO> saveResponse = restTemplate.postForEntity(baseUrl, testHeroDTO,
                SuperHeroDTO.class);

        // then
        assertTrue(saveResponse.getStatusCode().is2xxSuccessful(), "Response should be successful");
        SuperHeroDTO savedHeroDTO = saveResponse.getBody();
        assertNotNull(savedHeroDTO, "Saved hero should not be null");
        assertTrue(savedHeroDTO.getId() > 0, "Id should be assigned to a saved SuperHero");

        // get
        ResponseEntity<SuperHeroDTO> getResponse = restTemplate.getForEntity(baseUrl + "/" + savedHeroDTO.getId(),
                SuperHeroDTO.class);
        assertTrue(getResponse.getStatusCode().is2xxSuccessful(), "Response should be successful");
        assertEquals(savedHeroDTO, getResponse.getBody(), "Saved hero should match retrieved hero");

        // delete
        restTemplate.delete(baseUrl + "/" + savedHeroDTO.getId());
        ResponseEntity<Void> notFoundResponse = restTemplate.getForEntity(baseUrl + "/" + savedHeroDTO.getId(),
                Void.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), notFoundResponse.getStatusCode().value(),
                "Response should not be found");
    }

    @Test
    void testGetAllSuperHeroes() {

        ResponseEntity<List<SuperHeroDTO>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        assertTrue(response.getStatusCode().is2xxSuccessful(), "Response should be successful");
        assertTrue(!response.getBody().isEmpty(), "Response body should not be empty");
        assertEquals(2, response.getBody().size(), "Response body should have 2 elements");
        assertEquals("Clark Kent", response.getBody().get(0).getName(), "First element name should be 'Clark Kent'");
        assertEquals("Tony Stark", response.getBody().get(1).getName(), "First element name should be 'Tony Stark'");
    }

    @Test
    void testSearchSuperHeroesByAssociations() {
        ResponseEntity<List<SuperHeroDTO>> response = restTemplate.exchange(
                baseUrl + "/search?association=avengers&&association=thanos", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(1, response.getBody().size(), "Response body should have 1 element");
        assertEquals("Tony Stark", response.getBody().get(0).getName(), "Name should be 'Tony Stark'");
    }

    // Other tests can be added here but we don't need to repeat all the same tests as
    // we did in the webMvc unit test of the same controller
}
