package org.zerhusen.rest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.rest.dto.RegisterDTO;
import org.zerhusen.security.model.Authority;
import org.zerhusen.security.model.User;
import org.zerhusen.security.repository.AuthorityRepository;
import org.zerhusen.security.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class RegisterController {
   PasswordEncoder passwordEncoder;

   UserRepository userRepository;

   AuthorityRepository authorityRepository;

   public RegisterController(PasswordEncoder passwordEncoder,
                             UserRepository userRepository,
                             AuthorityRepository authorityRepository) {
      this.passwordEncoder = passwordEncoder;
      this.userRepository = userRepository;
      this.authorityRepository = authorityRepository;
   }
   @PostMapping("/register")
   public void register(@RequestBody RegisterDTO registerDTO){
      User user = new User();
      user.setId(123L);
      user.setUsername(registerDTO.getUsername());
      user.setEmail(registerDTO.getEmail());
      user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
      user.setFirstname(registerDTO.getFirstName());
      user.setLastname(registerDTO.getLastName());
      user.setActivated(true);
      System.out.println(user.toString());
      Set<Authority> authorities = new HashSet<>();
      Authority authority = new Authority();
      authority.setName("ROLE_USER");
      authorities.add(authority);
      user.setAuthorities(authorities);
      userRepository.save(user);
   }
}
