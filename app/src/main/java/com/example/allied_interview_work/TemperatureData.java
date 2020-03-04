package com.example.allied_interview_work;

public class TemperatureData {
    Boolean Clickable;
    String startTime;
    String endTime;
    String parameterName;
    String parameterUnit;
    public TemperatureData(Boolean c, String start, String end, String parameter, String unit){
        Clickable = c;
        startTime = start;
        endTime = end;
        parameterName = parameter;
        parameterUnit = unit;
    }
    public String printContent(){
        return startTime + "\n" + endTime + "\n" + parameterName + parameterUnit;
    }
}
