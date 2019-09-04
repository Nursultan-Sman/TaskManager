package com.bestprofi.repositories;

import com.bestprofi.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from task t order by t.id")
    List<Task> findAllOrderById();

    @Modifying
    @Query("update task t set t.status='Created'")
    void updateTasksStatusCreated();
}
