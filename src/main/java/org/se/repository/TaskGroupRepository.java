package org.se.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.se.domain.TaskGroup;
import org.se.security.model.User;

import java.util.Optional;

public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {
   Optional<TaskGroup> findOneByUserAndMainTaskGroup(User user, Boolean isMainTaskGroup);

   Optional<TaskGroup> findOneById(Long id);
}
