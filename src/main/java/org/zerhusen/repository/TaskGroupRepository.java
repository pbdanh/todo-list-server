package org.zerhusen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerhusen.domain.TaskGroup;
import org.zerhusen.security.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {
   Optional<TaskGroup> findOneByUserAndMainTaskGroup(User user, Boolean isMainTaskGroup);

   Optional<TaskGroup> findOneById(Long id);
}
