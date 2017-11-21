package com.test.web1.service;

import com.test.mysql.entity.AppInfo;
import com.test.mysql.repository.AppInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
@Service
public class AppInfoService {
    @Autowired
    private AppInfoRepository appInfoRepositor;

    public AppInfo getAppInfo(){
        List<AppInfo> list = appInfoRepositor.findAll();
        if(null!=list&&!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }

    }
}
