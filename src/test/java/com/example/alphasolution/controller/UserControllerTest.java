package com.example.alphasolution.controller;

import com.example.alphasolution.Service.UserService;
import com.example.alphasolution.model.UserModel;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    @Test
    void showLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));

    }

    @Test
    void showlogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    void processLoginForm() throws Exception {
        String username = "username";
        String password = "password";
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setUsername(username);
        userModel.setPassword(password);

        when(userService.authenticate(username, password)).thenReturn(userModel);

        mockMvc.perform(MockMvcRequestBuilders.post("/loginSucces")
                        .param("username", username)
                .param("password",password))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/dashboardview?userId="+ userModel.getId()));

        verify(userService, times(1)).authenticate(username,password);
    }

    @Test
    void showCreateUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/createusersite"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("createUser"));
    }

    @Test
    void processCreateUserForm() throws Exception {
        String username = "username";
        String password = "password";
        String email = "ac@email.dk";
        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        userModel.setPassword(password);
        userModel.setEmail(email);
        doNothing().when(userService).saveUser(userModel);

        mockMvc.perform(MockMvcRequestBuilders.post("/createUser")
                .param("username",username)
                .param("password",password)
                .param("email",email))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));

    }

    @Test
    void processLogout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"));
    }
}