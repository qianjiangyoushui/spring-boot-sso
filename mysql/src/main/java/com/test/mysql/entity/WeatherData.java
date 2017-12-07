package com.test.mysql.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "WeatherData")
public class WeatherData implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  country;
    private String name;
    private String range1;
    private String  day1;
    private String night1;
    private String range2;
    private String day2;
    private String night2;
    private String range3;
    private String day3;
    private String night3;
    private String range4;
    private String day4;
    private String night4;
    private String apidate;
    @Column(columnDefinition="tinyint default 0")
    private int state;

    public WeatherData() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRange1() {
        return range1;
    }

    public void setRange1(String range1) {
        this.range1 = range1;
    }

    public String getDay1() {
        return day1;
    }

    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getNight1() {
        return night1;
    }

    public void setNight1(String night1) {
        this.night1 = night1;
    }

    public String getRange2() {
        return range2;
    }

    public void setRange2(String range2) {
        this.range2 = range2;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public String getNight2() {
        return night2;
    }

    public void setNight2(String night2) {
        this.night2 = night2;
    }

    public String getRange3() {
        return range3;
    }

    public void setRange3(String range3) {
        this.range3 = range3;
    }

    public String getDay3() {
        return day3;
    }

    public void setDay3(String day3) {
        this.day3 = day3;
    }

    public String getNight3() {
        return night3;
    }

    public void setNight3(String night3) {
        this.night3 = night3;
    }

    public String getRange4() {
        return range4;
    }

    public void setRange4(String range4) {
        this.range4 = range4;
    }

    public String getDay4() {
        return day4;
    }

    public void setDay4(String day4) {
        this.day4 = day4;
    }

    public String getNight4() {
        return night4;
    }

    public void setNight4(String night4) {
        this.night4 = night4;
    }

    public String getApidate() {
        return apidate;
    }

    public void setApidate(String apidate) {
        this.apidate = apidate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
