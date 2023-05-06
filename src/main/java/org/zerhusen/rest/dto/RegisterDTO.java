package org.zerhusen.rest.dto;

import java.util.Objects;

public class RegisterDTO {

   private String username;
   private String password;

   private String firstName;

   private String lastName;

   private String email;

   public RegisterDTO() {
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      RegisterDTO that = (RegisterDTO) o;
      return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email);
   }

   @Override
   public int hashCode() {
      return Objects.hash(username, password, firstName, lastName, email);
   }

   @Override
   public String toString() {
      return "RegisterDTO{" +
         "username='" + username + '\'' +
         ", password='" + password + '\'' +
         ", firstName='" + firstName + '\'' +
         ", lastName='" + lastName + '\'' +
         ", email='" + email + '\'' +
         '}';
   }
}
