package com.example.alphasolution.Service;


import com.example.alphasolution.Model.UserModel;
import com.example.alphasolution.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void saveUser(UserModel user){
        userRepository.addUser(user);
    }
    public UserModel authenticate(String username, String password){
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public String findUsernamebyUserid(int userid){
        return userRepository.findUsernameByUserId(userid);
    }


}