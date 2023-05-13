package org.se.rest;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.se.domain.TaskGroup;
import org.se.repository.TaskGroupRepository;
import org.se.repository.TaskRepository;
import org.se.rest.dto.TaskDTO;
import org.se.domain.Task;
import org.se.security.SecurityUtils;
import org.se.security.model.User;
import org.se.security.repository.UserRepository;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
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
         taskDTO.setDueDate(task.getDueDate());
         res.add(taskDTO);
      }




      return ResponseEntity.ok(res);
   }

   @GetMapping("/importantTasks")
   public ResponseEntity<List<TaskDTO>> getImportantTasks() {
      List<Task> tasks = taskRepository.findTaskByImportantAndDeleted(true, false);
      List<TaskDTO> res = new ArrayList<>();
      for(Task task: tasks) {
//         System.out.println("currentUsername: " + SecurityUtils.getCurrentUsername().get());
//         System.out.println("User w task: " + task.getTaskGroup().getUser().getUsername());
         if (task.getTaskGroup().getUser().getUsername().equals(SecurityUtils.getCurrentUsername().get())
            && !task.getTaskGroup().isDeleted()
            && !task.isComplete()) {
         TaskDTO taskDTO = new TaskDTO();
         taskDTO.setId(task.getId());
         taskDTO.setTitle(task.getTitle());
         taskDTO.setComplete(task.isComplete());
         taskDTO.setImportant(task.isImportant());
         taskDTO.setNote(task.getNote());
         taskDTO.setTaskGroupId(task.getTaskGroup().getId());
         taskDTO.setDueDate(task.getDueDate());
         res.add(taskDTO);
        }
      }
      return ResponseEntity.ok(res);
   }

   @GetMapping("/dueTodayTasks")
   public ResponseEntity<List<TaskDTO>> getDueTodayTasks() {
      List<Task> tasks = taskRepository.findTaskByDeleted(false);
      List<TaskDTO> res = new ArrayList<>();
      for(Task task: tasks) {
         if(task.getDueDate() != null) {
            if (task.getTaskGroup().getUser().getUsername().equals(SecurityUtils.getCurrentUsername().get())
               && !task.getTaskGroup().isDeleted()
               && task.getDueDate().isEqual(LocalDate.now())
               && !task.isComplete()) {

               TaskDTO taskDTO = new TaskDTO();
               taskDTO.setId(task.getId());
               taskDTO.setTitle(task.getTitle());
               taskDTO.setComplete(task.isComplete());
               taskDTO.setImportant(task.isImportant());
               taskDTO.setNote(task.getNote());
               taskDTO.setTaskGroupId(task.getTaskGroup().getId());
               taskDTO.setDueDate(task.getDueDate());
               res.add(taskDTO);
            }
         }

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
      newTask.setDueDate(taskDTO.getDueDate());
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
      response.setDueDate(newTask.getDueDate());
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
      task.setDueDate(taskDTO.getDueDate());
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

   @GetMapping("/search")
   public ResponseEntity<List<TaskDTO>> search(@RequestParam("keyword") String keyword) {
      Specification<Task> spec = (root, query, criteriaBuilder) -> {
         String[] keywords = keyword.split(" "); // Tách từ khóa thành mảng các từ
         List<Predicate> predicates = new ArrayList<>();

         // Tạo điều kiện tìm kiếm cho mỗi từ
         for (String word : keywords) {
            Expression<String> contentExpression = root.get("title");
            Predicate predicate = criteriaBuilder.like(contentExpression, "%" + word + "%");
            predicates.add(predicate);
         }

         // Kết hợp tất cả các điều kiện tìm kiếm bằng AND
         return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };

      List<Task> tasks = taskRepository.findAll(spec);
      List<TaskDTO> response = new ArrayList<>();
      for(Task task: tasks) {
         if(task.getTaskGroup().getUser().getUsername().equals(SecurityUtils.getCurrentUsername().get())
         && !task.getTaskGroup().isDeleted()) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setDueDate(task.getDueDate());
            taskDTO.setComplete(task.isComplete());
            taskDTO.setTaskGroupId(task.getTaskGroup().getId());
            taskDTO.setTitle(task.getTitle());
            taskDTO.setNote(task.getNote());
            taskDTO.setImportant(task.isImportant());
            taskDTO.setId(task.getId());
            response.add(taskDTO);
         }
      }
      return ResponseEntity.ok().body(response);
   }
}
