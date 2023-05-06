package org.zerhusen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.domain.Task;
import org.zerhusen.domain.TaskGroup;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
   public List<Task> findTaskByTaskGroup(TaskGroup taskGroup);
}
