package com.rafid.Report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ASUS on 02-Jun-17.
 */
public class LineData {
    String x[];
    int y[];
    SimpleDateFormat simpleDateFormat;
    @JsonProperty
    String  mode;
    @JsonProperty
    String  name;
    @JsonIgnore
    ObjectMapper mapper;

    public LineData(String[] date, int[] value,String name) {
        this.x = date;
        this.y = value;
        simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
        this.mode="line";
        this.name=name;
        mapper = new ObjectMapper();
    }

    public LineData(String  name) {

        simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
        this.mode="line";
        this.name=name;
        mapper = new ObjectMapper();
    }

    public String[] getX() {
        return x;
    }
    public  void addX(Date date1)
    {
        if(x==null)
        {
            x=new String[1];
            x[0]=simpleDateFormat.format(date1);
        }
        else
        {
            String[] temp=new String[x.length+1];
            System.arraycopy(x,0,temp,0,x.length);
            temp[x.length]=simpleDateFormat.format(date1);
            x=temp;
        }
    }
    public void addY(int value1)
    {
        if(y==null)
        {
            y=new int[1];
            y[0]=value1;
        }
        else
        {
            int[] temp=new int[y.length+1];
            System.arraycopy(y,0,temp,0,y.length);
            temp[y.length]=value1;
            y=temp;
        }
    }

    public void setX(String[] date) {
        this.x = date;
    }

    public int[] getY() {
        return y;
    }

    public void setY(int[] value) {
        this.y = value;
    }
    @JsonIgnore
    public  String getAsJsonString() throws IOException {
        LineData array[]=new LineData[1];
        array[0]=this;
        return mapper.writeValueAsString(array);

    }
}
