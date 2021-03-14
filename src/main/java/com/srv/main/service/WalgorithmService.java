package com.srv.main.service;

import com.srv.main.dao.VolumeDao;
import com.srv.main.dao.WalgorithmDao;
import com.srv.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WalgorithmService {

    @Autowired
    private WalgorithmDao walgorithmDao;

    @Autowired
    private VolumeDao volumeDao;


    public void WalgorithmMain(String 종목코드) throws Exception {

        Map param = new HashMap<String, String>();
        param.put("종목코드", 종목코드);


        Map data01 = walgorithmDao.selectMinData(param);

        Map data01_1 = null;
        if(data01 != null) {
            data01.put("종목코드", 종목코드);
            data01_1 = walgorithmDao.selectBeforMaxData(data01);

            //log.info("data01_1 ::" +종목코드 +":" + data01_1);

        }

        Map data02 = null;
        if(data01_1 != null) {
            data01.put("종목코드", 종목코드);
            data02 = walgorithmDao.selectMaxMiddleData(data01);

        }

        Map data03 = null;
        if(data02 != null) {
            data02.put("날짜", DateUtils.getAddDay((String) data02.get("날짜"), 1));
            data02.put("종목코드", 종목코드);
            data03 = walgorithmDao.selectMinMiddleData(data02);

        }

        if(data03 != null){

            int f0 = Integer.parseInt(String.valueOf(data01_1.get("종가")));

            int f1 = Integer.parseInt(String.valueOf(data01.get("종가")));
            int f2 = Integer.parseInt(String.valueOf(data02.get("종가")));
            int f3 = Integer.parseInt(String.valueOf(data03.get("종가")));

            Map fData = walgorithmDao.selectCurrentData(종목코드);

            int f4 = Integer.parseInt(String.valueOf(fData.get("종가")));

            long 거래량 = Long.parseLong(String.valueOf(fData.get("거래량")));

            if(f0 >= f2 && (f0 > f1 && f1 < f2 && f2 > f3 && f3 < f4 && f2 > f4)) {
                data03.put("알고리즘", "W001");
                data03.put("종목코드", 종목코드);
                data03.put("날짜", fData.get("날짜"));
                log.info("등록 W001 ::" + 종목코드 +":" +f0 + ":" + f1 + ":" + f2 +":" + f3 +":" + f4);
                try {
                    volumeDao.insertRecommend(data03);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
