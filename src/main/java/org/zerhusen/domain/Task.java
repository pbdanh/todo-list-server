package org.zerhusen.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

   private String note;

   @NotNull
   private boolean important;

   @NotNull
   private boolean deleted;
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

   public boolean isDeleted() {
      return deleted;
   }

   public void setDeleted(boolean deleted) {
      this.deleted = deleted;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public boolean isImportant() {
      return important;
   }

   public void setImportant(boolean important) {
      this.important = important;
   }
}
