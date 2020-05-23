package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.QuestionRepo;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @GetMapping("/question/{question_id}.json")
    public @ResponseBody List<Question> viewUserInJSONFormat(@PathVariable long question_id){
        return questionDao.findAllById(question_id);
    }


    @GetMapping("/Questions")
    public String questions(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findByUsername(loggedInUser.getUsername());
        SQLBackupSeeder seeder = new SQLBackupSeeder(questionDao);
        System.out.println(seeder.CreateQuestionSeederSting());
        if (currentUser.isAdmin()){
            System.out.println("adminView");
            return "admin/admin_questions";
        }
        return "user/user_questions";
    }

    @GetMapping("/backupQuestions")
    public String SqlQuestionsInsertBackup() throws IOException {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findByUsername(loggedInUser.getUsername());
        if (currentUser.isAdmin()){
            LocalDate currentDate = LocalDate.now();
            String directory = "src/main/resources/static/db";
            String filename =  currentDate + "insertBackup.sql";
            Path dataDirectory = Paths.get(directory);
            Path dataFile = Paths.get(directory, filename);

            if (Files.notExists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            if (! Files.exists(dataFile)) {
                Files.createFile(dataFile);
            }
            SQLBackupSeeder seeder = new SQLBackupSeeder(questionDao);
            Files.writeString(dataFile, seeder.CreateQuestionSeederSting());

            return "redirect:/Questions";
        }
        return "redirect:/Questions";

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
        Question newQuestion = new Question(id,subject,language,level,question,solution,solution_video,resource);
        questionDao.save(newQuestion);
        System.out.println(newQuestion.getSolution());
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

