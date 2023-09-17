package cn.itcast.demo.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsycTestDemo {

    /**
     * 不带返回值
     */
    @Async(value = "taskThread") //异步
    public void test(){
        try {
            Thread.sleep(3000L);
            System.out.println(Thread.currentThread() + " 异步线程运行了 业务方法");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Async(value = "taskThread")//异步
    public Future<String> test2(){
        try {
            Thread.sleep(3000L);
            System.out.println(Thread.currentThread() + " 异步线程运行了 业务方法");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        AsyncResult<String> result = new AsyncResult<>("这是的要返回的信息");
        return result;
    }

}
