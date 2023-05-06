package org.zerhusen.domain;

import org.zerhusen.security.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "task_group")
public class TaskGroup {
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
      TaskGroup taskGroup = (TaskGroup) o;
      return deleted == taskGroup.deleted && Objects.equals(id, taskGroup.id) && Objects.equals(name, taskGroup.name) && Objects.equals(user, taskGroup.user);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, name, deleted, user);
   }

   @Override
   public String toString() {
      return "TaskGroup{" +
         "id=" + id +
         ", name='" + name + '\'' +
         ", deleted=" + deleted +
         ", user=" + user +
         '}';
   }
}
