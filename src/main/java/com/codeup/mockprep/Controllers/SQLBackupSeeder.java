package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Repo.QuestionRepo;
import com.codeup.mockprep.Repo.UserRepo;

import java.util.List;

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


}
