package com.tw.apistackbase.Controller;

import com.tw.apistackbase.Model.ParkingLot;
import com.tw.apistackbase.Repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
    public ResponseEntity getParkingLots(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "pageSize", defaultValue = "0") int pageSize) {
        return pageSize == 0 ? ResponseEntity.ok(parkingLotRepository.findAll())
                : ResponseEntity.ok(parkingLotRepository
                    .findAll(PageRequest.of(page,pageSize))
                    .stream().collect(Collectors.toList()));
    }

    @GetMapping("/parking-lots/{id}")
    public ResponseEntity findParkingLotByID(@PathVariable long id) {
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findAll()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst();
        if (optionalParkingLot.isPresent()) return ResponseEntity.ok(optionalParkingLot.get());
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("/parking-lots/{id}")
    public ResponseEntity updateParkingLotSetCapacityByID(@PathVariable long id, @RequestBody ParkingLot parkingLot) {

        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findAll()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst();
        if (optionalParkingLot.isPresent()) {
            ParkingLot newParkingLot = optionalParkingLot.get();
            newParkingLot.setCapacity(parkingLot.getCapacity());
            newParkingLot = parkingLotRepository.save(newParkingLot);
            return ResponseEntity.ok(newParkingLot);
        }
        else return ResponseEntity.notFound().build();
    }
}
