package com.example.alphasolution.Controller;

import com.example.alphasolution.Model.ProjectModel;
import com.example.alphasolution.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam("userId") int userId, Model model) {
        ArrayList<ProjectModel> projects = projectService.projectList(userId);
        model.addAttribute("projects", projects);
        model.addAttribute("userId", userId);
        System.out.println(projects);
        return "dashboard"; // Navnet på din Thymeleaf-skabelon
    }

}
