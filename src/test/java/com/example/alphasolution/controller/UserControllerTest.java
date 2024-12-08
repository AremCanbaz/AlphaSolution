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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

    }

    @Test
    void showlogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
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

        mockMvc.perform(post("/loginSucces")
                        .param("username", username)
                .param("password",password))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/dashboardview?userId="+ userModel.getId()));

        verify(userService, times(1)).authenticate(username,password);
    }

    @Test
    void showCreateUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/createusersite"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-User"));
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

        when(userService.emailExists(email)).thenReturn(false);
        when(userService.usernameExists(username)).thenReturn(false);
        doNothing().when(userService).saveUser(userModel);

        mockMvc.perform(post("/createUser")
                .param("username",username)
                .param("password",password)
                .param("email",email))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
        verify(userService, times(1)).saveUser(argThat(user ->
                user.getUsername().equals(username) &&
                        user.getPassword().equals(password) &&
                        user.getEmail().equals(email)
        ));
        verify(userService, times(1)).emailExists(email);
        verify(userService, times(1)).usernameExists(username);

    }
    @Test
    void processCreateUserForm_ReturnErrorIfUsernameExist() throws Exception {
        String username = "username";
        String password = "password";
        String email = "ac@email.dk";


        when(userService.usernameExists(username)).thenReturn(true);

        mockMvc.perform(post("/createUser")
                        .param("username", username)
                        .param("password", password)
                        .param("email", email))
                .andExpect(status().isOk())  // 200 OK
                .andExpect(view().name("create-User"))
                .andExpect(model().attributeExists("error1"))  // Fejlmeddelelse for brugernavn
                .andExpect(model().attribute("error1", "Dette brugernavn findes allerede"));
        verify(userService,times(2)).usernameExists(username);
    }
    @Test
    void processCreateUserForm_ReturnErrorIfEmailExist() throws Exception {
        String username = "username";
        String password = "password";
        String email = "ac@email.dk";


        when(userService.emailExists(email)).thenReturn(true);

        mockMvc.perform(post("/createUser")
                        .param("username", username)
                        .param("password", password)
                        .param("email", email))
                .andExpect(status().isOk())  // 200 OK
                .andExpect(view().name("create-User"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Denne email adresse findes allerede"));
        verify(userService,times(2)).emailExists(email);
    }

    @Test
    void processLogout() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }
}