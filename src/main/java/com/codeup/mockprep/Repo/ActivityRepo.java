package com.codeup.mockprep.Repo;

import com.codeup.mockprep.Models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity, Long> {
}
