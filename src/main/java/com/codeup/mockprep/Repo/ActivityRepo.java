package com.codeup.mockprep.Repo;

import com.codeup.mockprep.Models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface ActivityRepo extends JpaRepository<Activity, Long> {

    @Modifying
    @Query("DELETE FROM Activity a WHERE a.id = :id")
    void deleteAllByQuestionID(@Param("id")Long ID);

    @Transactional
    void deleteAllByQuestion_Id(Long id);
}
