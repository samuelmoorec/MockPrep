package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.Activity;
import com.codeup.mockprep.Models.ActivityAjax;
import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.ActivityRepo;
import com.codeup.mockprep.Repo.QuestionRepo;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;

@RestController
public class AjaxController {

    private final UserRepo userDao;
    private final ActivityRepo activityDao;
    private final QuestionRepo questionDao;

    public AjaxController(UserRepo userDao,ActivityRepo activityDao,QuestionRepo questionDao){
        this.userDao = userDao;
        this.activityDao = activityDao;
        this.questionDao = questionDao;}

    @ResponseBody
    @RequestMapping(value = "/questionSelection", method = RequestMethod.POST)
    public void getSearchResultViaAjax(
            @RequestBody ActivityAjax activityAjax) {
        System.out.println(activityAjax.getQuestion_id());
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userInDB = userDao.findByUsername(loggedInUser.getUsername());
        Long dateInMilliSecs = new java.util.Date().getTime();
        Timestamp timestamp = new Timestamp(dateInMilliSecs);
        Question question = questionDao.getOne(activityAjax.getQuestion_id());
        Activity newActivity = new Activity(question,userInDB,activityAjax.getNumber_in_secs(),"Selected a Question",timestamp);
        activityDao.save(newActivity);

    }

}