package com.example.alphasolution.Controller;


import Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Service.UserService;


@Controller
public class UserController {

    private Userservice userService;

    @GetMapping()
    public String showlogin(){
        return "login";
    }

    @GetMapping("/login")
    public String processLoginForm(@RequestParam String username, @RequestParam String password){
        UserModel user = userService.authenticateUser(username, password);
        if(user != null){
            return "redirect:/";
        }
        return "login";
    }
    @GetMapping("/createusersite")
    public String showCreateUserForm(){
        return "createuser";
    }
    @PostMapping("/createUser")
    public String processCreateUserForm(@RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        @RequestParam("email") String email){
        UserModel userModel = new UserModel(username, email, password);
        userService.saveUser(userModel);
        return "redirect:/login";
    }
}

