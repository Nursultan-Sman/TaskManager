package com.bestprofi.controllers;

import com.bestprofi.models.Task;
import com.bestprofi.repositories.TaskRepository;
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
        taskRepository.save(task);
        return "redirect:/";
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

    @GetMapping("/activeTask/{id}")
    public String activeTask(@PathVariable("id") Task task) {
        taskRepository.delete(task);
        return "redirect:/";
    }
}
