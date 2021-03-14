package com.srv.config;

import com.srv.main.service.VolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainExeConfig implements CommandLineRunner {

    @Autowired
    private VolumeService volumeService;

    @Override
    public void run(String... args) throws Exception {
        //volumeService.start();
    }
}
