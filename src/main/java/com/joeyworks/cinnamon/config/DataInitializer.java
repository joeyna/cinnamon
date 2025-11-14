package com.joeyworks.cinnamon.config;

import com.joeyworks.cinnamon.alarm.entity.AlarmDefinition;
import com.joeyworks.cinnamon.alarm.repository.AlarmDefinitionRepository;
import com.joeyworks.cinnamon.fsm.entity.FsmAction;
import com.joeyworks.cinnamon.fsm.entity.FsmDefinition;
import com.joeyworks.cinnamon.fsm.entity.FsmState;
import com.joeyworks.cinnamon.fsm.entity.FsmTransition;
import com.joeyworks.cinnamon.fsm.repository.FsmActionRepository;
import com.joeyworks.cinnamon.fsm.repository.FsmDefinitionRepository;
import com.joeyworks.cinnamon.fsm.repository.FsmStateRepository;
import com.joeyworks.cinnamon.fsm.repository.FsmTransitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.state.PseudoStateKind;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AlarmDefinitionRepository alarmDefinitionRepository;
    private final FsmDefinitionRepository fsmDefinitionRepository;
    private final FsmStateRepository fsmStateRepository;
    private final FsmActionRepository fsmActionRepository;
    private final FsmTransitionRepository fsmTransitionRepository;


    @Override
    public void run(String... args) throws Exception {
        FsmState fsmState1 = null;
        FsmState fsmState2 = null;
        FsmAction fsmAction = null;

        if (alarmDefinitionRepository.count() == 0) {
            alarmDefinitionRepository.save(AlarmDefinition.of(null,"Synology NAS Webhook Alarm", "Synology NAS",  null,"Synology Webhook", null));
        }

        if (fsmDefinitionRepository.count() == 0) {
            fsmDefinitionRepository.save(FsmDefinition.of(null, "ALARM", 1, null, null, "ALARM"));
        }
        if (fsmActionRepository.count() == 0) {
            fsmAction = FsmAction.of(null, "SlackNotification", null);
            fsmActionRepository.save(fsmAction);
        }

        if (fsmStateRepository.count() == 0) {
            Set<FsmAction> actionSet = new HashSet<>();
            actionSet.add(fsmAction);

            fsmState1 = FsmState.ofInitial(1L,"1","EVENT_RECIEVED",true, PseudoStateKind.INITIAL, null,null,null );
            fsmStateRepository.save(fsmState1);

            fsmState2 = FsmState.ofInitial(2L,"1","EVENT_COMPLETED",false, PseudoStateKind.END, null,actionSet,null );
            fsmStateRepository.save(fsmState2);
        }

        if(fsmTransitionRepository.count() == 0) {
            fsmTransitionRepository.save(FsmTransition.ofInitial("1", fsmState1, fsmState2, "EVENT_CONFIRM"));
        }
    }
}
