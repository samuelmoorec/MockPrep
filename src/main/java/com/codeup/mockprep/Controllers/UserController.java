package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class UserController {

    private final UserRepo userDao;

    public UserController(UserRepo userDao){this.userDao = userDao;}

    @GetMapping("/users.json")
    public @ResponseBody List<User> viewAllUsersInJSONFormat(){ return userDao.findAll();}
}
