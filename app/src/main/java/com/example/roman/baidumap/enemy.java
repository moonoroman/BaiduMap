package com.example.roman.baidumap;

import java.io.Serializable;

/**
 * Created by Roman on 2016/10/17.
 */
public class enemy implements Serializable {
    private int id;
    private String name;
    private String number;

    public enemy(){}

    public enemy(int id, String name, String number){
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }
}
