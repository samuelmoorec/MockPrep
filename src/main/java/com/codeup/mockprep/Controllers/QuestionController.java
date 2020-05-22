package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.QuestionRepo;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuestionController {

    private final QuestionRepo questionDao;
    private final UserRepo userDao;

    public QuestionController(QuestionRepo questionDao,UserRepo userDao){
        this.questionDao = questionDao;
        this.userDao = userDao;
    }


    @GetMapping("/questions.json")
    public @ResponseBody List<Question> viewAllQuestionsInJSONFormat(){
        return questionDao.findAll();
    }


    @GetMapping("/Questions")
    public String questions(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findByUsername(loggedInUser.getUsername());
        if (currentUser.isAdmin()){
            System.out.println("adminView");
            return "admin/admin_questions";
        }
        return "user/user_questions";
    }




    @GetMapping("/CreateQuestion")
    public String addQuestionForm(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findByUsername(loggedInUser.getUsername());
        if (currentUser.isAdmin()){
            return "admin/createQuestion";
        }
        return "redirect:/Questions";
    }

    @PostMapping("/CreateQuestion")
    public String SubmitQuestion(
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "language") String language,
            @RequestParam(name = "level") Long level,
            @RequestParam(name = "questionFormatted") String question,
            @RequestParam(name = "solutionFormatted") String solution,
            @RequestParam(name = "solution_video") String solution_video,
            @RequestParam(name = "resource") String resource
            ){
            Question newQuestion = new Question(subject,language,level,question,solution,solution_video,resource);
            questionDao.save(newQuestion);
        System.out.println(newQuestion.getSolution());
        return "redirect:/Questions";
    }

//    @PostMapping("/SelectQuestion")
//    public String submitquestionActivity(){
//        @RequestParam
//    }
}

