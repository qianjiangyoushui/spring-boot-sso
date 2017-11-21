package com.test.web1.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.test.mysql.entity.ConfirmSchedule;
import com.test.mysql.entity.Schedule;
import com.test.mysql.entity.SessionInfo;
import com.test.mysql.entity.User;
import com.test.mysql.model.ScheduleQo;
import com.test.mysql.repository.ScheduleRepository;
import com.test.mysql.repository.UserRepository;
import com.test.web1.constant.Constants;
import com.test.web1.service.ScheduleService;
import com.test.web1.service.SessionInfoService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/weapp/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private SessionInfoService sessionInfoService;
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/list")
    public @ResponseBody
    String getUserinfo(HttpServletRequest request){
        String skey=request.getHeader(Constants.WX_HEADER_SKEY);
        SessionInfo sessionInfo = sessionInfoService.getSessionInfoBySessionkey(skey);
        User user =userRepository.findByOpenid(sessionInfo.getOpenId());
        ScheduleQo scheduleQo = new ScheduleQo();
        scheduleQo.setUserid(user.getId());
        scheduleQo.setUser(user);
        JSONObject result = new JSONObject();
        Page<Schedule> pageList = scheduleService.pageList(scheduleQo);
        result.put("content",pageList.getContent());
        result.put("total",pageList.getTotalElements());
        result.put("page",pageList.getTotalPages());
        result.put("size",pageList.getSize());
        /*String re = result.toString();
        String re1 = result.toJSONString();*/
        return result.toString();
    }

    @RequestMapping("/add")
    public @ResponseBody
    String add(HttpServletRequest request, HttpServletResponse response, Model model)throws  Exception{
        String skey=request.getHeader(Constants.WX_HEADER_SKEY);
        SessionInfo sessionInfo = sessionInfoService.getSessionInfoBySessionkey(skey);
        User user =userRepository.findByOpenid(sessionInfo.getOpenId());
        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);
        JSONObject jsonObject = JSONObject.parseObject(jb.toString());
        Schedule schedule = new Schedule();
        String volume = jsonObject.getString("volume");
        BigDecimal volum =new BigDecimal(volume);
        schedule.setVolume(volum);
        int car = Integer.parseInt(jsonObject.getString("car"));
        schedule.setCar(car);
        schedule.setStartplace(jsonObject.getString("startplace"));
        schedule.setEndplace(jsonObject.getString("endplace"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date usedate = dateFormat.parse(jsonObject.getString("usedate"));
        schedule.setUsedate(usedate);
        schedule.setUser(user);
        schedule.setCreatedate(Calendar.getInstance().getTime());
        schedule.setDepartment(user.getDepartment());
        scheduleService.save(schedule);
        JSONObject result = new JSONObject();
        result.put("state","success");
        return result.toJSONString();
    }

}
