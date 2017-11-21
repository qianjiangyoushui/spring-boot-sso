package com.test.mysql.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class AppInfoQo extends PageQo{
    private Long id;
    private String appid;
    private String secret;
    private Integer loginDuration;
    private Integer sessionDuration;
    private String qcloudAppid;
    private String ip;

    public AppInfoQo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getLoginDuration() {
        return loginDuration;
    }

    public void setLoginDuration(Integer loginDuration) {
        this.loginDuration = loginDuration;
    }

    public Integer getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Integer sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public String getQcloudAppid() {
        return qcloudAppid;
    }

    public void setQcloudAppid(String qcloudAppid) {
        this.qcloudAppid = qcloudAppid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
