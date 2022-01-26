package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
class ClassControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void listStudents() throws Exception {
        this.mockMvc.perform(get("/students/1"))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.errorMessages[0]", equalTo("An Authentication object was not found in the SecurityContext")));
    }

    @Test
    @WithMockUser(roles = {"TEACHER111"})
    void listStudentsWithInvalidRole() throws Exception {
        this.mockMvc.perform(get("/students/1"))
            .andDo(print())
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.errorMessages[0]", equalTo("Access is denied")));
    }

    @Test
    @WithMockUser(roles = {"TEACHER"})
    void listStudentsWithAuth() throws Exception {
        this.mockMvc.perform(get("/students/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.students[0].name", equalTo("Dongchen Zhang")));
    }
}