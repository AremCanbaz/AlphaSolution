package Service;

import Model.UserModel;
import com.example.alphasolution.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

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


}
