package com.joeyworks.cinnamon.fsm.action;

import com.joeyworks.cinnamon.alarm.dto.AlarmHistoryDto;
import com.joeyworks.cinnamon.alarm.service.AlarmHistoryService;
import com.joeyworks.cinnamon.fsm.entity.FsmState;
import com.joeyworks.cinnamon.slack.service.SlackNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.ObjectStateMachine;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component("SlackNotification")
@RequiredArgsConstructor
public class SlackNotificationAction extends FsmBaseAction implements Action<FsmState, String> {

    private final SlackNotificationService slackNotificationService;
    private final AlarmHistoryService alarmHistoryService;

    @Override
    public void execute(StateContext<FsmState, String> context) {
        String taskId = getTaskId(context);
        log.info("Slack Notification Action task Id: {}", taskId);

        AlarmHistoryDto alarmHistoryDto = alarmHistoryService.getBytaskId(Long.parseLong(taskId));
        slackNotificationService.sendMessage(alarmHistoryDto.getMsg());

    }
}
