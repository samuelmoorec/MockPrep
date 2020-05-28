package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.Activity;
import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.ActivityRepo;
import com.codeup.mockprep.Repo.QuestionRepo;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class AdminController {


    private final QuestionRepo questionDao;
    private final UserRepo userDao;
    private final ActivityRepo activityDao;

    public AdminController(QuestionRepo questionDao,UserRepo userDao,ActivityRepo activityDao){
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.activityDao = activityDao;
    }

    @GetMapping("/activitiesDescByTimeStamp.json")
    public @ResponseBody
    List<Activity> viewAllActivitiesInJSONFormat(){ return activityDao.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));}

    @GetMapping("/SuperdupperlikereallySecretAdminmakerthingy")
    public String makeAdmin(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userInDB = userDao.findByUsername(loggedInUser.getUsername());
        if (userInDB.getEmail().contains("@codeup.com")) {
            userInDB.setAdmin(true);
            userDao.save(userInDB);
        }
        return "redirect:/Questions";
    }

    @GetMapping("/admin")
    public String viewActivity(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findByUsername(loggedInUser.getUsername());
        if (currentUser.isAdmin()){

            return "admin/admin_viewActivity";
        }else{
            return "redirect:/Questions";
        }
    }
}
