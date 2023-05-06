package org.zerhusen.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.domain.Task;
import org.zerhusen.domain.TaskGroup;
import org.zerhusen.repository.TaskGroupRepository;
import org.zerhusen.repository.TaskRepository;
import org.zerhusen.rest.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskResource {

   TaskGroupRepository taskGroupRepository;

   TaskRepository taskRepository;

   public TaskResource(TaskGroupRepository taskGroupRepository, TaskRepository taskRepository) {
      this.taskGroupRepository = taskGroupRepository;
      this.taskRepository = taskRepository;
   }

   @GetMapping("/tasks")
   public ResponseEntity<List<TaskDTO>> getTasks(@RequestParam(name = "taskGroupId") Long taskGroupId) {
      TaskGroup taskGroup = taskGroupRepository.findOneById(taskGroupId).get();
      List<Task> tasks = taskRepository.findTaskByTaskGroup(taskGroup);


      List<TaskDTO> res = new ArrayList<>();
      for(Task task: tasks) {
         res.add(new TaskDTO(task.getId(), task.getTitle(), task.getTaskGroup().getId()));
      }



      return ResponseEntity.ok(res);
   }

   @PostMapping("/task")
   public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {

      Task newTask = new Task();
      newTask.setTitle(taskDTO.getTitle());
      newTask.setComplete(false);
      TaskGroup taskGroup = taskGroupRepository.getOne(taskDTO.getTaskGroupId());
      newTask.setTaskGroup(taskGroup);
      newTask = taskRepository.save(newTask);
      TaskDTO response = new TaskDTO(newTask.getId(), newTask.getTitle(), newTask.getTaskGroup().getId());
      return ResponseEntity.ok(response);
   }
}
