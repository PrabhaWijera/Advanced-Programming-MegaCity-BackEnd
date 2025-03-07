package com.prabhash.megacity.dto;

import java.math.BigDecimal;

public class BookingDTO {
    private int id;
    private int userId;
    private int carId;
    private String startDate;
    private String endDate;
    private double totalAmount;
    private String status;
    private String fromPlace;  // New field
    private String toPlace;    // New field
    private String driverName; // New field
    private String createdAt;
    private String updatedAt;

    @Override
    public String toString() {
        return "BookingDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", carId=" + carId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", fromPlace='" + fromPlace + '\'' +
                ", toPlace='" + toPlace + '\'' +
                ", driverName='" + driverName + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BookingDTO() {
    }

    public BookingDTO(int id, int userId, int carId, String startDate, String endDate, double totalAmount, String status, String fromPlace, String toPlace, String driverName, String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.driverName = driverName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
