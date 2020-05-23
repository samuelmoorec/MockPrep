package com.codeup.mockprep.Controllers;

import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Repo.QuestionRepo;
import java.util.List;

public class SQLBackupSeeder {

    private final QuestionRepo questionDao;

    public SQLBackupSeeder(QuestionRepo questionDao){
        this.questionDao = questionDao;
    }

        public String CreateQuestionSeederSting(){
            List<Question> questions = questionDao.findAll();
            String sqlInsertStatements = "";
            for (Question currentQuestion : questions) {
                sqlInsertStatements += "insert into mockprepdb.questions (id, language, level, question, resource, solution, title, video_url) values (";
                sqlInsertStatements += currentQuestion.getId()        + ", ";
                sqlInsertStatements += currentQuestion.getLanguage()  + ", ";
                sqlInsertStatements += currentQuestion.getLevel()     + ", ";
                sqlInsertStatements += currentQuestion.getQuestion()  + ", ";
                sqlInsertStatements += currentQuestion.getResource()  + ", ";
                sqlInsertStatements += currentQuestion.getSolution()  + ", ";
                sqlInsertStatements += currentQuestion.getTitle()     + ", ";
                sqlInsertStatements += currentQuestion.getVideo_url() + ");\n";
            }

            return sqlInsertStatements;

        }


}
