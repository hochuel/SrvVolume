package com.srv.main.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface WalgorithmDao {

    public Map selectBeforMaxData(Map param) throws Exception;

    public Map selectMinData(Map param) throws Exception;

    public Map selectMaxMiddleData(Map param) throws Exception;

    public Map selectMinMiddleData(Map param) throws Exception;

    public Map selectCurrentData(String 종목코드) throws Exception;

}
