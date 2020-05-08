package com.codeup.mockprep.Repo;

import com.codeup.mockprep.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
