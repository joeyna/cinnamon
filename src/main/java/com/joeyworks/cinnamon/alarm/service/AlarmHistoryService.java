package com.joeyworks.cinnamon.alarm.service;

import com.joeyworks.cinnamon.alarm.dto.AlarmHistoryDto;
import com.joeyworks.cinnamon.alarm.entity.AlarmHistory;
import com.joeyworks.cinnamon.alarm.repository.AlarmHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmHistoryService {

    private final AlarmHistoryRepository alarmHistoryRepository;

    public AlarmHistoryDto getById(Long id) {
        AlarmHistory history = alarmHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("alm_history not found"));
        return AlarmHistoryDto.from(history);
    }

    public AlarmHistoryDto getBytaskId(Long taskId) {
        AlarmHistory history = alarmHistoryRepository.findByTaskId(taskId)
                .orElseThrow(() -> new EntityNotFoundException("alm_history not found"));
        return AlarmHistoryDto.from(history);
    }

    public Long save(AlarmHistoryDto alarmHistoryDto) {
        AlarmHistory saved = alarmHistoryRepository.save(alarmHistoryDto.toEntity());
        return saved.getId();
    }

    public Long save(AlarmHistory alarmHistory) {
        AlarmHistory saved = alarmHistoryRepository.save(alarmHistory);
        return saved.getId();
    }

}
