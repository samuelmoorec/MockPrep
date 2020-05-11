package com.codeup.mockprep.Services;

import com.codeup.mockprep.Models.User;
import com.codeup.mockprep.Models.UserWithRoles;
import com.codeup.mockprep.Repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepo userDoa;

    public UserDetailsLoader(UserRepo userDoa){
        this.userDoa = userDoa;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDoa.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        return new UserWithRoles(user);
    }
}
