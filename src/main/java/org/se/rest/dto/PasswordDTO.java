package org.se.rest.dto;

public class PasswordDTO {
   public String currentPassword;
   public String newPassword;

   public String getCurrentPassword() {
      return currentPassword;
   }

   public void setCurrentPassword(String currentPassword) {
      this.currentPassword = currentPassword;
   }

   public String getNewPassword() {
      return newPassword;
   }

   public void setNewPassword(String newPassword) {
      this.newPassword = newPassword;
   }
}
