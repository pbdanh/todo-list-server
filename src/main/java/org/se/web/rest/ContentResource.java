package org.se.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContentResource {
   @GetMapping("/content")
   public ResponseEntity<List<TaskList>> getContent() {
      List<TaskList> list = new ArrayList<>();
      list.add(new TaskList("abc", 1L));
      list.add(new TaskList("abfaskdjfc", 12L));
      list.add(new TaskList("agadgbc", 13L));
      list.add(new TaskList("abadsoifoiasduc", 14L));
      return ResponseEntity.ok(list);
   }

   @PostMapping("/content")
   public void saveTaskList(@RequestBody TaskList taskList) {
      System.out.println(taskList.getName());
   }
}


 class TaskList {
   String name;
   Long id;

    public String getName() {
       return name;
    }

    public void setName(String name) {
       this.name = name;
    }

    public Long getId() {
       return id;
    }

    public void setId(Long id) {
       this.id = id;
    }

    public TaskList(String name, Long id) {
       this.name = name;
       this.id = id;
    }

    @Override
    public String toString() {
       return "TaskList{" +
          "name='" + name + '\'' +
          ", id=" + id +
          '}';
    }
 }
