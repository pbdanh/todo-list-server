package org.zerhusen.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.rest.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskResource {
   @GetMapping("/tasks")
   public ResponseEntity<List<TaskDTO>> getTasks() {
      List<TaskDTO> res = new ArrayList<>();
      res.add(new TaskDTO(1L, "day la taks 1"));
      res.add(new TaskDTO(3L, "daasdfjls 1"));

      res.add(new TaskDTO(4L, "daysdfafks 1fsd"));

      res.add(new TaskDTO(6L, "day la taks fasdjfkj"));

      res.add(new TaskDTO(7L, "day la takfasjdfk "));
      res.add(new TaskDTO(12L, "day la takssjdfkj"));



      return ResponseEntity.ok(res);
   }
}
