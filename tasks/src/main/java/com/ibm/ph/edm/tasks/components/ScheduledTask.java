package com.ibm.ph.edm.tasks.components;

import com.ibm.ph.edm.common.services.EmployeeActionItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jesus Lising <jess.lising@gmail.com>
 */

@Component
public class ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    EmployeeActionItemService employeeActionItemService;

    //second, minute, hour, day of month, month, day(s) of week
    //@Scheduled(fixedRate = 10000)
    @Scheduled(cron="0 2 5 * * MON")
    public void reportCurrentTime() throws Exception{
        employeeActionItemService.sendEmployeePendingActionItems();
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}