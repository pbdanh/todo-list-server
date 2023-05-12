package org.se.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.se.ConstantError;
import org.se.rest.dto.PasswordDTO;
import org.se.rest.dto.UserDTO;
import org.se.security.SecurityUtils;
import org.se.security.model.User;
import org.se.security.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

   UserRepository userRepository;

   PasswordEncoder passwordEncoder;

   public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   @PutMapping("/user")
   public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {

      if(userRepository.findOneByEmail(userDTO.getEmail()).isPresent()) {
         HttpHeaders headers = new HttpHeaders();
         headers.add("Error-Code", String.valueOf(ConstantError.USERNAME_ALREADY_EXISTS));
         return ResponseEntity.status(HttpStatus.OK)
            .headers(headers).body("Email already exists or used by current user");
      }

      String currentUsername = SecurityUtils.getCurrentUsername().get();
      User currentUser = userRepository.findOneWithAuthoritiesByUsername(currentUsername).get();
      currentUser.setFirstname(userDTO.getFirstName());
      currentUser.setLastname(userDTO.getLastName());
      currentUser.setEmail(userDTO.getEmail());
      userRepository.save(currentUser);
      return ResponseEntity.ok().body("Success");
   }

   @PutMapping("/changePassword")
   public ResponseEntity<String> changePassword(@RequestBody PasswordDTO passwordDTO) {
      String currentUsername = SecurityUtils.getCurrentUsername().get();
      User currentUser = userRepository.findOneWithAuthoritiesByUsername(currentUsername).get();
      System.out.println(passwordEncoder.encode(passwordDTO.getCurrentPassword()));
      System.out.println(currentUser.getPassword());
      if(passwordEncoder.matches(passwordDTO.getCurrentPassword(), currentUser.getPassword())) {
         currentUser.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
         userRepository.save(currentUser);
         return ResponseEntity.ok().body("Success");
      }

         return ResponseEntity.ok().body("Wrong current password!");

   }
}
