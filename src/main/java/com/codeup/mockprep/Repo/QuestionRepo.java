package com.codeup.mockprep.Repo;

import com.codeup.mockprep.Models.Question;
import com.codeup.mockprep.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepo extends JpaRepository<Question, Long> {

    List<Question> findAllById(Long id);

    @Query("FROM Question q WHERE q.title LIKE %:term% OR q.language LIKE %:term% OR q.question LIKE %:term%")
    List<Question> FindBySearchTerm(@Param("term") String term);
}
