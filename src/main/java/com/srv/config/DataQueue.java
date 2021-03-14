package com.srv.config;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class DataQueue {

    private BlockingQueue<Map> queue = new LinkedBlockingQueue<Map>();

    public void setData(Map param){
        try {
            queue.put(param);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Map getData() throws InterruptedException {
        return queue.take();
    }

    public int getDataCnt(){
        return queue.size();
    }

    public void setRemove(){
        queue.removeAll(queue);
    }

}
