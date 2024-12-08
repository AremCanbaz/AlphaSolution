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

    // Indhente service klasserne til controllerne
    @Autowired
    SubTaskService subTaskService;
    @Autowired
    ProjectService projectService;

    // Controller til at fremvise alle delprojekter
    @GetMapping("/subtaskview")
    public String subtaskview(@RequestParam int projectid, Model model) {
        ArrayList<SubTaskModel> subtasks = subTaskService.getAllSubTasks(projectid);
        subTaskService.updateProjectIscomplete(projectid);
        projectService.getTotalHours(projectid);

        int userId = subTaskService.getUseridByProjectId(projectid);
        String projectName = projectService.getProjectName(projectid);
        model.addAttribute("subtasks", subtasks);
        model.addAttribute("projectid", projectid);
        model.addAttribute("projectname", projectName);
        model.addAttribute("userId", userId);

        return "subtask-view";
    }

    // Controller til at slette delprojekter og sende tilbage til delprojekt siden.
    @PostMapping("/deleteSubTask")
    public String deleteSubTask(@RequestParam("subtaskId") int subTaskId,
                                @RequestParam("projectid") int projectId) {
        subTaskService.deleteSubTask(subTaskId);
        return "redirect:/subtask-view?projectid=" + projectId; // Redirect tilbage til samme side
    }

    // Controller til at fremvise siden til at oprette delprojekter tilknyttet projektId
    @GetMapping("/createSubTaskView")
    public String createsubtaskview(@RequestParam int projectid,
                                    Model model) {
        model.addAttribute("projectid", projectid);
        return "createsubtask";

    }
    // Controller til at oprette delprojekter med 3 parametre
    @PostMapping("/createsubtaskaction")
    public String createsubtask(@RequestParam int projectid,
                                @RequestParam String subtaskname,
                                @RequestParam String subtaskdescription) {
        subTaskService.createSubTask(projectid, subtaskname, subtaskdescription);
        return "redirect:/subtask-view?projectid=" + projectid;

    }
    // Controller til at vise ændre delprojekter siden
    @GetMapping("/editsubtask")
    public String editProject(@RequestParam("subtaskid") int subtaskid,
                              @RequestParam("projectid") int projectid,
                              Model model) {
        SubTaskModel subTaskModel = subTaskService.findSubTaskById(subtaskid);
        if (subTaskModel == null) {
            System.err.println("Subtask not found for ID: " + subtaskid);
            return "error"; // Sørg for at have en "error.html"-template
        }

        System.out.println("Received subtaskid: " + subtaskid + ", projectid: " + projectid +
                ", subtaskname: " + subTaskModel.getSubTaskName());

        model.addAttribute("subtaskid", subtaskid);
        model.addAttribute("projectid", projectid);
        model.addAttribute("subtask", subTaskModel);

        return "edit-subtask";
    }
    //Controller til at sende ændringer af delprojektet til databasen.
    @PostMapping("/editSubTaskSucces")
    public String editProjectSucces(@RequestParam("subTaskName") String subTaskName,
                                    @RequestParam("subTaskDescription") String subTaskDescription,
                                    @RequestParam("subTaskId") int subTaskId,
                                    @RequestParam("projectId") int projectid
    ) {
        subTaskService.editSubTask(subTaskId, subTaskName, subTaskDescription);
        return "redirect:/subtask-view?projectid=" + projectid;
    }

}
