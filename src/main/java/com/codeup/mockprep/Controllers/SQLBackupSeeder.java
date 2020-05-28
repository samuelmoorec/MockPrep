package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.QuestionRepo;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Controller
public class SQLBackupSeeder {

    private final QuestionRepo questionDao;
    private final UserRepo userDao;

    public SQLBackupSeeder(QuestionRepo questionDao, UserRepo userDao){
        this.questionDao = questionDao;
        this.userDao = userDao;
    }


        public String CreateQuestionSeederSting(){
            List<Question> questions = questionDao.findAll();
            String sqlInsertStatements = "";
            for (Question currentQuestion : questions) {
                sqlInsertStatements += "insert into mockprepdb.questions (id, language, level, question, resource, solution, title, video_url) values (";
                sqlInsertStatements += currentQuestion.getId()        + ", ";
                sqlInsertStatements += "'" + currentQuestion.getLanguage()  + "', ";
                sqlInsertStatements += currentQuestion.getLevel()     + ", ";
                sqlInsertStatements += "'" + currentQuestion.getQuestion()  + "', ";
                sqlInsertStatements += "'" + currentQuestion.getResource()  + "', ";
                sqlInsertStatements += "'" + currentQuestion.getSolution()  + "', ";
                sqlInsertStatements += "'" + currentQuestion.getTitle()     + "', ";
                sqlInsertStatements += "'" + currentQuestion.getVideo_url() + "');\n";
            }

            return sqlInsertStatements;

        }

        public String CreateUserSeederString(){
        List<User> users = userDao.findAll();
        String sqlInsertStatements = "";
        for (User currentUser: users){
            sqlInsertStatements += "insert into mockprepdb.users (id, admin, email, first_name, last_name, password, username) values (";
            sqlInsertStatements += currentUser.getId()               + ", ";
            sqlInsertStatements += currentUser.isAdmin()             + ", ";
            sqlInsertStatements += "'" + currentUser.getEmail()      + "', ";
            sqlInsertStatements += "'" + currentUser.getFirst_name() + "', ";
            sqlInsertStatements += "'" + currentUser.getLast_name()  + "', ";
            sqlInsertStatements += "'" + currentUser.getPassword()   + "', ";
            sqlInsertStatements += "'" + currentUser.getUsername()   + "');\n";
        }
        return sqlInsertStatements;
        }


    @GetMapping("/backupUsers")
    public String SqlUserInsertBackup() throws IOException {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDao.findByUsername(loggedInUser.getUsername());
        if (currentUser.isAdmin()){
            LocalDate currentDate = LocalDate.now();
            String directory = "src/main/resources/static/db";
            String filename =  currentDate + "insertUsers.sql";
            Path dataDirectory = Paths.get(directory);
            Path dataFile = Paths.get(directory, filename);

            if (Files.notExists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            if (! Files.exists(dataFile)) {
                Files.createFile(dataFile);
            }
            SQLBackupSeeder seeder = new SQLBackupSeeder(questionDao,userDao);
            Files.writeString(dataFile, seeder.CreateUserSeederString());

            return "redirect:/Questions";
        }
        return "redirect:/Questions";

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
            SQLBackupSeeder seeder = new SQLBackupSeeder(questionDao,userDao);
            Files.writeString(dataFile, seeder.CreateQuestionSeederSting());

            return "redirect:/Questions";
        }
        return "redirect:/Questions";

    }


}
