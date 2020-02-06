package nl.quintor.demo.service;


import java.util.ArrayList;

import nl.quintor.demo.dto.UserDTO;
import nl.quintor.demo.model.UserDAO;
import nl.quintor.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No username found.");
        }

        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public UserDAO save(UserDTO user) {
        // todo transformer maken
        UserDAO newUser = new UserDAO();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        return userRepository.save(newUser);
    }
}
