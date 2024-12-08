package com.example.alphasolution.controller;

import com.example.alphasolution.model.ProjectModel;
import com.example.alphasolution.Service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;


    // Controller til at vise dashboard ved hjælp af userId
    @GetMapping("/dashboardview")
    public String showDashboard(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        if (userId == null) {
            // redirct til login hvis session ikke bliver accepteret
            return "redirect:/login";
        }

        ArrayList<ProjectModel> projects = projectService.projectList(userId);

        model.addAttribute("projects", projects);
        model.addAttribute("userId", userId);
        model.addAttribute("username", username);
        return "dashboard";
    }
    // Controller til at oprette projekter som kræver 3 parametre
    @PostMapping("/createproject")
    public String addProject(@RequestParam("projectname") String projectname,
                             @RequestParam("description") String description, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // sender dig tilbage til login hvis session er ikke valid
            return "redirect:/login";
        }
        projectService.createProject(projectname, description, userId);
        return "redirect:/dashboardview?userId=" + userId;
    }
    // Controller der fremviser siden til at oprette projekter
    @GetMapping("/createprojectview")
    public String createProject(@RequestParam("userId") int userid,
                                Model model) {
        model.addAttribute("userId", userid);
        return "createproject";
    }
    // Kontroller til at ændre eksisterende projekter siden
    @GetMapping("/editproject")
    public String editProject(@RequestParam("projectid") int projectid,
                              @RequestParam("userid") int userid,
                              Model model) {
        ProjectModel project = projectService.getProjectById(projectid);
        model.addAttribute("projectid", projectid);
        model.addAttribute("userId", userid);
        model.addAttribute("project",project);
        return "edit-project";
    }
    // Kontroller til at ændre eksisterende projekter og sende tilbage til dashboard
    @PostMapping("/editprojectSucces")
    public String editProjectSucces(@RequestParam("projectName") String projectName,
                                    @RequestParam("description") String description,
                                    @RequestParam("projectid") int projectid,
                                    @RequestParam("userid") int userid
                                    ) {
        projectService.editProject(projectName, description, projectid);
        return "redirect:/dashboardview?userId=" + userid;
    }
    // Controller til at slette projekter og sende tilbage til dashboard med userId
    @PostMapping("deleteproject")
    public String deleteProject(@RequestParam("projectid") int projectid ,
                                @RequestParam("userid") int userid) {
        projectService.deleteProject(projectid);
        return "redirect:/dashboardview?userId=" + userid;
    }

    // søge funktion til projekter
    @PostMapping("searchproject")
    public String searchProject(@RequestParam String projectName,
                                @RequestParam int userId,
                                Model model) {
        ArrayList<ProjectModel> userProjects = projectService.projectList(userId);

        ProjectModel project = projectService.getProjectByName(projectName, userProjects);

        if (project != null) {
            int projectid = project.getProjectId();
            return "redirect:/subtaskview?projectid=" + projectid;
        } else {
            model.addAttribute("Fejl", "Ingen projekter fundet med navnet: " + projectName);
            model.addAttribute("projects", userProjects);
            model.addAttribute("userId", userId);
            return "redirect:/dashboardview?userId=" + userId;
        }
    }

}
