package com.bestprofi.controllers;

import com.bestprofi.models.Task;
import com.bestprofi.repositories.TaskRepository;
import com.bestprofi.services.SchedulerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private SchedulerService schedulerService;

    @GetMapping("/")
    public String getMainPage(Model model) {
        model.addAttribute("tasks", taskRepository.findAllOrderById());
        return "main";
    }

    @GetMapping("/createTask")
    public String createTask(Model model) {
        model.addAttribute("task", new Task());
        return "createTaskForm";
    }

    @PostMapping("/save")
    public String saveTask(@Valid Task task, Errors errors) {
        if (errors.hasErrors()) {
            return "createTaskForm";
        }
        task.setStatus("Created");
        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/viewTask/{id}")
    public String viewTask(@PathVariable("id") long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id: " + id));
        model.addAttribute("task", task);
        model.addAttribute("logs",task.getLogs());
        return "viewTaskForm";
    }

    @GetMapping("/editTask/{id}")
    public String editTask(@PathVariable("id") long id, Model model) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id: " + id));
        model.addAttribute("task", task);
        return "editTaskForm";
    }

    @PostMapping("/updateTask/{id}")
    public String updateTask(@PathVariable("id") Task tasFromDb, @ModelAttribute(name = "task") Task task) {
        BeanUtils.copyProperties(task, tasFromDb, "id");
        taskRepository.save(tasFromDb);
        return "redirect:/";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") Task task) {
        taskRepository.delete(task);
        return "redirect:/";
    }

    @GetMapping("/activateTask/{id}")
    public String activateTask(@PathVariable("id") Task task) {
        schedulerService.activateTask(task);
        return "redirect:/";
    }

    @GetMapping("/deactivateTask/{id}")
    public String deactivateTask(@PathVariable("id") Task task) {
        schedulerService.deactivateTask(task);
        return "redirect:/";
    }

    @GetMapping("/startTask/{id}")
    public String startTask(@PathVariable("id") Task task) {
        schedulerService.startTask(task);
        return "redirect:/";
    }

    @GetMapping("/stopTask/{id}")
    public String stopTask(@PathVariable("id") Task task) {
        schedulerService.interruptJob(task);
        return "redirect:/";
    }
}
