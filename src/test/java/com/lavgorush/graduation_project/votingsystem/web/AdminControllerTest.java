package com.lavgorush.graduation_project.votingsystem.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void loginWithValidAdminThenAuthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
                .user("admin@gmail.com")
                .password("admin");
        mockMvc.perform(login).andExpect(authenticated().withUsername("admin@gmail.com"));
    }
}
