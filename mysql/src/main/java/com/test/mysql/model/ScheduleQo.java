package com.test.mysql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.test.mysql.entity.ConfirmSchedule;
import com.test.mysql.entity.Department;
import com.test.mysql.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class ScheduleQo extends PageQo{

    private Long id;
    private Long  userid;
    private String unitid;
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date usedate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arriveddate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishdate;

    public ScheduleQo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getUsedate() {
        return usedate;
    }

    public void setUsedate(Date usedate) {
        this.usedate = usedate;
    }

    public Date getArriveddate() {
        return arriveddate;
    }

    public void setArriveddate(Date arriveddate) {
        this.arriveddate = arriveddate;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
