package org.zerhusen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerhusen.domain.TaskGroup;

import java.util.List;
import java.util.Optional;

public interface TaskListRepository extends JpaRepository<TaskGroup, Long> {
   @Query("select taskList from TaskGroup taskList where taskList.user.username = ?#{principal.username}")
   List<TaskGroup> findByUserIsCurrentUser();

   Optional<TaskGroup> findOneById(Long id);
}
