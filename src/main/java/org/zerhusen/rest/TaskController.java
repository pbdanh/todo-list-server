package org.zerhusen.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.domain.TaskGroup;
import org.zerhusen.repository.TaskGroupRepository;
import org.zerhusen.repository.TaskRepository;
import org.zerhusen.rest.dto.TaskDTO;
import org.zerhusen.domain.Task;
import org.zerhusen.rest.dto.TaskGroupDTO;
import org.zerhusen.security.SecurityUtils;
import org.zerhusen.security.model.User;
import org.zerhusen.security.repository.UserRepository;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {

   TaskGroupRepository taskGroupRepository;

   TaskRepository taskRepository;
   private final UserRepository userRepository;

   public TaskController(TaskGroupRepository taskGroupRepository, TaskRepository taskRepository,
                         UserRepository userRepository) {
      this.taskGroupRepository = taskGroupRepository;
      this.taskRepository = taskRepository;
      this.userRepository = userRepository;
   }

   @GetMapping("/tasks")
   public ResponseEntity<List<TaskDTO>> getTasks(@RequestParam(name = "taskGroupId") Long taskGroupId) {
      TaskGroup taskGroup = taskGroupRepository.findOneById(taskGroupId).get();
      List<Task> tasks = taskRepository.findTaskByTaskGroupAndDeleted(taskGroup, false);


      List<TaskDTO> res = new ArrayList<>();
      for(Task task: tasks) {
         TaskDTO taskDTO = new TaskDTO();
         taskDTO.setId(task.getId());
         taskDTO.setTitle(task.getTitle());
         taskDTO.setComplete(task.isComplete());
         taskDTO.setImportant(task.isImportant());
         taskDTO.setNote(task.getNote());
         taskDTO.setTaskGroupId(taskGroupId);
         res.add(taskDTO);
      }



      return ResponseEntity.ok(res);
   }

   @PostMapping("/task")
   public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {

      Task newTask = new Task();
      newTask.setTitle(taskDTO.getTitle());
      newTask.setComplete(false);
      newTask.setImportant(false);
      newTask.setDeleted(false);
      newTask.setNote("");
      TaskGroup taskGroup = taskGroupRepository.getOne(taskDTO.getTaskGroupId());
      newTask.setTaskGroup(taskGroup);
      newTask = taskRepository.save(newTask);



      TaskDTO response = new TaskDTO();
      response.setId(newTask.getId());
      response.setImportant(newTask.isImportant());
      response.setNote(newTask.getNote());
      response.setTitle(newTask.getTitle());
      response.setComplete(newTask.isComplete());
      response.setTaskGroupId(newTask.getTaskGroup().getId());
      return ResponseEntity.ok(response);
   }

   @DeleteMapping("/task")
   public void deleteTask(@RequestParam(name = "id") Long id) {
      Task task = taskRepository.findById(id).get();
      task.setDeleted(true);
      taskRepository.save(task);
   }

   @PutMapping("/task")
   public void updateTask(@RequestBody TaskDTO taskDTO) {
      Task task = taskRepository.findById(taskDTO.getId()).get();
      task.setTitle(taskDTO.getTitle());
      task.setNote(taskDTO.getNote());
      taskRepository.save(task);
   }

   @PutMapping("/switchCompleteStatus")
   public void switchCompleteStatus(@RequestParam(name = "taskId") Long taskId) {
      Task task = taskRepository.findById(taskId).get();
      task.setComplete(!task.isComplete());
      taskRepository.save(task);
   }

   @PutMapping("/switchImportantStatus")
   public void switchImportantStatus(@RequestParam(name = "taskId") Long taskId) {
      Task task = taskRepository.findById(taskId).get();
      task.setImportant(!task.isImportant());
      taskRepository.save(task);
   }

   @GetMapping("/mainTaskGroup")
   public ResponseEntity<List<TaskDTO>> getMainTaskGroup() {

      List<TaskDTO> res = new ArrayList<>();

      String username = SecurityUtils.getCurrentUsername().get();
      User user = userRepository.findOneWithAuthoritiesByUsername(username).get();
      Optional<TaskGroup> mainTaskGroup = taskGroupRepository.findOneByUserAndMainTaskGroup(user, true);
      if(mainTaskGroup.isEmpty()) {
         TaskGroup newTaskGroup = new TaskGroup();
         newTaskGroup.setMainTaskGroup(true);
         newTaskGroup.setName("#main_task_group");
         newTaskGroup.setUser(user);
         newTaskGroup.setDeleted(false);
         taskGroupRepository.save(newTaskGroup);
      } else {
         List<Task> tasks = taskRepository.findTaskByTaskGroupAndDeleted(mainTaskGroup.get(), false);
         for(Task task: tasks) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTitle(task.getTitle());
            taskDTO.setComplete(task.isComplete());
            taskDTO.setImportant(task.isImportant());
            taskDTO.setNote(task.getNote());
            taskDTO.setTaskGroupId(mainTaskGroup.get().getId());
            res.add(taskDTO);
         }
      }

      return ResponseEntity.ok(res);
   }
}
