package cn.itcast.demo.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/async")
public class Controller {

    @Autowired
    private AsycTestDemo asycTestDemo;

    @RequestMapping("/asyncTest")
    public String asyncTest() throws ExecutionException, InterruptedException {
        System.out.println(Thread.currentThread() + "业务开始 " +   LocalDate.now());
        // 不带返回值
        //asycTestDemo.test();
        // 带返回值
        Future<String> stringFuture = asycTestDemo.test2();
        System.out.println(Thread.currentThread() + "业务结束 " +   LocalDate.now());
        return stringFuture.get();
    }


}
