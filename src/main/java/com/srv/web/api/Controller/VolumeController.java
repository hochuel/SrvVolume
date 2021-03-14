package com.srv.web.api.Controller;


import com.srv.config.DataQueue;
import com.srv.main.service.VolumeService;
import com.srv.utils.DateUtils;
import com.srv.web.api.Service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.sisu.Parameters;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class VolumeController {

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private DataQueue dataQueue;

    @Autowired
    private ApiService apiService;

    /**
     * 거래량 가져와 DataQueue에 넣기 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/volumeStart.do")
    public String volumeStart(@RequestParam String algorithm) throws Exception {

        algorithm = StringUtils.isEmpty(algorithm)?"":algorithm;

        int cnt = volumeService.getCompanyList(algorithm);
        return String.valueOf(cnt);
    }


    @RequestMapping(value = "/volumeStop.do")
    public int volumeStop() throws Exception {
        dataQueue.setRemove();
        return dataQueue.getDataCnt();
    }

    /**
     * DataQueue 갯수 가져오기
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/volumeCnt.do")
    public int volumeCnt()throws Exception {
        return dataQueue.getDataCnt();
    }

    @RequestMapping(value = "/getAlgorithm001.do")
    public List getAlgorithm001() throws Exception {

        List resultList = apiService.getAlgorithm001();

        for (int i = 0; i < resultList.size(); i++) {
            Map data = (Map) resultList.get(i);
            volumeService.insertRecommend(data);
//  VALUES(#{종목코드}, #{추천일자}, #{시작일자}, #{추천금액}, #{시작금액})
            data.put("추천일자", data.get("날짜"));
            data.put("시작일자", data.get("날짜"));
            data.put("추천금액", data.get("종가"));
            data.put("시작금액", data.get("종가"));
            //volumeService.insertRate(data);
        }

        return resultList;
    }


    @RequestMapping(value = "/getResult.do")
    public List getResult() throws Exception {

        List resultList = volumeService.selectRecommend(null);

        return resultList;
    }


    @RequestMapping(value = "/selectTodayRising.do")
    public List selectTodayRising(HttpServletRequest request) throws Exception {
        String type = request.getParameter("type");

        String sDate = request.getParameter("sDate");
        String eDate = request.getParameter("eDate");

        Map param = new HashMap();
        param.put("type", type);
        param.put("sDate", sDate);
        param.put("eDate", eDate);

        List resultList = volumeService.selectTodayRising(param);

        return resultList;
    }



    @RequestMapping(value = "/selectListChartData.do")
    public Map selectListChartData(HttpServletRequest request) throws Exception {

        String param = request.getParameter("param");
        String type = request.getParameter("type");

        String sDate = request.getParameter("sDate");
        String eDate = request.getParameter("eDate");

        log.info("param :" + param +":"+type);

        Map result = new HashMap();
        List resultList = null;

        Map param01 = new HashMap();

        //param01.put("sDate", DateUtils.getAddMonth(eDate, -5));
        param01.put("sDate", sDate);
        param01.put("eDate", eDate);

        String[] temp = param.split(",");

        if(type.equals("KOSPI") || type.equals("KOSPI200")){
            param01.put("종목코드", "KOSPI");
        }else{
            param01.put("종목코드", "KOSDAQ");
        }
        resultList = volumeService.selectListChartData(param01);
        result.put(type, resultList);

        for(int i = 0; i < temp.length; i++){
            if(!StringUtils.isEmpty(temp[i])) {

                Map compayInfo = volumeService.selectCompayInfo(temp[i]);
                result.put(temp[i]+"_info", compayInfo);


                param01.put("종목코드", temp[i]);
                resultList = volumeService.selectListChartData(param01);
                result.put(temp[i]+"_data", resultList);
            }

        }





/*
        param01.put("종목코드", "014820");
        resultList = volumeService.selectListChartData(param01);
        result.put("동원시스템즈", resultList);


        param01.put("종목코드", "010950");
        resultList = volumeService.selectListChartData(param01);
        result.put("S-OIL", resultList);
*/

        return result;
    }

}
