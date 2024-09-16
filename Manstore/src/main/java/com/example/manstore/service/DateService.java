package com.example.manstore.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateService {

    public static Date lastDate(String startDate){
        LocalDate convertedDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        convertedDate = convertedDate.withDayOfMonth(
                convertedDate.getMonth().length(convertedDate.isLeapYear()));
        return Date.valueOf(convertedDate.toString());
    }

    public static Date lastDateByQuy(Integer quy, Integer year){
        String startDate = "";
        if(quy == 1){
            startDate = year+"-03-01";
        }
        if(quy == 2){
            startDate = year+"-06-01";
        }
        if(quy == 3){
            startDate = year+"-09-01";
        }
        if(quy == 4){
            startDate = year+"-12-01";
        }
        LocalDate convertedDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        convertedDate = convertedDate.withDayOfMonth(
                convertedDate.getMonth().length(convertedDate.isLeapYear()));
        return Date.valueOf(convertedDate.toString());
    }

    public static Date firstDateByQuy(Integer quy, Integer year){
        String startDate = "";
        if(quy == 1){
            startDate = year+"-01-01";
        }
        if(quy == 2){
            startDate = year+"-04-01";
        }
        if(quy == 3){
            startDate = year+"-07-01";
        }
        if(quy == 4){
            startDate = year+"-10-01";
        }
        return Date.valueOf(startDate);
    }

}