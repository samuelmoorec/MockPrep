package com.codeup.mockprep.Controllers;


import com.codeup.mockprep.Models.Activity;
import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.ActivityRepo;
import com.codeup.mockprep.Repo.QuestionRepo;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController{

    private final UserRepo userDao;
    private final ActivityRepo activityDao;
    private final QuestionRepo questionDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepo userDao,ActivityRepo activityDao, QuestionRepo questionDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.activityDao = activityDao;
        this.questionDao = questionDao;
        this.passwordEncoder = passwordEncoder;}

    @GetMapping("/users.json")
    public @ResponseBody List<User> viewAllUsersInJSONFormat(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findByUsername(loggedInUser.getUsername());

            if (currentUser.isAdmin()){

                return userDao.findAll();

            }else{
                return userDao.findAllById(currentUser.getId());
            }

        }


    @GetMapping("/currentUser.json")
    public @ResponseBody List<User> viewUserInJSONFormat(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDao.findAllById(loggedInUser.getId());}

    @GetMapping("/signup")
    public String SignUpForm(){
        System.out.println( SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if ( SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "anonymousUser/createUser";
        }else{
            return "redirect:/Questions";
        }
    }

    @PostMapping("/signup")
    public String CreateUser(
            @RequestParam(name = "first_name") String first_name,
            @RequestParam(name = "last_name") String last_name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password
    ){
        System.out.println(password);
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(username,email,first_name,last_name,hashedPassword,false);
        userDao.save(newUser);
        User userInDB = userDao.findByUsername(username);
        Long dateInMilliSecs = new java.util.Date().getTime();
        Timestamp timestamp = new Timestamp(dateInMilliSecs);
        Activity newActivity = new Activity(userInDB,"Created Account",timestamp);
        activityDao.save(newActivity);
        return "redirect:/login";
    }

    @GetMapping("/login/redirect")
    public String LoginRedirect(){

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userInDB = userDao.findByUsername(loggedInUser.getUsername());
        Long dateInMilliSecs = new java.util.Date().getTime();
        Timestamp timestamp = new Timestamp(dateInMilliSecs);
        Activity newActivity = new Activity(userInDB,"Logged In",timestamp);
        System.out.println("Logged in");
        activityDao.save(newActivity);
        return "redirect:/Questions";
    }



    @GetMapping("/updateAccount")
    public String UpdateUserForm(){
        return "editUser";
    }


    @PostMapping("/user/update")
    public String UpdateUser(
            @RequestParam(name = "first_name") String first_name,
            @RequestParam(name = "last_name") String last_name,
            @RequestParam(name = "email") String email
    ){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> User = userDao.findById(loggedInUser.getId());
        User updatedUser = User.get();
        updatedUser.setFirst_name(first_name);
        updatedUser.setLast_name(last_name);
        updatedUser.setEmail(email);
        userDao.save(updatedUser);
        return "redirect:/Questions";
    }


}
