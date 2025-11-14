package com.joeyworks.cinnamon.alarm.repository;

import com.joeyworks.cinnamon.alarm.entity.AlarmHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory, Long> {
    Optional<AlarmHistory> findByTaskId(Long task_id);
}
