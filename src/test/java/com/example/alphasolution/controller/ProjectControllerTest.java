package com.example.alphasolution.controller;

import com.example.alphasolution.Service.ProjectService;
import com.example.alphasolution.Service.UserService;
import com.example.alphasolution.model.ProjectModel;
import com.example.alphasolution.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectController projectController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    void showDashboard() throws Exception {
        int userId = 1;
        String username = "admin";
        UserModel userModel = new UserModel();
        userModel.setId(userId);
        userModel.setUsername(username);
        ArrayList<ProjectModel> projects = new ArrayList<>();
        projects.add(new ProjectModel(1,"Test","Test",10,false));

        when(userService.findUsernamebyUserid(userId)).thenReturn(username);
        when(projectService.projectList(userId)).thenReturn(projects);
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboardview").param("userId", String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("dashboard"))
                .andExpect(MockMvcResultMatchers.model().attribute("projects", projects))
                .andExpect(MockMvcResultMatchers.model().attribute("userId", userId))
                .andExpect(MockMvcResultMatchers.model().attribute("username", username));

        verify(userService,times(1)).findUsernamebyUserid(userId);
        verify(projectService,times(1)).projectList(userId);

    }

    @Test
    void addProject() throws Exception {
        int userId = 1;
        String projectName = "Test";
        String projectDescription = "Test";

        doNothing().when(projectService).createProject(projectName, projectDescription, userId);

        mockMvc.perform(MockMvcRequestBuilders.post("/createproject").
                param("userId", String.valueOf(userId)).
                param("projectname", projectName).
                param("description", projectDescription))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/dashboardview?userId=" + userId));
        verify(projectService,times(1)).createProject(projectName, projectDescription, userId);

    }

    @Test
    void createProject() throws Exception {
        int userId = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/createprojectview").param("userId", String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("createproject"))
                .andExpect(MockMvcResultMatchers.model().attribute("userId",userId));
    }

    @Test
    void editProject() {
    }

    @Test
    void editProjectSucces() {
    }

    @Test
    void deleteProject() {
    }

    @Test
    void searchProject() {
    }
}