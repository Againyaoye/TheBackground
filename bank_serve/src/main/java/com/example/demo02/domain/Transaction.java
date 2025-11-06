package com.example.demo02.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;


@TableName("transaction")
public class Transaction {

    @TableId(value = "transaction_id", type = IdType.ASSIGN_ID)
    private String transactionId;          // 交易记录id
    private String userId;

    private String machineId;              // 设备表的id
    private String orderStatus;            // 订单状态
    private Double totalLiters;            // 总加水量
    private Double finalAmount;            // 最终金额

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;       // 交易开始的时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;         // 交易结束的时间

    private String waterAddSwitch;         // stm32的开水开关
    private String fillUp;                 // 是否加满
    private String deviceTemperature;      // 设备温度
    private String batteryLevel;           // 电池电量
    private String latitudeAndLongitude;   // 经纬度
    // 用户id

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalLiters() {
        return totalLiters;
    }

    public void setTotalLiters(Double totalLiters) {
        this.totalLiters = totalLiters;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getWaterAddSwitch() {
        return waterAddSwitch;
    }

    public void setWaterAddSwitch(String waterAddSwitch) {
        this.waterAddSwitch = waterAddSwitch;
    }

    public String getFillUp() {
        return fillUp;
    }

    public void setFillUp(String fillUp) {
        this.fillUp = fillUp;
    }

    public String getDeviceTemperature() {
        return deviceTemperature;
    }

    public void setDeviceTemperature(String deviceTemperature) {
        this.deviceTemperature = deviceTemperature;
    }

    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getLatitudeAndLongitude() {
        return latitudeAndLongitude;
    }

    public void setLatitudeAndLongitude(String latitudeAndLongitude) {
        this.latitudeAndLongitude = latitudeAndLongitude;
    }




    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", userId='" + userId + '\'' +
                ", machineId='" + machineId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalLiters=" + totalLiters +
                ", finalAmount=" + finalAmount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", waterAddSwitch='" + waterAddSwitch + '\'' +
                ", fillUp='" + fillUp + '\'' +
                ", deviceTemperature='" + deviceTemperature + '\'' +
                ", batteryLevel='" + batteryLevel + '\'' +
                ", latitudeAndLongitude='" + latitudeAndLongitude + '\'' +
                '}';
    }




}
