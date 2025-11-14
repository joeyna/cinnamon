package com.joeyworks.cinnamon.task.repository;

import com.joeyworks.cinnamon.task.entity.ProcessTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessTaskRepository extends JpaRepository<ProcessTask, Long> {
}
