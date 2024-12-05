package com.example.alphasolution.Controller;

import com.example.alphasolution.Model.SubTaskModel;
import com.example.alphasolution.Repository.SubTaskRepository;
import com.example.alphasolution.Service.ProjectService;
import com.example.alphasolution.Service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class SubTaskController {
    @Autowired
    SubTaskService subTaskService;
    @Autowired
    SubTaskRepository subTaskRepository;
    @Autowired
    ProjectService projectService;

    @GetMapping("/subtaskview")
    public String subtaskview(@RequestParam int projectid, Model model) {
        ArrayList<SubTaskModel> subtasks = subTaskService.getAllSubTasks(projectid);
        projectService.getTotalHours(projectid);
        String projectName = projectService.getProjectName(projectid);
        model.addAttribute("subtasks", subtasks);
        model.addAttribute("projectid", projectid);
        model.addAttribute("projectname", projectName);
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

}
    @GetMapping("/editsubtask")
    public String editSubTask(@RequestParam ("subTaskId") int subTaskId, Model model) {
        SubTaskModel subTask = subTaskRepository.getSubTaskById(subTaskId);
        model.addAttribute("subTask", subTask);
        return "editsubtask";
    }
    @PostMapping("/updateSubTask")
    public String updateSubTask(@RequestParam int subTaskId,
                                @RequestParam String subTaskName,
                                @RequestParam String subTaskDescription,
                                @RequestParam int projectId) {
        System.out.println("Updating SubTask:");
        System.out.println("SubTask ID: " + subTaskId);
        System.out.println("Name: " + subTaskName);
        System.out.println("Description: " + subTaskDescription);
        System.out.println("Project ID: " + projectId);

        subTaskService.updateSubTask(subTaskId, subTaskName, subTaskDescription);
        return "redirect:/subtaskview?projectid=" + projectId; // Redirect to subtaskview
    }


}
