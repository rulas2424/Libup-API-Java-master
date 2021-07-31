package com.americadigital.libupapi.Security.JWT.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

            return new User(userName, "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6ImFtZXJpY2FkaWdpdGFsMjAxOSIsImlhdCI6MTUxNjIzOTAyMn0.NntPvBv5c34VSm1zrPVRFmvhB9y3eIZzBsc1DqytWMTPbDA7uJsUj7Kx5wSFN8cJQ2_w2WSOtf7hqYv_t4UzEA",
                    new ArrayList<>());

    }
}
