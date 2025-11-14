package com.joeyworks.cinnamon.alarm.service;

import com.joeyworks.cinnamon.alarm.dto.AlarmDto;

public interface Alarm<T extends  AlarmDto> {
    public void createAlarm(T dto) throws Exception;
}
