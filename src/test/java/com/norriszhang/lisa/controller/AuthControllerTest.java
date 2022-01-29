package com.norriszhang.lisa.controller;

import com.norriszhang.lisa.ControllerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPositive() throws Exception {
        this.mockMvc.perform(post("/login").param("username", "lisa").param("password", "password"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.loginUsername", equalTo("lisa")))
            .andExpect(jsonPath("$.role", equalTo("TEACHER")))
            .andExpect(jsonPath("$.token", Matchers.not(Matchers.emptyOrNullString())));
    }
    @Test
    public void testLoginNegative() throws Exception {
        this.mockMvc.perform(post("/login").param("username", "nonexist").param("password", "password"))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.errorMessages[0]", equalTo("Invalid username or password.")));
    }
    @Test
    public void testLoginNegativeWrongPassword() throws Exception {
        this.mockMvc.perform(post("/login").param("username", "lisa").param("password", "wrongpassword"))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.errorMessages[0]", equalTo("Invalid username or password.")));
    }
}
