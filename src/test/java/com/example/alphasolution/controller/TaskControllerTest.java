package com.example.alphasolution.controller;

import com.example.alphasolution.Service.ProjectService;
import com.example.alphasolution.Service.SubTaskService;
import com.example.alphasolution.Service.TaskService;
import com.example.alphasolution.model.SubTaskModel;
import com.example.alphasolution.model.TaskModel;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;
    @Mock
    private SubTaskService subTaskService;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private TaskController taskController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void subtaskview() throws Exception {
        int subtaskId = 1;
        int projectId = 4;
        String subtaskName = "subtaskName";
        int userId = 123;

        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("userId")).thenReturn(userId);

        SubTaskModel subTaskModelTest = new SubTaskModel();
        subTaskModelTest.setProjectid(projectId);
        subTaskModelTest.setSubTaskId(subtaskId);
        subTaskModelTest.setSubTaskName(subtaskName);
        subTaskModelTest.setSubTaskTime(5);

        ArrayList<TaskModel> tasksTest = new ArrayList<>();
        tasksTest.add(new TaskModel(1,subtaskId,"test","testDescription",2,false));
        tasksTest.add(new TaskModel(2,subtaskId,"test","testDescription",3,false));

        when(taskService.getTasks(subtaskId)).thenReturn(tasksTest);
        when(subTaskService.getSubtaskName(subtaskId)).thenReturn(subtaskName);
        doNothing().when(subTaskService).getTotalHoursForSubTask(subtaskId);
        doNothing().when(taskService).updateSubTaskToTrueIfAllTaskAreDone(subtaskId);
        when(taskService.getProjectIdBySubtaskId(subtaskId)).thenReturn(projectId);

        mockMvc.perform(MockMvcRequestBuilders.get("/taskview").param("subtaskid", String.valueOf(subtaskId))
                        .sessionAttr("userId", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("task-view"))
                .andExpect(MockMvcResultMatchers.model().attribute("tasks", tasksTest))
                .andExpect(MockMvcResultMatchers.model().attribute("subtaskid", subtaskId))
                .andExpect(MockMvcResultMatchers.model().attribute("subtaskname", subtaskName))
                .andExpect(MockMvcResultMatchers.model().attribute("projectid", projectId));

        verify(taskService, times(1)).getTasks(subtaskId);
        verify(subTaskService, times(1)).getSubtaskName(subtaskId);
        verify(subTaskService, times(1)).getTotalHoursForSubTask(subtaskId);
        verify(taskService,times(1)).updateSubTaskToTrueIfAllTaskAreDone(subtaskId);
        verify(taskService, times(1)).getProjectIdBySubtaskId(subtaskId);


    }

    @Test
    void opretopgaveview() throws Exception {
        int subtaskId = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/opretopgaveview").param("subtaskid", String.valueOf(subtaskId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("createtask"))
                .andExpect(MockMvcResultMatchers.model().attribute("subtaskid",subtaskId));
    }

    @Test
    void opretopgave() throws Exception {
        int subtaskId = 1;
        String taskName = "taskName";
        String taskDescription = "taskDescription";
        int timeTest = 4;

        doNothing().when(taskService).createtask(subtaskId, taskName, taskDescription, timeTest);

        mockMvc.perform(MockMvcRequestBuilders.post("/opretopgaveaction")
                .param("subtaskid", String.valueOf(subtaskId))
                .param("taskname",taskName)
                .param("taskdescription",taskDescription)
                .param("time", String.valueOf(timeTest)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/taskview?subtaskid="+subtaskId));

        verify(taskService,times(1)).createtask(subtaskId, taskName, taskDescription, timeTest);

    }

    @Test
    void deletetask() throws Exception {
        int subtaskId = 1;
        int taskId = 2;

        doNothing().when(taskService).deletetask(taskId);

        mockMvc.perform(MockMvcRequestBuilders.post("/deletetask")
                .param("subtaskid", String.valueOf(subtaskId))
                .param("taskid", String.valueOf(taskId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/taskview?subtaskid="+subtaskId));

        verify(taskService,times(1)).deletetask(taskId);

    }

    @Test
    void edittask() throws Exception {
        int subtaskId = 1;
        int taskId = 2;
        TaskModel taskModel = new TaskModel(taskId,subtaskId,"test","testDescription",2,false);

        when(taskService.getTask(taskId)).thenReturn(taskModel);
        mockMvc.perform(MockMvcRequestBuilders.get("/edittask")
                .param("taskId", String.valueOf(taskId))
                .param("subtaskid", String.valueOf(subtaskId)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit-task"))
                .andExpect(MockMvcResultMatchers.model().attribute("taskid",taskId))
                .andExpect(MockMvcResultMatchers.model().attribute("subtaskid",subtaskId))
                .andExpect(MockMvcResultMatchers.model().attribute("taskmodel",taskModel));

        verify(taskService,times(1)).getTask(taskId);
    }

    @Test
    void edittaskSucces() throws Exception {
        int taskId =1;
        String taskName = "testname";
        String testDescription = "Description";
        int hoursspent = 5;
        boolean iscompleted = true;
        int subtaskId = 6;

        doNothing().when(taskService).updatetask(taskId,taskName,testDescription,hoursspent,iscompleted);

        mockMvc.perform(MockMvcRequestBuilders.post("/edittaskSucces")
                .param("taskid", String.valueOf(taskId))
                .param("taskname",taskName)
                .param("description",testDescription)
                .param("hoursspent", String.valueOf(hoursspent))
                .param("iscompleted", String.valueOf(iscompleted))
                .param("subtaskid", String.valueOf(subtaskId)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/taskview?subtaskid=" + subtaskId));

        verify(taskService,times(1)).updatetask(taskId,taskName,testDescription,hoursspent,iscompleted);
    }
}