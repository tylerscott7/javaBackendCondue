package condueApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private Object seshData;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/auth")
    public Object getSesh(HttpSession session) throws Exception{
        try{
            seshData = session.getAttribute("username");
            return seshData;
        }catch(Exception err){
            throw new Exception(err.getMessage());
        }
    }

    @PostMapping("/auth/register")
    public User register(@RequestBody User user, HttpSession session) throws Exception{
        try{
            User newUser = userService.saveUser(user);
            session.setAttribute("username", newUser.getUsername());
            return newUser;
        }catch(Exception err){
            throw new Exception(err.getMessage());
        }
    }

    @PostMapping("/auth/login")
    public User login(@RequestBody User user, HttpSession session) throws Exception{
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User userLoggingIn = userRepository.findByUsername(user.getUsername());
        boolean validLogin = bCryptPasswordEncoder.matches(user.getPassword(), userLoggingIn.getPassword());
        if(validLogin){
            session.setAttribute("username", userLoggingIn.getUsername());
            return userLoggingIn;
        }else{
            throw new Exception("invalid credentials");
        }
    }

}