package com.tw.apistackbase.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    private int capacity;

    private String location;

    @OneToMany(mappedBy = "parkingLot")
    List<Order> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public ParkingLot() {
        orders = new ArrayList<>();
    }

    public ParkingLot(String name, int capacity, String location, List<Order> orders) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.orders = orders;
    }

    public boolean isAvailable() {
        return orders.size() < capacity;
    }
}
