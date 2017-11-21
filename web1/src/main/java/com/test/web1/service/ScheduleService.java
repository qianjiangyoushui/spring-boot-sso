package com.test.web1.service;

import com.alibaba.fastjson.JSONObject;
import com.test.mysql.entity.ConfirmSchedule;
import com.test.mysql.entity.Schedule;
import com.test.mysql.entity.SessionInfo;
import com.test.mysql.entity.User;
import com.test.mysql.model.ScheduleQo;
import com.test.mysql.repository.ConfirmScheduleRepository;
import com.test.mysql.repository.ScheduleRepository;
import com.test.mysql.repository.SessionInfoRepository;
import com.test.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017/10/13.
 */
@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ConfirmScheduleRepository confirmScheduleRepository;

    public Page<Schedule> pageList(ScheduleQo scheduleQo){
        Pageable pageable = new PageRequest(scheduleQo.getPage(), scheduleQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
        return scheduleRepository.findByUser(scheduleQo.getUser(), pageable);
    }
    @Transactional
    public Schedule save(Schedule schedule){
        schedule =  scheduleRepository.save(schedule);
        ConfirmSchedule confirmSchedule = new ConfirmSchedule();
        confirmSchedule.setDepartment(schedule.getUser().getDepartment());
        confirmSchedule.setUser(schedule.getUser());
        confirmSchedule.setCreatedate(Calendar.getInstance().getTime());
        confirmSchedule.setSchedule(schedule);
        ArrayList list = new ArrayList();
        confirmSchedule = confirmScheduleRepository.save(confirmSchedule);
        list.add(confirmSchedule);
        schedule.setConfirmList(list);
        return schedule;
    }
}
