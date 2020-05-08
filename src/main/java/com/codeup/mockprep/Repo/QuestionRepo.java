package com.codeup.mockprep.Repo;

import com.codeup.mockprep.Models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, Long> {
}
