package org.zerhusen.rest.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class TaskDTO {


   public TaskDTO() {
   }



   private Long id;

   private LocalDate dueDate;
   private String title;

   private Long taskGroupId;

   private Boolean complete;

   private Boolean important;

   private String note;

   public Boolean getComplete() {
      return complete;
   }

   public void setComplete(Boolean complete) {
      this.complete = complete;
   }

   public Boolean getImportant() {
      return important;
   }

   public void setImportant(Boolean important) {
      this.important = important;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public Long getTaskGroupId() {
      return taskGroupId;
   }

   public void setTaskGroupId(Long taskGroupId) {
      this.taskGroupId = taskGroupId;
   }


   public LocalDate getDueDate() {
      return dueDate;
   }

   public void setDueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
   }
}
