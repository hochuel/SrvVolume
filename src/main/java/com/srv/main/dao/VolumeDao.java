package com.srv.main.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface VolumeDao {

    public List selectCompanyList(String algorithm) throws Exception;

    public void insertVolume(Map param) throws Exception;

    public void updateVolume(Map param) throws Exception;

    public int selectVolumeCnt(Map param) throws Exception;

    public List selectVolumeList(Map param) throws Exception;

    public void updteVolume02(Map param) throws Exception;

    public void updateMa(Map param) throws Exception;

    public List algorithm001(Map param) throws Exception;

    public List selectRecommend(Map param) throws Exception;

    public void insertRecommend(Map param) throws Exception;

    public void insertRate(Map param) throws Exception;

    public void updateRate(Map param) throws Exception;

    public Map selectRate(Map param) throws Exception;

    public List selectRateResult(Map param) throws Exception;

    public Map selectMaxDate(Map param) throws Exception;

    public List selectTodayRising(Map param) throws Exception;

    public List selectListChartData(Map param) throws Exception;
    
    public Map selectCompayInfo(String 종목코드) throws Exception;
}
