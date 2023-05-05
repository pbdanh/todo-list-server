package org.zerhusen.security.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.security.SecurityUtils;
import org.zerhusen.security.model.TaskList;
import org.zerhusen.security.model.User;
import org.zerhusen.security.repository.TaskListRepository;
import org.zerhusen.security.repository.UserRepository;
import org.zerhusen.security.rest.dto.TaskListDTO;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskListController {

   TaskListRepository taskListRepository;

   UserRepository userRepository;

   public TaskListController(TaskListRepository taskListRepository, UserRepository userRepository) {
      this.taskListRepository = taskListRepository;
      this.userRepository = userRepository;
   }

   @GetMapping("/taskList")
   public ResponseEntity<List<TaskListDTO>> getCurrentUserTaskList() {
      List<TaskList> lists = taskListRepository.findAll();
      List<TaskListDTO> response = new ArrayList<>();
      for(TaskList taskList: lists) {
         if(taskList.getUser().getUsername().equals(SecurityUtils.getCurrentUsername().get()) && !taskList.isDeleted()) {
            response.add(new TaskListDTO(taskList));
         }
      }
      return ResponseEntity.ok(response);
   }

   @PostMapping("/taskList")
   public ResponseEntity<TaskListDTO> saveTaskList(@RequestBody TaskListDTO taskListDTO) {
      TaskList taskList = new TaskList();
      taskList.setName(taskListDTO.getName());
      taskList.setDeleted(false);
      String currentUserUserName = SecurityUtils.getCurrentUsername().get();
      User currentUser = userRepository.findOneWithAuthoritiesByUsername(currentUserUserName).get();
      taskList.setUser(currentUser);
      taskList = taskListRepository.save(taskList);
      return ResponseEntity.ok(new TaskListDTO(taskList));
   }

   @DeleteMapping("/taskList")
   public void deleteTaskList(@RequestParam(name = "id") Long id) {
      TaskList taskList = taskListRepository.findOneById(id).get();
      if(taskList.getUser().getUsername().equals(SecurityUtils.getCurrentUsername())) {
         taskList.setDeleted(true);
         taskListRepository.save(taskList);
      }
   }

   @PutMapping("/taskList")
   public ResponseEntity<TaskListDTO> updateTaskListName(@RequestBody TaskListDTO taskListDTO) {
      TaskList taskList = taskListRepository.findOneById(taskListDTO.getId()).get();
      taskList.setName(taskListDTO.getName());
      taskList = taskListRepository.save(taskList);
      return  ResponseEntity.ok(new TaskListDTO(taskList));
   }

}
