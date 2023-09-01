package cn.itcast.demo.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 使用 原生 spring 注解定时器
 */
@Component
public class ScheduleTestTask {


    @Scheduled(cron = "4-40 * * * * ?")
    public void scheduleTask(){
        System.out.println("现在时间 "+ new Date().toString());
    }


}
