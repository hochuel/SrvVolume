package com.srv.main.service;

import com.srv.config.DataQueue;
import com.srv.main.dao.VolumeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class VolumeService {

    @Autowired
    private VolumeDao volumeDao;

    @Autowired
    private DataQueue dataQueue;

    public int getCompanyList(String 알고리즘) throws Exception {

        List list = volumeDao.selectCompanyList(알고리즘);

        for(int i = 0; i < list.size(); i++){
            Map data = (Map)list.get(i);
            dataQueue.setData(data);
            //log.info("data :" + data);
        }

        return list.size();
    }

    public void getVolumeList() throws Exception {

        List list = volumeDao.selectVolumeList(null);

        for(int i = 0; i < list.size(); i++){
            Map data = (Map)list.get(i);
            dataQueue.setData(data);
            //log.info("data :" + data);
        }
    }

    public void insertVolume(Map param) throws Exception {
        volumeDao.insertVolume(param);
    }

    public int selectVolumeCnt(Map param) throws Exception {
        return volumeDao.selectVolumeCnt(param);
    }

    public void updateVolume(Map param) throws Exception {
        volumeDao.updateVolume(param);
    }

    public void updateVolume02(Map param) throws Exception {
        volumeDao.updteVolume02(param);
    }

    public void updateMa(Map param) throws Exception {
        volumeDao.updateMa(param);
    }

    public List selectRecommend(Map param) throws Exception{
        return volumeDao.selectRecommend(param);
    }

    public void insertRecommend(Map param) throws Exception{
        volumeDao.insertRecommend(param);
    }

    public void insertRate(Map param) throws Exception{
        volumeDao.insertRate(param);
    }

    public void updateRate(Map param) throws Exception{
        volumeDao.updateRate(param);
    }


    public Map selectRate(Map param) throws Exception{
        return  volumeDao.selectRate(param);
    }

    public Map selectMaxDate(Map param) throws Exception{
        return  volumeDao.selectMaxDate(param);
    }


    public List selectTodayRising(Map param) throws Exception{
        return  volumeDao.selectTodayRising(param);
    }

    public List selectListChartData(Map param) throws Exception{
        return  volumeDao.selectListChartData(param);
    }

    public Map selectCompayInfo(String 종목코드) throws Exception{
        return  volumeDao.selectCompayInfo(종목코드);
    }
}
