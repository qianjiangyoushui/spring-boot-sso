package com.test.mysql.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal volume;
    private int car;
    private String startplace;
    private String endplace;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date usedate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arriveddate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdate;

    @ManyToOne
    @JoinColumn(name="userid")
    @JsonBackReference
    private User  user;

    @ManyToOne
    @JoinColumn(name = "unitid")
    @JsonBackReference
    private Department department;
    @OneToMany
    @JoinColumn(name="scheduleid")
    private List<ConfirmSchedule> confirmList;


    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
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

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getStartplace() {
        return startplace;
    }

    public void setStartplace(String startplace) {
        this.startplace = startplace;
    }

    public String getEndplace() {
        return endplace;
    }

    public void setEndplace(String endplace) {
        this.endplace = endplace;
    }

    public User getUser() {
        return user;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<ConfirmSchedule> getConfirmList() {
        return confirmList;
    }

    public void setConfirmList(List<ConfirmSchedule> confirmList) {
        this.confirmList = confirmList;
    }
}
