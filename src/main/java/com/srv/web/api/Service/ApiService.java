package com.srv.web.api.Service;

import com.srv.main.dao.VolumeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApiService {

    @Autowired
    private VolumeDao volumeDao;

    public List getAlgorithm001() throws Exception {
        return volumeDao.algorithm001(null);
    }

    public List selectRateResult(Map param) throws Exception{
        return  volumeDao.selectRateResult(param);
    }
}
