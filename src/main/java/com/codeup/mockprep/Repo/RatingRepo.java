package com.codeup.mockprep.Repo;

import com.codeup.mockprep.Models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepo extends JpaRepository<Rating, Long> {
}
