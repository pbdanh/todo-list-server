package org.zerhusen.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "task")
public class Task {
   @Id
   @GeneratedValue (strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   private String title;

   @NotNull
   private boolean complete;

   @ManyToOne
   @JoinColumn(name = "task_group")
   private TaskGroup taskGroup;

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

   public boolean isComplete() {
      return complete;
   }

   public void setComplete(boolean complete) {
      this.complete = complete;
   }

   public TaskGroup getTaskGroup() {
      return taskGroup;
   }

   public void setTaskGroup(TaskGroup taskGroup) {
      this.taskGroup = taskGroup;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Task task = (Task) o;
      return complete == task.complete && Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(taskGroup, task.taskGroup);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, title, complete, taskGroup);
   }

   @Override
   public String toString() {
      return "Task{" +
         "id=" + id +
         ", title='" + title + '\'' +
         ", complete=" + complete +
         ", taskGroup=" + taskGroup +
         '}';
   }
}
