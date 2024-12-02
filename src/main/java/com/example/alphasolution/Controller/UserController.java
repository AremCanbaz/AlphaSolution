package com.example.alphasolution.Controller;


import com.example.alphasolution.Model.UserModel;
import com.example.alphasolution.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String showlogin() {
        return "login";
    }

    @GetMapping("/login")
    public String processLoginForm(@RequestParam String username, @RequestParam String password) {
        UserModel user = userService.authenticate(username, password);
        if (user != null) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/createusersite")
    public String showCreateUserForm() {
        return "createUser";
    }

    @PostMapping("/createUser")
    public String processCreateUserForm(@RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        @RequestParam("email") String email) {
        UserModel userModel = new UserModel(username, email, password);
        userService.saveUser(userModel);
        return "redirect:/login";
    }

    @PostMapping("/loginSucces")
    public String processLoginFormm(@RequestParam String username, @RequestParam String password) {
        UserModel user = userService.authenticate(username, password);
        if (user != null) {
            return "redirect:/dashboard?userId=" + user.getId();  // Omdirigér til forsiden med userId som URL-parameter
        }
        return "login";
    }
}

