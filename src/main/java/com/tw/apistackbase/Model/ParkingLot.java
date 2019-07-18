package com.tw.apistackbase.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ParkingLot {

    @Id
    @GeneratedValue
    private String id;

    @Column(unique = true)
    private String name;

    private int capacity;

    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public ParkingLot() {
    }

    public ParkingLot(String name, int capacity, String location) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLot that = (ParkingLot) o;
        return capacity == that.capacity &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, location);
    }
}
