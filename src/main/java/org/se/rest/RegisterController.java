package org.se.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.se.ConstantError;
import org.se.rest.dto.RegisterDTO;
import org.se.security.model.Authority;
import org.se.security.model.User;
import org.se.security.repository.AuthorityRepository;
import org.se.security.repository.UserRepository;

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
   public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
      User user = new User();
      if(userRepository.findOneByUsername(registerDTO.getUsername()).isPresent()) {
         HttpHeaders headers = new HttpHeaders();
         headers.add("Error-Code", String.valueOf(ConstantError.USERNAME_ALREADY_EXISTS));
         return ResponseEntity.status(HttpStatus.OK)
            .headers(headers).body("Username already exists");
      }
      user.setUsername(registerDTO.getUsername());


      if(userRepository.findOneByEmail(registerDTO.getEmail()).isPresent()) {
         HttpHeaders headers = new HttpHeaders();
         headers.add("Error-Code", String.valueOf(ConstantError.EMAIL_ALREADY_EXISTS));
         return ResponseEntity.status(HttpStatus.OK)
            .headers(headers).body("Email already exists");
      }
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
      return ResponseEntity.ok().body("Successful");
   }
}
