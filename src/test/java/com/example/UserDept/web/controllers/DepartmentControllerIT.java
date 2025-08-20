package com.example.UserDept.web.controllers;

import com.example.UserDept.utils.PageResponse;
import com.example.UserDept.web.dto.department.DepartmentDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springdoc.api.ErrorMessage;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class DepartmentControllerIT extends AbstractIntegrationTest {

    @BeforeEach
    void setup() throws Exception {
        performPostRequest(
                "/departments",
                Map.of("name", "HR"),
                DepartmentDto.class,
                status().isCreated()
        );
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void  create_whenValid_then201AndReturnDepartmentDto() throws Exception {
        var body = Map.of("name", "Finance");

        var deptDto = performPostRequest(
                "/departments",
                body,
                DepartmentDto.class,
                status().isCreated()
        );


        assertThat(deptDto.getName()).isEqualTo("Finance");
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturnConflictWhenDepartmentNameAlreadyExists() throws Exception {
        var body = Map.of("name", "HR");

        performPostRequest(
                "/departments",
                body,
                ErrorMessage.class,
                status().isConflict());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void create_whenInvalid_then422() throws Exception {
        var body = Map.of("name", "");

        ErrorMessage responseWithException = performPostRequest(
                "/departments",
                body,
                ErrorMessage.class,
                status().isUnprocessableEntity()
        );

        assertNotNull(responseWithException);
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getAllDepartments_whenCalled_then200AndReturnList() throws Exception {
        performPostRequest("/departments",Map.of("name", "Finance"),  DepartmentDto.class, status().isCreated());

        var result = performGetRequest("/departments")
                .andExpect(status().isOk())
                .andReturn();

        var responseJson = result.getResponse().getContentAsString();

        // wrapper to Page<DepartmentDto>
        var page = mapper.readValue(responseJson, new TypeReference<PageResponse<DepartmentDto>>() {});

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent())
                .extracting(DepartmentDto::getName)
                .containsExactlyInAnyOrder("Finance", "HR");
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getDepartmentById_whenExists_then200AndReturnDepartmentDto() throws Exception {

        var result = performGetRequest("/departments/1")
                .andExpect(status().isOk())
                .andReturn();

        var department = mapper.readValue(result.getResponse().getContentAsString(), DepartmentDto.class);
        assertThat(department.getName()).isEqualTo("HR");
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getDepartmentById_whenNotExists_then404() throws Exception {
        performGetRequest("/departments/999")
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void deleteDepartment_whenExists_then204() throws Exception {
        performDeleteRequest("/departments/1")
                .andExpect(status().isNoContent());

        performGetRequest("/departments/1")
                .andExpect(status().isNotFound());
    }
}

