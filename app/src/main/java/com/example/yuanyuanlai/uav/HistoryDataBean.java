package com.example.yuanyuanlai.uav;

public class HistoryDataBean {

    public static final int YEAR = 4;

    public static final int MONTH = 5;

    public static final int WEEK = 6;

    public static final int CHART = 1;

    public static final int DATA = 2;

    public static final int DATA_YEAR = 7;

    public static final int END = 3;

    private int type;

    private String date;

    private String time;

    private String oxygen;

    private String nitrogen;

    private String ph;

    private int chartSelect;

    public int getChartSelect() {
        return chartSelect;
    }

    public void setChartSelect(int chartSelect) {
        this.chartSelect = chartSelect;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getOxygen() {
        return oxygen;
    }

    public void setOxygen(String oxygen) {
        this.oxygen = oxygen;
    }

    public String getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(String nitrogen) {
        this.nitrogen = nitrogen;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

}
