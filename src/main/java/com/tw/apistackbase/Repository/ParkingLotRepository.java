package com.tw.apistackbase.Repository;

import com.tw.apistackbase.Model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {
}
