package com.example.alphasolution.controller;

import com.example.alphasolution.model.ProjectModel;
import com.example.alphasolution.Service.ProjectService;
import com.example.alphasolution.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;


    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam("userId") int userId, Model model) {
        ArrayList<ProjectModel> projects = projectService.projectList(userId);
        String username = userService.findUsernamebyUserid(userId);
        model.addAttribute("projects", projects);
        model.addAttribute("userId", userId);
        model.addAttribute("username", username);
        System.out.println(projects);
        return "dashboard";
    }

    @PostMapping("/createproject")
    public String addProject(@RequestParam("userId")int userId , @RequestParam("projectname") String projectname, @RequestParam("description") String description) {
        projectService.createProject(projectname, description, userId);

      return "redirect:/dashboard?userId=" + userId;
    }
    @GetMapping("/createprojectview")
    public String createProject(@RequestParam("userId") int userid, Model model) {
        model.addAttribute("userId", userid);
        return "createproject";
    }

    @GetMapping("/editproject")
    public String editProject(@RequestParam("projectid") int projectid,@RequestParam("userid") int userid, Model model) {
        ProjectModel project = projectService.getProjectById(projectid);
        model.addAttribute("projectid", projectid);
        model.addAttribute("userId", userid);
        model.addAttribute("project",project);
        return "editproject";
    }
    @PostMapping("/editprojectSucces")
    public String editProjectSucces(@RequestParam("projectName") String projectName,
                                    @RequestParam("description") String description,
                                    @RequestParam("projectid") int projectid,
                                    @RequestParam("userid") int userid
                                    ) {
        projectService.editProject(projectName, description, projectid);
        return "redirect:/dashboard?userId=" + userid;
    }
    @PostMapping("deleteproject")
    public String deleteProject(@RequestParam("projectid") int projectid , @RequestParam("userid") int userid) {
        projectService.deleteProject(projectid);
        return "redirect:/dashboard?userId=" + userid;
    }

    // søge funktion til projekter
    @PostMapping("searchproject")
    public String searchProject(@RequestParam String projectName, @RequestParam int userId, Model model) {
        ArrayList<ProjectModel> userProjects = projectService.projectList(userId);


        ProjectModel project = projectService.getProjectByName(projectName, userProjects);

        if (project != null) {
            return "redirect:/subtaskview?projectid=" + project.getProjectId();
        } else {
            model.addAttribute("Fejl", "Ingen projekter fundet med navnet: " + projectName);
        }


        return "dashboard";
    }



}
