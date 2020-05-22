package com.codeup.mockprep.Repo;

import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {

    List<Question> findAllById(Long id);
}
