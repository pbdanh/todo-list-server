package org.se.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.se.domain.Task;
import org.se.domain.TaskGroup;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
   public List<Task> findTaskByTaskGroupAndDeleted(TaskGroup taskGroup, Boolean deleted);

   public List<Task> findTaskByImportantAndDeleted(Boolean important, Boolean deleted);

   public List<Task> findTaskByDeleted(Boolean deleted);

   public List<Task> findAll(Specification<Task> spec);

//   @Query(value = "SELECT * FROM task WHERE MATCH(title) "
//      + "AGAINST (?1)", nativeQuery = true)
//   public List<Task> search(String keyword);
}
