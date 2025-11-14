package com.joeyworks.cinnamon.alarm.entity;

import com.joeyworks.cinnamon.common.vo.Dates;
import com.joeyworks.cinnamon.task.entity.ProcessTask;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alarm_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String msg;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alarm_definition_id", foreignKey = @ForeignKey(name = "fk_alarm_history_definition"))
    private AlarmDefinition definition;

    @ManyToOne
    @JoinColumn(name = "task_id", foreignKey = @ForeignKey(name = "fk_alarm_history_process_task"))
    private ProcessTask task;

    @Embedded
    private Dates dates;

    public static AlarmHistory of(Long id, String msg, AlarmDefinition definition, ProcessTask task, Dates dates) {
        if (dates == null) dates = Dates.create();

        return new AlarmHistory(id, msg, definition, task, dates);
    }

}