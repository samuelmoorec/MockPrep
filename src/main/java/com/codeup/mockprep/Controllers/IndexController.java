package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final UserRepo userDao;

    public IndexController(UserRepo userDao){
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String homeRedirect(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(){
        if ( SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "anonymousUser/anonymous_index";
        }else{

            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userInDB = userDao.findByUsername(loggedInUser.getUsername());

            if (userInDB.isAdmin()) {
                return "admin/admin_index";
            }
        }
        return "user/user_index";
    }

}
