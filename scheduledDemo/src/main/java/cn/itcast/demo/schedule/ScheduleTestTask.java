package cn.itcast.demo.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 使用 原生 spring 注解定时器
 */
@Component
public class ScheduleTestTask {

    /**
     * 表示每2秒 执行任务
     */
    @Scheduled(cron = "0/2 * * * * ? ")
    public void scheduleTask(){
        System.out.println("现在时间 "+ new Date().toString());
    }


}
