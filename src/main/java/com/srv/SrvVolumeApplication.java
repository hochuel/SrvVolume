package com.srv;

import com.srv.main.service.VolumeService;
import com.srv.robot.StockBot;
import com.srv.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class SrvVolumeApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SrvVolumeApplication.class, args);

        int threadCnt = 50;//Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);
        for(int i = 0; i < threadCnt; i++) {
            executorService.execute(new StockBot("thread_" + i));
        }
    }
}
