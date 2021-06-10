package com.example.smart.billModel;

public class BillModel {

    String date,time,id;
    Long tot_amt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BillModel(String date, String time, String id, Long tot_amt) {
        this.date = date;
        this.time = time;
        this.id = id;
        this.tot_amt = tot_amt;
    }

    public BillModel() {
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getTot_amt() {
        return tot_amt;
    }

    public void setTot_amt(Long tot_amt) {
        this.tot_amt = tot_amt;
    }


}
