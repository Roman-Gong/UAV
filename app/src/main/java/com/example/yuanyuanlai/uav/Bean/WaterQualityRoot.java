package com.example.yuanyuanlai.uav.Bean;

import java.util.List;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/11/2
 * @github https://github.com/Roman-Gong
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class WaterQualityRoot {
    private String flag;

    private List<DeviceList> deviceList ;

    private String msg;

    public void setFlag(String flag){
        this.flag = flag;
    }
    public String getFlag(){
        return this.flag;
    }
    public void setDeviceList(List<DeviceList> deviceList){
        this.deviceList = deviceList;
    }
    public List<DeviceList> getDeviceList(){
        return this.deviceList;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
}
