    package com.example.alphasolution.Controller;

    import com.example.alphasolution.Model.TaskModel;
    import com.example.alphasolution.Repository.SubTaskRepository;
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
        @Autowired
        private SubTaskRepository subTaskRepository;

        @GetMapping("/taskview")
        public String taskview(@RequestParam int subTaskId, Model model) {
            ArrayList<TaskModel> tasks = taskService.getTasks(subTaskId);
            String subTaskName = subTaskService.getSubTaskName(subTaskId);
            model.addAttribute("tasks", tasks);
            model.addAttribute("subtaskid", subTaskId);
            model.addAttribute("subtaskname", subTaskName);
            return "taskview";
        }
        @PostMapping("/deleteTask")
        public String deleteTask(@RequestParam("taskId") int taskId, @RequestParam("subTaskId") int subTaskId) {
            taskService.deleteTask(taskId);
            return "redirect:/taskview?subTaskId=" + subTaskId; // Redirect tilbage til samme side
        }



    }