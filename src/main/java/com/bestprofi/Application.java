package com.bestprofi;

import com.bestprofi.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        taskRepository.updateTasksStatusCreated();
    }
}
