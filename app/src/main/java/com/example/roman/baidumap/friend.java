package com.example.roman.baidumap;

import java.io.Serializable;

/**
 * Created by Roman on 2016/10/17.
 */
public class friend implements Serializable{
    private String name;
    private String number;
    private String latitude;
    private String longitude;
    private Double distant;

    public friend(){}

    public friend(String name, String number){
        this.name = name;
        this.number = number;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public void setDistant(Double distant){
        this.distant = distant;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }

    public String getLatitude(){
        return latitude;
    }

    public String getLongitude(){
        return longitude;
    }

    public Double getDistant(){
        return distant;
    }
}
