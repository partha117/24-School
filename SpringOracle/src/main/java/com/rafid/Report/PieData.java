package com.rafid.Report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by ASUS on 02-Jun-17.
 */
public class PieData {
    String[]labels;
    double [] values;
    String type;
    @JsonIgnore
    int length;
    @JsonIgnore
    int currentValue;
    @JsonIgnore
    int currentLabel;
    @JsonIgnore
    ObjectMapper mapper;
    public PieData(String[] labels, double[] values) {
        this.labels = labels;
        this.values = values;
        this.type ="pie";
        this.currentLabel=0;
        this.currentValue=0;
        mapper = new ObjectMapper();
    }

    public PieData(int length) {
        this.length = length;
        labels=new String[length];
        values=new double[length];
        type="pie";
        this.currentLabel=0;
        this.currentValue=0;
        mapper = new ObjectMapper();
    }



    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public  void addLabels(String newLabel)
    {
        if(currentLabel<length)
        {
            labels[currentLabel]=newLabel;
            currentLabel++;
        }
    }
    public  void addValues(double newValue)
    {
        if(currentValue<=length)
        {
            values[currentValue]=newValue;
            currentValue++;
        }
    }

    public String[] getLabels() {
        return labels;
    }

    public double[] getValues() {
        return values;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }
    @JsonIgnore
    public  String getAsJsonString() throws IOException {
        PieData array[]=new PieData[1];
        array[0]=this;
        return mapper.writeValueAsString(array);//.replace("\"","&quot;");

    }
}
