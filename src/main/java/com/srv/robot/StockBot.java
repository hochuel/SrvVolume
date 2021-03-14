package com.srv.robot;

import ch.qos.logback.classic.Logger;
import com.srv.config.DataQueue;
import com.srv.crawler.CrawlerMain;
import com.srv.main.service.VolumeService;
import com.srv.main.service.WalgorithmService;
import com.srv.utils.BeanUtils;
import com.srv.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class StockBot implements Runnable{

    private String name = "";
    public StockBot(String name){
        this.name = name;
    }

    @Override
    public void run() {

        DataQueue dataQueue = (DataQueue)BeanUtils.getBean("dataQueue");
        String stockCode = null;
        String algorithm = null;
        VolumeService volumeService = (VolumeService) BeanUtils.getBean("volumeService");

        WalgorithmService walgorithmService = (WalgorithmService) BeanUtils.getBean("walgorithmService");

        while(true) {
            try {
                Map data = dataQueue.getData();
                //log.info(this.name + "::종목정보 ::" + data);
                stockCode = (String) data.get("종목코드");
                algorithm = (String) data.get("알고리즘");
                //volumeService.updateMa(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Map error = null;

            if(StringUtils.isEmpty(algorithm)) {
                List resultList = null;
                CrawlerMain crawlerMain = new CrawlerMain();


                resultList = crawlerMain.getVolumeData(stockCode);
                if (resultList != null && resultList.size() > 0) {

                    for (int i = 0; i < resultList.size(); i++) {
                        Map data = (Map) resultList.get(i);
                        try {
                            data.put("종목코드", stockCode);
                            error = data;
                            // log.info(this.name + "::1 ::" + data);
                            volumeService.insertVolume(data);
                        } catch (Exception e) {
                            log.error("ERROR1 :" + error);
                            e.printStackTrace();
                        }
                    }
                }

                resultList = crawlerMain.getDayVolumeData(stockCode, 1);
                if (resultList != null && resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Map data = (Map) resultList.get(i);
                        try {
                            data.put("종목코드", stockCode);
                            error = data;
                            //log.info(this.name + "::2 ::" + data);
                            volumeService.updateVolume02(data);
                            volumeService.updateMa(data);

                        } catch (Exception e) {
                            log.error("ERROR2 :" + error);
                            e.printStackTrace();
                        }
                    }
                }

                resultList = crawlerMain.getKospiData("kospi", 1);
                if (resultList != null && resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Map data = (Map) resultList.get(i);
                        try {
                            data.put("종목코드", "KOSPI");
                            error = data;
                            //log.info(this.name + "::2 ::" + data);
                            volumeService.updateVolume02(data);
                        } catch (Exception e) {
                            log.error("ERROR2 :" + error);
                            e.printStackTrace();
                        }
                    }
                }

                resultList = crawlerMain.getKospiData("kosdaq", 1);
                if (resultList != null && resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Map data = (Map) resultList.get(i);
                        try {
                            data.put("종목코드", "KOSDAQ");
                            error = data;
                            //log.info(this.name + "::2 ::" + data);
                            volumeService.updateVolume02(data);
                        } catch (Exception e) {
                            log.error("ERROR2 :" + error);
                            e.printStackTrace();
                        }
                    }
                }

            }else if(algorithm.equals("W001")){
                try {
                    walgorithmService.WalgorithmMain(stockCode);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

/*
            try {
                Map data = new HashMap();
                data.put("종목코드", stockCode);

                data = volumeService.selectMaxDate(data);

                setRate(data, volumeService);
            } catch (Exception e) {
                e.printStackTrace();
            }
*/
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setRate(Map param, VolumeService volumeService) throws Exception {

        Map rateMap = volumeService.selectRate(param);

        if(rateMap != null) {
            log.info("rateMap ::" + rateMap);
            /*
            UPDATE t수익률 SET 진행일자 = #{진행일자}
                            , 수익률 = #{수익률}
                            , 진행금액=#{진행금액}
                              WHERE 종목코드 = #{종목코드}
                                AND 종료유무 = 'N'
             */
            String 날짜 = (String)param.get("날짜");

            //if(DateUtils.getDate().equals(날짜)) {
                String 시작금액 = String.valueOf(rateMap.get("시작금액"));
                String 종가 = String.valueOf(param.get("종가"));


                log.info("param ::" + param);
                double m1 = Double.parseDouble(시작금액);
                double m2 = Double.parseDouble(종가);

                double 수익률 = Math.round((m2 - m1) / m1 * 100);

                param.put("수익률", 수익률);
                param.put("진행일자", param.get("날짜"));
                param.put("진행금액", param.get("종가"));
                volumeService.updateRate(param);
           // }
        }
    }



}
