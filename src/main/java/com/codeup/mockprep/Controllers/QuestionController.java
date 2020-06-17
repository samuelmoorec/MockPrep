package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.ActivityRepo;
import com.codeup.mockprep.Repo.QuestionRepo;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class QuestionController {

    private final QuestionRepo questionDao;
    private final UserRepo userDao;
    private final ActivityRepo activityDao;

    public QuestionController(QuestionRepo questionDao,UserRepo userDao, ActivityRepo activityDao){
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.activityDao = activityDao;
    }




    @GetMapping("/questions.json")
    public @ResponseBody List<Question> viewAllQuestionsInJSONFormat(){
        return questionDao.findAll();
    }

    @GetMapping("/question/{question_id}.json")
    public @ResponseBody List<Question> viewUserInJSONFormat(@PathVariable long question_id){
        return questionDao.findAllById(question_id);
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
        return "redirect:/Questions";
    }


    @GetMapping("/Questions")
    public String questions(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findByUsername(loggedInUser.getUsername());
        SQLBackupSeeder seeder = new SQLBackupSeeder(questionDao,userDao);
        seeder.CreateQuestionSeederSting();
        if (currentUser.isAdmin()){
            return "admin/admin_questions";
        }
        return "user/user_questions";
    }


    @GetMapping("/editQuestion")
    public String editQuestion(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findByUsername(loggedInUser.getUsername());
        if (currentUser.isAdmin()){
            return "admin/editQuestion";
        }
        return "redirect:/Questions";
    }

    @PostMapping("/editQuestion")
    public String SubmitQuestion(
            @RequestParam(name = "question_id") Long id,
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "language") String language,
            @RequestParam(name = "level") Long level,
            @RequestParam(name = "questionFormatted") String question,
            @RequestParam(name = "solutionFormatted") String solution,
            @RequestParam(name = "solution_video") String solution_video,
            @RequestParam(name = "resource") String resource
    ){
        System.out.println(id);
        Question questionInDB = questionDao.getOne(id);
        questionInDB.setTitle(subject);
        questionInDB.setLanguage(language);
        questionInDB.setLevel(level);
        questionInDB.setQuestion(question);
        questionInDB.setSolution(solution);
        questionInDB.setVideo_url(solution_video);
        questionInDB.setResource(resource);
        questionDao.save(questionInDB);
        return "redirect:/Questions";

    }


    @GetMapping("/deleteQuestion/{question_id}")
    public String DeleteQuestion(@PathVariable long question_id){


        activityDao.deleteAllByQuestion_Id(question_id);
        questionDao.deleteById(question_id);
        return "redirect:/admin#questions";
    }
}

