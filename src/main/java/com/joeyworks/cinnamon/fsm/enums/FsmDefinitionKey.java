package com.joeyworks.cinnamon.fsm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FsmDefinitionKey {
    SYNOLOGY_ALARM_WEBHOOK("ALARM"),
    SYNOLOGY_ALARM_SYSLOG("ALARM"),
    SYNOLOGY_ALARM_SNMP("ALARM");

    private final String definitionKey;
}
