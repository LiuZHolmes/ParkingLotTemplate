package com.tw.apistackbase.Controller;

import com.tw.apistackbase.Model.ParkingLot;
import com.tw.apistackbase.Repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParkingLotController {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @PostMapping("/parking-lots")
    public ResponseEntity postParkingLot(@RequestBody ParkingLot newParkingLot) {
        ParkingLot parkingLot = parkingLotRepository.save(newParkingLot);
        return ResponseEntity.ok(parkingLot);
    }

    @DeleteMapping("/parking-lots/{id}")
    public ResponseEntity deleteParkingLotByID(@PathVariable String id) {
        parkingLotRepository.deleteById(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }

}
