package org.se.domain;

import org.se.security.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "task_group")
public class TaskGroup {
   @Id
   @GeneratedValue (strategy = GenerationType.IDENTITY)
   private Long id;

   @NotNull
   private String name;

   @NotNull
   private Boolean mainTaskGroup;

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

   public Boolean getMainTaskGroup() {
      return mainTaskGroup;
   }

   public void setMainTaskGroup(Boolean mainTaskGroup) {
      this.mainTaskGroup = mainTaskGroup;
   }
}
