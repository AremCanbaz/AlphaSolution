package com.example.alphasolution.controller;

import com.example.alphasolution.model.SubTaskModel;
import com.example.alphasolution.Service.ProjectService;
import com.example.alphasolution.Service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;


@Controller
public class SubTaskController {
    @Autowired
    SubTaskService subTaskService;
    @Autowired
    ProjectService projectService;

    @GetMapping("/subtaskview")
    public String subtaskview(@RequestParam int projectid, Model model) {
        ArrayList<SubTaskModel> subtasks = subTaskService.getAllSubTasks(projectid);
        subTaskService.updateProjectIscomplete(projectid);
        projectService.getTotalHours(projectid);
        System.out.println(projectid);

        int userId = subTaskService.getUseridByProjectId(projectid);
        String projectName = projectService.getProjectName(projectid);
        model.addAttribute("subtasks", subtasks);
        model.addAttribute("projectid", projectid);
        model.addAttribute("projectname", projectName);
        model.addAttribute("userId", userId);

        return "subtaskview";
    }

    @PostMapping("/deleteSubTask")
    public String deleteSubTask(@RequestParam("subtaskId") int subTaskId, @RequestParam("projectid") int projectId) {
        subTaskService.deleteSubTask(subTaskId);
        return "redirect:/subtaskview?projectid=" + projectId; // Redirect tilbage til samme side
    }

    @GetMapping("/createSubTaskView")
    public String createsubtaskview(@RequestParam int projectid, Model model) {
        model.addAttribute("projectid", projectid);
        return "createsubtask";

    }

    @PostMapping("/createsubtaskaction")
    public String createsubtask(@RequestParam int projectid, @RequestParam String subtaskname, @RequestParam String subtaskdescription) {
        subTaskService.createSubTask(projectid, subtaskname, subtaskdescription);
        return "redirect:/subtaskview?projectid=" + projectid;

    }
    @GetMapping("/editsubtask")
    public String editProject(@RequestParam("subtaskid") int subtaskid,@RequestParam("projectid") int projectid, Model model) {
        SubTaskModel subTaskModel = subTaskService.findSubTaskById(subtaskid);
        System.out.println("Received subtaskid: " + subtaskid + ", projectid: " + projectid + "subtaskname" + subTaskModel.getSubTaskName());
        model.addAttribute("subtaskid", subtaskid);
        model.addAttribute("projectid", projectid);
        model.addAttribute("subtask",subTaskModel);
        return "editsubtask";
    }
    @PostMapping("/editSubTaskSucces")
    public String editProjectSucces(@RequestParam("subTaskName") String subTaskName,
                                    @RequestParam("subTaskDescription") String subTaskDescription,
                                    @RequestParam("subTaskId") int subTaskId,
                                    @RequestParam("projectId") int projectid
    ) {
        subTaskService.editSubTask(subTaskId, subTaskName, subTaskDescription);
        return "redirect:/subtaskview?projectid=" + projectid;
    }

}
