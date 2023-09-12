package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduleTaskUtil {

    private final Logger logger = LoggerFactory.getLogger(ScheduleTaskUtil.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 6000)
    public void process(){
        logger.error("现在时间是{}",dateFormat.format(new Date()));
    }

    @Scheduled(cron = "*/6 * * * * ?")
    public void processOther(){
        logger.error("现在时间是processOther{}",dateFormat.format(new Date()));
    }


}
