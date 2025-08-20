package com.example.UserDept.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import config.TestcontainersConfig;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Abstract base class for integration tests.
 *
 * This class configures the test environment with:
 * - Spring Boot context with a random port
 * - Testcontainers for PostgreSQL (via {@link TestcontainersConfig})
 * - MockMvc for HTTP request testing
 * - Automatic JSON serialization/deserialization with Jackson
 *
 * It also provides utility methods to simplify writing integration tests,
 * such as performing GET/POST/DELETE requests and validating responses.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Import(TestcontainersConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    protected final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Perform a POST request with a given request body and expect a specific status.
     */
    protected <T> T performPostRequest(String path, Object object, Class<T> responseType, ResultMatcher expectedStatus)
            throws Exception {
        MvcResult mvcResult = getResultActions(path, object)
                .andExpect(expectedStatus)
                .andReturn();
        return convertStringToClass(mvcResult.getResponse().getContentAsString(), responseType);
    }

    /**
     * Perform a GET request.
     */
    protected ResultActions performGetRequest(String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(path)
                .contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Perform a DELETE request.
     */
    protected ResultActions performDeleteRequest(String path) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(path)
                .contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Internal helper to build and execute a POST request.
     */
    private ResultActions getResultActions(String path, Object object) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(object)));
    }

    /**
     * Convert a JSON string into a Java object.
     */
    private <T> T convertStringToClass(String jsonString, Class<T> responseType) throws JsonProcessingException {
        return mapper.readValue(jsonString, responseType);
    }

    /**
     * Convert an object into JSON string (used in tests).
     */
    protected String write(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
