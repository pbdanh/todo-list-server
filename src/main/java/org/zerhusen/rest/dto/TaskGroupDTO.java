package org.zerhusen.rest.dto;

import org.zerhusen.domain.TaskGroup;

import java.util.Objects;

public class TaskGroupDTO {

   public TaskGroupDTO() {

   }


   TaskGroup toEntity() {
      TaskGroup taskGroup = new TaskGroup();
      taskGroup.setName(this.name);
      taskGroup.setId(this.id);
      return taskGroup;
   }
   private Long id;

   private String name;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      TaskGroupDTO that = (TaskGroupDTO) o;
      return Objects.equals(id, that.id) && Objects.equals(name, that.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, name);
   }

   @Override
   public String toString() {
      return "TaskGroupDTO{" +
         "id=" + id +
         ", name='" + name + '\'' +
         '}';
   }
}
