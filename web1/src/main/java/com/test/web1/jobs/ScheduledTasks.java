package com.test.web1.jobs;

import com.test.web1.service.WeatherInsertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {
    @Autowired
    WeatherInsertService weatherInsertService;
    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date()));
    }

    //每1分钟执行一次
    @Scheduled(cron = "0 */5 *  * * * ")
    public void reportCurrentByCron(){
//        weatherInsertService.deleteDataAll();
//        weatherInsertService.excute();
        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
    }
    //每天8点15执行一次
    @Scheduled(cron = "0 0 08 * * ?")
    public void weatherDelete(){
        weatherInsertService.deleteDataAll();
        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
    }
    //每天8点15执行一次
    @Scheduled(cron = "0 15 08 * * ?")
    public void weatherReport(){
        weatherInsertService.excute();
        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat("HH:mm:ss");
    }

}
