package org.zerhusen.rest.dto;


import java.util.Objects;

public class TaskDTO {


   public TaskDTO() {
   }

   public TaskDTO(Long id, String title) {
      this.id = id;
      this.title = title;
   }

   private Long id;

   private String title;

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

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      TaskDTO taskDTO = (TaskDTO) o;
      return Objects.equals(id, taskDTO.id) && Objects.equals(title, taskDTO.title);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, title);
   }

   @Override
   public String toString() {
      return "TaskDTO{" +
         "id=" + id +
         ", title='" + title + '\'' +
         '}';
   }
}
