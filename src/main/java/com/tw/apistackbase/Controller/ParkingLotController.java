package com.tw.apistackbase.Controller;

import com.tw.apistackbase.Model.ParkingLot;
import com.tw.apistackbase.Repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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

    @GetMapping("/parking-lots")
    public ResponseEntity getParkingLots(@RequestParam(value = "page",defaultValue = "1") long page,
                                         @RequestParam(value = "pageSize", defaultValue = "0") long pageSize) {
        return pageSize == 0 ? ResponseEntity.ok(parkingLotRepository.findAll())
        : ResponseEntity.ok(parkingLotRepository.findAll().stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize).collect(Collectors.toList()));
    }
}
