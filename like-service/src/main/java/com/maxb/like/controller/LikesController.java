package com.maxb.like.controller;

import com.maxb.like.domain.Task;
import com.maxb.like.events.models.BalanceChangeModel;
import com.maxb.like.repository.TaskRepository;
import com.maxb.like.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class LikesController {


    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @PostConstruct
    @RequestMapping(value="/test_data",method = RequestMethod.GET)
    private void addTestData(){
        taskRepository.deleteAll();

        List<String> actionsUserIds1 = new ArrayList<>();
        actionsUserIds1.add("19543977");

        List<String> actionsUserIds2 = new ArrayList<>();
        actionsUserIds2.add("99999");

        Task task1 = new Task();
        task1.setUserId("1234567");
        task1.setPageId("19543977");
        task1.setItemId("456239839");
        task1.setPriority(0);
        task1.setActionUserIds(actionsUserIds2);

        Task task2 = new Task();
        task2.setUserId("1234567");
        task2.setPageId("19543977");
        task2.setItemId("456239839");
        task2.setPriority(1);
        task2.setActionUserIds(actionsUserIds2);

        Task task3 = new Task();
        task3.setUserId("1234567");
        task3.setPageId("19543977");
        task3.setItemId("456239839");
        task3.setPriority(2);
        task3.setActionUserIds(actionsUserIds2);

        Task task4 = new Task();
        task4.setUserId("1234567");
        task4.setPageId("19543977");
        task4.setItemId("456239839");
        task4.setPriority(0);
        task4.setActionUserIds(actionsUserIds1);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskRepository.save(task4);
    }

    @RequestMapping(value="/test",method = RequestMethod.GET)
    public String test(){
        return "hello there man";
    }

    @RequestMapping(value="/all",method = RequestMethod.GET)
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> newTask(@RequestBody Task task, UriComponentsBuilder uriComponentsBuilder){
        log.debug("get new Task: " + task.toString());
        taskService.addNewTask(task);

        UriComponents uriComponents = uriComponentsBuilder.path("/{id}").buildAndExpand(task.getId());

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(@PathVariable String id){
        log.debug("deleting Task: " + id);
        taskService.deleteTask(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateTask(@RequestBody Task task){
        log.debug("updating Task: " + task.toString());
        taskService.updateTask(task);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/increment", method = RequestMethod.POST)
    public void incrementTaskCount(@RequestParam String id, @RequestParam("userId") String userActionId){
        log.debug("incrementing Like Task: " +id);
        taskService.incrementTaskCount(id, userActionId);
    }

    @RequestMapping(value="/running", method = RequestMethod.GET)
    public List<Task> getRunningTasks(@RequestParam String userId){
        return taskService.getTasks(userId, Task.TaskStatus.RUNNING);
    }

    @RequestMapping(value="/earn", method = RequestMethod.GET)
    public List<Task> getEarnTasks(@RequestParam String userId, @RequestParam String bdateString,
                                   @RequestParam int sex, @RequestParam int vkStatus,
                                   @RequestParam int countryId, @RequestParam int cityId){
        return taskService.getEarnTasks(userId, bdateString, sex, vkStatus, countryId, cityId);
    }

    @RequestMapping(value="/clear",method = RequestMethod.GET)
    public ResponseEntity clear(){
        taskService.clearAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/mem",method = RequestMethod.GET)
    public String mem(){

        String res = "";

        Runtime runtime = Runtime.getRuntime();
        final NumberFormat format = NumberFormat.getInstance();
        final long maxMemory = runtime.maxMemory();
        final long allocatedMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long mb = 1024 * 1024;
        final String mega = " MB";
        log.info("========================== Memory Info ==========================");
        log.info("Free memory: " + format.format(freeMemory / mb) + mega);
        log.info("Allocated memory: " + format.format(allocatedMemory / mb) + mega);
        log.info("Max memory: " + format.format(maxMemory / mb) + mega);
        log.info("Total free memory:" + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
        log.info("=================================================================\n");


        String maxMemory1 = format.format(maxMemory / mb) + mega;

        return maxMemory1;
    }
}
