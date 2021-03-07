package com.article.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
  * 创建线程池，提供增加线程的服务
  *
  * @author: roger
 **/
@Service
@Data
public class ThreadPoolExecutorService {

    /**
     * 线程池
     */
    private ExecutorService executor;
    /**
     * 核心线程数
     */
    @Value("${core.pool.size}")
    private Integer corePoolSize;
    /**
     * 最大线程数，普通+核心
     */
    @Value("${maximum.pool.size}")
    private Integer maximumPoolSize;
    /**
     * 普通空闲线程存活时间
     */
    @Value("${keep.alive.time}")
    private long keepAliveTime;
    /**
     * 线程缓存大小
     */
    @Value("${queue.capacity.size}")
    private Integer queueCapacitySize;
    @PostConstruct
    public void init(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacitySize),namedThreadFactory,new ThreadPoolExecutor.CallerRunsPolicy());
    }
    /**
     * 功能描述: 利用线程池执行一个线程
     * @param runnable 一个执行的线程
     */
    public void add(Runnable runnable){
        executor.execute(runnable);
//        executor.shutdown();
    }
    public Future<Object>submit(Callable callable){
        return executor.submit(callable);
    }

    public static void main(String[] args){
        Object obj = "";
        System.out.println("".equals(obj));
    }
}
