package com.tw.apistackbase.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String status;

    private Date createdTime;

    private Date closedTime;

    private String licensePlateNumber;

    @ManyToOne
    @JoinColumn(name="parking_lot_id")
    private ParkingLot parkingLot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(Date closedTime) {
        this.closedTime = closedTime;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public Order() {
    }

    public Order(String status, Date createdTime, String licensePlateNumber) {
        this.status = status;
        this.createdTime = createdTime;
        this.licensePlateNumber = licensePlateNumber;
    }
}
