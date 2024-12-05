package com.example.alphasolution.Controller;

import com.example.alphasolution.Model.TaskModel;
import com.example.alphasolution.Service.ProjectService;
import com.example.alphasolution.Service.SubTaskService;
import com.example.alphasolution.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class TaskController {
    @Autowired
    SubTaskService subTaskService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;

    @GetMapping("/taskview")
    public String subtaskview(@RequestParam int subtaskid, Model model) {
        ArrayList<TaskModel> tasks = taskService.getTasks(subtaskid);
        String subtaskName = subTaskService.getSubtaskName(subtaskid);
        subTaskService.getTotalHoursForSubTask(subtaskid);
        model.addAttribute("tasks", tasks);
        model.addAttribute("subtaskid", subtaskid);
        model.addAttribute("subtaskname", subtaskName);
        return "taskview";
    }
    @GetMapping("/opretopgaveview")
    public String opretopgaveview(@RequestParam int subtaskid, Model model) {
        model.addAttribute("subtaskid", subtaskid);
        return "createtask";

    }
    @PostMapping("/opretopgaveaction")
    public String opretopgave(@RequestParam int subtaskid, @RequestParam String taskname, @RequestParam String taskdescription, @RequestParam int time) {
        taskService.createtask(subtaskid, taskname, taskdescription, time);
        return "redirect:/taskview?subtaskid=" + subtaskid;

    }
}
