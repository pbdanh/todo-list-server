package org.zerhusen.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.security.SecurityUtils;
import org.zerhusen.domain.TaskGroup;
import org.zerhusen.security.model.User;
import org.zerhusen.repository.TaskListRepository;
import org.zerhusen.security.repository.UserRepository;
import org.zerhusen.rest.dto.TaskGroupDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskGroupController {

   TaskListRepository taskListRepository;

   UserRepository userRepository;

   public TaskGroupController(TaskListRepository taskListRepository, UserRepository userRepository) {
      this.taskListRepository = taskListRepository;
      this.userRepository = userRepository;
   }

   @GetMapping("/taskGroup")
   public ResponseEntity<List<TaskGroupDTO>> getCurrentUserTaskList() {
      List<TaskGroup> lists = taskListRepository.findAll();
      List<TaskGroupDTO> response = new ArrayList<>();
      for(TaskGroup taskGroup : lists) {
         if(taskGroup.getUser().getUsername().equals(SecurityUtils.getCurrentUsername().get()) && !taskGroup.isDeleted()) {
            response.add(new TaskGroupDTO(taskGroup));
         }
      }
      return ResponseEntity.ok(response);
   }

   @PostMapping("/taskGroup")
   public ResponseEntity<TaskGroupDTO> saveTaskList(@RequestBody TaskGroupDTO taskGroupDTO) {
      TaskGroup taskGroup = new TaskGroup();
      taskGroup.setName(taskGroupDTO.getName());
      taskGroup.setDeleted(false);
      String currentUserUserName = SecurityUtils.getCurrentUsername().get();
      User currentUser = userRepository.findOneWithAuthoritiesByUsername(currentUserUserName).get();
      taskGroup.setUser(currentUser);
      taskGroup = taskListRepository.save(taskGroup);
      return ResponseEntity.ok(new TaskGroupDTO(taskGroup));
   }

   @DeleteMapping("/taskGroup")
   public void deleteTaskList(@RequestParam(name = "id") Long id) {
      TaskGroup taskGroup = taskListRepository.findOneById(id).get();
//      if(taskList.getUser().getUsername().equals(SecurityUtils.getCurrentUsername())) {
         taskGroup.setDeleted(true);
         taskListRepository.save(taskGroup);
//      }
   }

   @PutMapping("/taskGroup")
   public ResponseEntity<TaskGroupDTO> updateTaskListName(@RequestBody TaskGroupDTO taskGroupDTO) {
      TaskGroup taskGroup = taskListRepository.findOneById(taskGroupDTO.getId()).get();
      taskGroup.setName(taskGroupDTO.getName());
      taskGroup = taskListRepository.save(taskGroup);
      return  ResponseEntity.ok(new TaskGroupDTO(taskGroup));
   }

}
