package com.test.mysql.model;

import com.test.mysql.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class WeatherQo extends PageQo{

    private Long id;
    private String country;
    private  int state;


    public WeatherQo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
