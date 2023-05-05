package org.zerhusen.security.rest.dto;

import org.zerhusen.security.model.TaskList;

import java.util.Objects;

public class TaskListDTO {

   public TaskListDTO() {

   }
   public TaskListDTO(TaskList taskList) {
      this.id = taskList.getId();
      this.name = taskList.getName();
   }

   TaskList toEntity() {
      TaskList taskList = new TaskList();
      taskList.setName(this.name);
      taskList.setId(this.id);
      return taskList;
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
      TaskListDTO that = (TaskListDTO) o;
      return Objects.equals(id, that.id) && Objects.equals(name, that.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, name);
   }

   @Override
   public String toString() {
      return "TaskListDTO{" +
         "id=" + id +
         ", name='" + name + '\'' +
         '}';
   }
}
