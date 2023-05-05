package org.zerhusen.security.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "task_list")
public class TaskList {
   @Id
   @GeneratedValue (strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   private String name;

   @NotNull
   private boolean deleted;

   @ManyToOne
   @JoinColumn(name = "user")
   private User user;

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

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public boolean isDeleted() {
      return deleted;
   }

   public void setDeleted(boolean deleted) {
      this.deleted = deleted;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      TaskList taskList = (TaskList) o;
      return deleted == taskList.deleted && Objects.equals(id, taskList.id) && Objects.equals(name, taskList.name) && Objects.equals(user, taskList.user);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, name, deleted, user);
   }

   @Override
   public String toString() {
      return "TaskList{" +
         "id=" + id +
         ", name='" + name + '\'' +
         ", deleted=" + deleted +
         ", user=" + user +
         '}';
   }
}
