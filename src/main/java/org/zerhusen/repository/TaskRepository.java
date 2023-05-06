package org.zerhusen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
