package org.zerhusen.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerhusen.security.model.TaskList;

import java.util.List;
import java.util.Optional;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
   @Query("select taskList from TaskList taskList where taskList.user.username = ?#{principal.username}")
   List<TaskList> findByUserIsCurrentUser();

   Optional<TaskList> findOneById(Long id);
}
