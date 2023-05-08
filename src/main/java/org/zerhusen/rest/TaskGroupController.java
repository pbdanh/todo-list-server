package org.zerhusen.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.security.SecurityUtils;
import org.zerhusen.domain.TaskGroup;
import org.zerhusen.security.model.User;
import org.zerhusen.repository.TaskGroupRepository;
import org.zerhusen.security.repository.UserRepository;
import org.zerhusen.rest.dto.TaskGroupDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskGroupController {

   TaskGroupRepository taskGroupRepository;

   UserRepository userRepository;

   public TaskGroupController(TaskGroupRepository taskGroupRepository, UserRepository userRepository) {
      this.taskGroupRepository = taskGroupRepository;
      this.userRepository = userRepository;
   }

   @GetMapping("/taskGroup")
   public ResponseEntity<List<TaskGroupDTO>> getCurrentUserTaskList() {
      List<TaskGroup> lists = taskGroupRepository.findAll();
      List<TaskGroupDTO> response = new ArrayList<>();
      for(TaskGroup taskGroup : lists) {
         if(taskGroup.getUser().getUsername().equals(SecurityUtils.getCurrentUsername().get()) && !taskGroup.isDeleted()) {
            TaskGroupDTO taskGroupDTO = new TaskGroupDTO();
            taskGroupDTO.setId(taskGroup.getId());
            taskGroupDTO.setName(taskGroup.getName());
            response.add(taskGroupDTO);
         }
      }
      return ResponseEntity.ok(response);
   }

   @PostMapping("/taskGroup")
   public ResponseEntity<TaskGroupDTO> saveTaskList(@RequestBody TaskGroupDTO taskGroupDTO) {
      TaskGroup taskGroup = new TaskGroup();
      taskGroup.setName(taskGroupDTO.getName());
      taskGroup.setDeleted(false);
      taskGroup.setMainTaskGroup(false);
      String currentUserUserName = SecurityUtils.getCurrentUsername().get();
      User currentUser = userRepository.findOneWithAuthoritiesByUsername(currentUserUserName).get();
      taskGroup.setUser(currentUser);
      taskGroup = taskGroupRepository.save(taskGroup);
      TaskGroupDTO res = new TaskGroupDTO();
      res.setId(taskGroup.getId());
      res.setName(taskGroup.getName());
      return ResponseEntity.ok(res);
   }

   @DeleteMapping("/taskGroup")
   public void deleteTaskList(@RequestParam(name = "id") Long id) {
      TaskGroup taskGroup = taskGroupRepository.findOneById(id).get();
//      if(taskList.getUser().getUsername().equals(SecurityUtils.getCurrentUsername())) {
         taskGroup.setDeleted(true);
         taskGroupRepository.save(taskGroup);
//      }
   }

   @PutMapping("/taskGroup")
   public ResponseEntity<TaskGroupDTO> updateTaskListName(@RequestBody TaskGroupDTO taskGroupDTO) {
      TaskGroup taskGroup = taskGroupRepository.findOneById(taskGroupDTO.getId()).get();
      taskGroup.setName(taskGroupDTO.getName());
      taskGroup = taskGroupRepository.save(taskGroup);
      TaskGroupDTO res = new TaskGroupDTO();
      res.setId(taskGroup.getId());
      res.setName(taskGroup.getName());
      return  ResponseEntity.ok(res);
   }

}
