package com.example.alphasolution.controller;

import com.example.alphasolution.Service.ProjectService;
import com.example.alphasolution.Service.SubTaskService;
import com.example.alphasolution.model.SubTaskModel;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
class SubTaskControllerTest {
    @Mock
    private SubTaskService subTaskService;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private SubTaskController subTaskController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialiserer mocks
        mockMvc = MockMvcBuilders.standaloneSetup(subTaskController).build();
    }

    @Test
    void subtaskview() throws Exception {
        int projectId = 1;
        ArrayList<SubTaskModel> subtasks = new ArrayList<>();
        subtasks.add(new SubTaskModel(projectId, "Subtask 1", "Subtask 2"));
        String projectName = "Project Name";
        int userId = 1;

        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("userId")).thenReturn(userId);

        when(subTaskService.getAllSubTasks(projectId)).thenReturn(subtasks);
        when(projectService.getProjectName(projectId)).thenReturn(projectName);


        mockMvc.perform(MockMvcRequestBuilders.get("/subtaskview").param("projectid", String.valueOf(projectId))
                        .sessionAttr("userId", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("subtask-view"))
                .andExpect(MockMvcResultMatchers.model().attribute("subtasks",subtasks))
                .andExpect(MockMvcResultMatchers.model().attribute("projectid",projectId))
                .andExpect(MockMvcResultMatchers.model().attribute("projectname",projectName))
                .andExpect(MockMvcResultMatchers.model().attribute("userId",userId));
        verify(subTaskService, times(1)).getAllSubTasks(projectId);
        verify(projectService, times(1)).getProjectName(projectId);
    }

    @Test
    void deleteSubTask() throws Exception {
        int subTaskId = 1;
        int projectId = 2;
        int userId = 3;

        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("userId")).thenReturn(userId);

        doNothing().when(subTaskService).deleteSubTask(subTaskId);

        mockMvc.perform(MockMvcRequestBuilders.post("/deleteSubTask")
                        .param("subtaskId", String.valueOf(subTaskId))
                        .param("projectid", String.valueOf(projectId))
                        .sessionAttr("userId", userId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/subtaskview?projectid=" + projectId));

        verify(subTaskService, times(1)).deleteSubTask(subTaskId);
    }

    @Test
    void createsubtaskview() throws Exception {
        int projectId = 1;
        int userId = 2;

        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("userId")).thenReturn(userId);

        mockMvc.perform(MockMvcRequestBuilders.get("/createSubTaskView")
                        .param("projectid", String.valueOf(projectId))
                        .sessionAttr("userId", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("createsubtask"))
                .andExpect(MockMvcResultMatchers.model().attribute("projectid", projectId));
    }

    @Test
    void createsubtask() throws Exception {
        int projectId = 1;
        String subtaskName = "New Task";
        String subtaskDescription = "Task Description";
        int userId = 2;
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("userId")).thenReturn(userId);

        doNothing().when(subTaskService).createSubTask(projectId, subtaskName, subtaskDescription);

        mockMvc.perform(MockMvcRequestBuilders.post("/createsubtaskaction")
                        .param("projectid", String.valueOf(projectId))
                        .param("subtaskname", subtaskName)
                        .param("subtaskdescription", subtaskDescription)
                        .sessionAttr("userId", userId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/subtaskview?projectid=" + projectId));

        verify(subTaskService, times(1)).createSubTask(projectId, subtaskName, subtaskDescription);
    }

    @Test
    void editProject() throws Exception {
        int subtaskid = 1;
        int projectid = 2;
        int userId = 3;

        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("userId")).thenReturn(userId);
        SubTaskModel subTaskModel = new SubTaskModel(subtaskid, "Task1", "Description1", projectid);

        assertNotNull(subTaskModel, "Mocked SubTaskModel is null"); // Tilføjet kontrol

        when(subTaskService.findSubTaskById(subtaskid)).thenReturn(subTaskModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/editsubtask")
                        .param("subtaskid", String.valueOf(subtaskid))
                        .param("projectid", String.valueOf(projectid))
                        .sessionAttr("userId", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit-subtask"))
                .andExpect(MockMvcResultMatchers.model().attribute("subtaskid", subtaskid))
                .andExpect(MockMvcResultMatchers.model().attribute("projectid", projectid))
                .andExpect(MockMvcResultMatchers.model().attribute("subtask", subTaskModel));

        verify(subTaskService, times(1)).findSubTaskById(subtaskid);
    }
    @Test
    void editProjectSucces() throws Exception {
        int subTaskId = 1;
        int projectId = 2;
        int userId = 3;
        String subTaskName = "Updated Task";
        String subTaskDescription = "Updated Description";

        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("userId")).thenReturn(userId);

        doNothing().when(subTaskService).editSubTask(subTaskId, subTaskName, subTaskDescription);

        mockMvc.perform(MockMvcRequestBuilders.post("/editSubTaskSucces")
                        .param("subTaskId", String.valueOf(subTaskId))
                        .param("projectId", String.valueOf(projectId))
                        .param("subTaskName", subTaskName)
                        .param("subTaskDescription", subTaskDescription)
                        .sessionAttr("userId", userId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/subtaskview?projectid=" + projectId));

        verify(subTaskService, times(1)).editSubTask(subTaskId, subTaskName, subTaskDescription);
    }
}