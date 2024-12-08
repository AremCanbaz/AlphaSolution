package com.example.alphasolution.controller;

import com.example.alphasolution.model.UserModel;
import com.example.alphasolution.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // Viser login-siden
    @GetMapping("")
    public String showLoginPage() {
        return "login";
    }
    @GetMapping("login")
    public String showlogin(){
        return "login";
    }

    // Håndterer login-formularen
    @PostMapping("/loginSucces")
    public String processLoginForm(@RequestParam String username, @RequestParam String password, HttpSession session) {
        UserModel user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            return "redirect:/dashboardview?userId=" + user.getId();  // Omdirigér til dashboardet
        }
        return "login";
    }

    // Viser siden til oprettelse af en ny bruger
    @GetMapping("/createusersite")
    public String showCreateUserForm() {
        return "createUser";
    }

    // Håndterer oprettelse af en ny bruger
    @PostMapping("/createUser")
    public String processCreateUserForm(@RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        @RequestParam("email") String email) {
        try {
            UserModel userModel = new UserModel(username, password, email);
            userService.saveUser(userModel);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "createUser";
        }
    }
    // Logud knap der benyttes af alle sider og sender brugeren tilbage til login.
    @PostMapping("logout")
    public String processLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
