package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class QuestionController {

    private final QuestionRepo questionDao;

    public QuestionController(QuestionRepo questionDao){
        this.questionDao = questionDao;
    }



    @GetMapping("/Questions")
    public String questions(){
        return "questions";
    }

    @GetMapping("/questions.json")
    public @ResponseBody List<Question> viewAllQuestionsInJSONFormat(){
        return questionDao.findAll();
    }



    @GetMapping("/Question/Create")
    public String addQuestionForm(){
        return "createQuestion";
    }

    @PostMapping("/Question/Create")
    public String SubmitQuestion(
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "language") String language,
            @RequestParam(name = "level") Long level,
            @RequestParam(name = "question") String question,
            @RequestParam(name = "solution") String solution,
            @RequestParam(name = "solution_video") String solution_video,
            @RequestParam(name = "resource") String resource
            ){
            Question newQuestion = new Question(subject,language,level,question,solution,solution_video,resource);
            questionDao.save(newQuestion);
        return "questions";
    }

    @PostMapping("/SelectQuestion")
    public String submitquestionActivity(){
        @RequestParam
    }
}

