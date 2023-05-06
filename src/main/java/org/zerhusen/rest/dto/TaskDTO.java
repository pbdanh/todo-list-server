package org.zerhusen.rest.dto;


import java.util.Objects;

public class TaskDTO {


   public TaskDTO() {
   }

   public TaskDTO(Long id, String title, Long taskGroupId) {
      this.id = id;
      this.title = title;
      this.taskGroupId = taskGroupId;
   }

   private Long id;

   private String title;

   private Long taskGroupId;

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


}
