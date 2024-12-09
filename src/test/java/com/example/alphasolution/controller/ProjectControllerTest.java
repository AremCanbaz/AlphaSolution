package com.example.alphasolution.controller;

import com.example.alphasolution.Service.ProjectService;
import com.example.alphasolution.model.ProjectModel;
import jakarta.servlet.http.HttpSession;
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
        int userId = 13;

        String username = "admin";
        ArrayList<ProjectModel> projects = new ArrayList<>();
        projects.add(new ProjectModel(1,"Test","Test",10,false,1));

        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("userId")).thenReturn(userId);
        when(mockSession.getAttribute("username")).thenReturn(username);

        when(projectService.projectList(userId)).thenReturn(projects);

        mockMvc.perform(MockMvcRequestBuilders.get("/dashboardview").param("userId", String.valueOf(userId))
                        .sessionAttr("userId", userId)
                        .sessionAttr("username", username))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("dashboard"))
                .andExpect(MockMvcResultMatchers.model().attribute("projects", projects))
                .andExpect(MockMvcResultMatchers.model().attribute("userId", userId))
                .andExpect(MockMvcResultMatchers.model().attribute("username", username));


        verify(projectService,times(1)).projectList(userId);

    }

    @Test
    void addProject() throws Exception {
        int userId = 1;
        String projectName = "Test";
        String projectDescription = "Test";
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("userId")).thenReturn(userId);


        doNothing().when(projectService).createProject(projectName, projectDescription, userId);

        mockMvc.perform(MockMvcRequestBuilders.post("/createproject").
                param("userId", String.valueOf(userId)).
                param("projectname", projectName).
                param("description", projectDescription).sessionAttr("userId", userId))
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
    void editProject() throws Exception {
        int userId = 1;
        int projectId = 1;
        ProjectModel projectModel = new ProjectModel(projectId,"Test","Test",10,false,2);
        when(projectService.getProjectById(projectId)).thenReturn(projectModel);
        mockMvc.perform(MockMvcRequestBuilders.get("/editproject")
                .param("projectid", String.valueOf(projectId))
                .param("userid",String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit-project"))
                .andExpect(MockMvcResultMatchers.model().attribute("userId",userId))
                .andExpect(MockMvcResultMatchers.model().attribute("projectid",projectId))
                .andExpect(MockMvcResultMatchers.model().attribute("project",projectModel));

        verify(projectService,times(1)).getProjectById(projectId);
    }

    @Test
    void editProjectSucces() throws Exception {
        int userId = 1;
        int projectId = 1;
        String projectName = "Test";
        String projectDescription = "Test";
        doNothing().when(projectService).editProject(projectName, projectDescription, projectId);

        mockMvc.perform(MockMvcRequestBuilders.post("/editprojectSucces")
                .param("projectName", projectName)
                .param("description", projectDescription)
                .param("projectid", String.valueOf(projectId))
                .param("userid", String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/dashboardview?userId=" + userId));


        verify(projectService,times(1)).editProject(projectName, projectDescription, projectId);
    }

    @Test
    void deleteProject() throws Exception {
        int projectId = 1;
        int userId = 1;
        doNothing().when(projectService).deleteProject(projectId);
        mockMvc.perform(MockMvcRequestBuilders.post("/deleteproject")
                .param("projectid", String.valueOf(projectId))
                .param("userid", String.valueOf(userId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/dashboardview?userId="+userId));
        verify(projectService,times(1)).deleteProject(projectId);
    }

    @Test
    void searchProject() {
    }
}