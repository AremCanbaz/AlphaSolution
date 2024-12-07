package com.example.alphasolution.controller;

import com.example.alphasolution.model.TaskModel;
import com.example.alphasolution.Service.SubTaskService;
import com.example.alphasolution.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class TaskController {
    //Indsættelse af service klasserne til brug af controllerne
    @Autowired
    SubTaskService subTaskService;
    @Autowired
    TaskService taskService;

    //Controller til at fremvise opgave siden
    @GetMapping("/taskview")
    public String subtaskview(@RequestParam int subtaskid, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            //
            return "redirect:/login";
        }
        // Hente opgaverne af delprojekt id
        ArrayList<TaskModel> tasks = taskService.getTasks(subtaskid);
        // hente navnet på delprojektet.
        String subtaskName = subTaskService.getSubtaskName(subtaskid);
        // samling af tiden af alle opgaverne til at sende dem til delprojektet
        subTaskService.getTotalHoursForSubTask(subtaskid);
        // Ændre til true ved delprojekt hvis alle opgaverne er true
        taskService.updateSubTaskToTrueIfAllTaskAreDone(subtaskid);
        // hente projektid af delprojekt id
        int projectid = taskService.getProjectIdBySubtaskId(subtaskid);
        model.addAttribute("tasks", tasks);
        model.addAttribute("subtaskid", subtaskid);
        model.addAttribute("subtaskname", subtaskName);
        model.addAttribute("projectid", projectid);
        return "taskview";
    }
    // Hente opret opgave siden
    @GetMapping("/opretopgaveview")
    public String opretopgaveview(@RequestParam int subtaskid,
                                  Model model) {
        model.addAttribute("subtaskid", subtaskid);
        return "createtask";
    }
    // Sender oprettelse af opgaven videre til databasen og sender brugeren tilbage til opgave siden
    @PostMapping("/opretopgaveaction")
    public String opretopgave(@RequestParam int subtaskid,
                              @RequestParam String taskname,
                              @RequestParam String taskdescription,
                              @RequestParam int time) {
        taskService.createtask(subtaskid, taskname, taskdescription, time);
        return "redirect:/taskview?subtaskid=" + subtaskid;

    }
    // Kontrolleren sletter opgaven i databasen, og sender brugeren tilbage til opgave siden
    @PostMapping("deletetask")
    public String deletetask(@RequestParam int subtaskid,
                             @RequestParam int taskid) {
        taskService.deletetask(taskid);
        return "redirect:/taskview?subtaskid=" + subtaskid;
    }
    // Henter ændre opgave siden
    @GetMapping("edittask")
    public String edittask(@RequestParam int taskId,
                           @RequestParam int subtaskid,
                           Model model) {
        // henter den specifikke opgave der skal ændres
        TaskModel taskModel = taskService.getTask(taskId);
        model.addAttribute("taskid", taskId);
        model.addAttribute("subtaskid", subtaskid);
        model.addAttribute("taskmodel", taskModel);
        return "edittask";
    }
    //Sender ændringerne af opgaven til databasen
    @PostMapping("edittaskSucces")
    public String edittaskSucces(
            @RequestParam int taskid,
            @RequestParam String taskname,
            @RequestParam String description,
            @RequestParam int hoursspent,
            // DefaultValue = "False" er sat ind, hvis den markeret box er falsk bliver det sendt ind i databasen er falsk, ellers virkede det ikke.
            @RequestParam(defaultValue = "false") boolean iscompleted,
            @RequestParam int subtaskid) {
        //Metode til at opdatere opgaven i databasen
        taskService.updatetask(taskid, taskname, description, hoursspent, iscompleted);
        return "redirect:/taskview?subtaskid=" + subtaskid;

}
}
