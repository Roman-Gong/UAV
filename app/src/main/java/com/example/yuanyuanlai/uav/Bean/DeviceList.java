package com.example.yuanyuanlai.uav.Bean;

import java.util.List;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/11/2
 * @github https://github.com/Roman-Gong
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class DeviceList {
    private String createDateTime;

    private String defaultTimescale;

    private String deviceId;

    private String deviceIoc;

    private String deviceName;

    private String deviceNo;

    private String faultDelay;

    private String isDelete;

    private String isSetLink;

    private String isShare;

    private String lat;

    private String linktype;

    private String lng;

    private List<SensorList> sensorList ;

    public void setCreateDateTime(String createDateTime){
        this.createDateTime = createDateTime;
    }
    public String getCreateDateTime(){
        return this.createDateTime;
    }
    public void setDefaultTimescale(String defaultTimescale){
        this.defaultTimescale = defaultTimescale;
    }
    public String getDefaultTimescale(){
        return this.defaultTimescale;
    }
    public void setDeviceId(String deviceId){
        this.deviceId = deviceId;
    }
    public String getDeviceId(){
        return this.deviceId;
    }
    public void setDeviceIoc(String deviceIoc){
        this.deviceIoc = deviceIoc;
    }
    public String getDeviceIoc(){
        return this.deviceIoc;
    }
    public void setDeviceName(String deviceName){
        this.deviceName = deviceName;
    }
    public String getDeviceName(){
        return this.deviceName;
    }
    public void setDeviceNo(String deviceNo){
        this.deviceNo = deviceNo;
    }
    public String getDeviceNo(){
        return this.deviceNo;
    }
    public void setFaultDelay(String faultDelay){
        this.faultDelay = faultDelay;
    }
    public String getFaultDelay(){
        return this.faultDelay;
    }
    public void setIsDelete(String isDelete){
        this.isDelete = isDelete;
    }
    public String getIsDelete(){
        return this.isDelete;
    }
    public void setIsSetLink(String isSetLink){
        this.isSetLink = isSetLink;
    }
    public String getIsSetLink(){
        return this.isSetLink;
    }
    public void setIsShare(String isShare){
        this.isShare = isShare;
    }
    public String getIsShare(){
        return this.isShare;
    }
    public void setLat(String lat){
        this.lat = lat;
    }
    public String getLat(){
        return this.lat;
    }
    public void setLinktype(String linktype){
        this.linktype = linktype;
    }
    public String getLinktype(){
        return this.linktype;
    }
    public void setLng(String lng){
        this.lng = lng;
    }
    public String getLng(){
        return this.lng;
    }
    public void setSensorList(List<SensorList> sensorList){
        this.sensorList = sensorList;
    }
    public List<SensorList> getSensorList(){
        return this.sensorList;
    }
}
