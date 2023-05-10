package org.zerhusen.rest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.rest.dto.PasswordDTO;
import org.zerhusen.rest.dto.UserDTO;
import org.zerhusen.security.SecurityUtils;
import org.zerhusen.security.model.User;
import org.zerhusen.security.repository.UserRepository;

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
   public void updateUser(@RequestBody UserDTO userDTO) {

      String currentUsername = SecurityUtils.getCurrentUsername().get();
      User currentUser = userRepository.findOneWithAuthoritiesByUsername(currentUsername).get();
      currentUser.setFirstname(userDTO.getFirstName());
      currentUser.setLastname(userDTO.getLastName());
      currentUser.setEmail(userDTO.getEmail());
      userRepository.save(currentUser);
   }

   @PutMapping("/changePassword")
   public void changePassword(@RequestBody PasswordDTO passwordDTO) {
      String currentUsername = SecurityUtils.getCurrentUsername().get();
      User currentUser = userRepository.findOneWithAuthoritiesByUsername(currentUsername).get();
      System.out.println(passwordEncoder.encode(passwordDTO.getCurrentPassword()));
      System.out.println(currentUser.getPassword());
      if(passwordEncoder.matches(passwordDTO.getCurrentPassword(), currentUser.getPassword())) {
         currentUser.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
         userRepository.save(currentUser);
      }
   }
}
