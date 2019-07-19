package com.tw.apistackbase.Controller;

import com.tw.apistackbase.Model.Car;
import com.tw.apistackbase.Model.Order;
import com.tw.apistackbase.Model.ParkingLot;
import com.tw.apistackbase.Repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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
                .findAll(PageRequest.of(page, pageSize))
                .stream().collect(Collectors.toList()));
    }

    @GetMapping("/parking-lots/{id}")
    public ResponseEntity findParkingLotByID(@PathVariable long id) throws Exception {
        return ResponseEntity.ok(parkingLotRepository.findById(id).orElseThrow(Exception::new));
    }

    @PutMapping("/parking-lots/{id}")
    public ResponseEntity updateParkingLotSetCapacityByID(@PathVariable long id, @RequestBody ParkingLot parkingLot) throws Exception {
        ParkingLot newParkingLot = parkingLotRepository.findById(id).orElseThrow(Exception::new);
        newParkingLot.setCapacity(parkingLot.getCapacity());
        newParkingLot = parkingLotRepository.save(newParkingLot);
        return ResponseEntity.ok(newParkingLot);
    }

    @PutMapping("/parking-lots/{id}/orders/")
    public ResponseEntity parkCarAndCreateOrder(@PathVariable long id, @RequestBody Car car) throws Exception {
        ParkingLot parkingLot = parkingLotRepository.findById(id).orElseThrow(Exception::new);
        if (parkingLot.isAvailable()) {
            Order order = new Order("open", new Date(), car.getLicensePlateNumber());
            parkingLot.getOrders().add(order);
            parkingLotRepository.save(parkingLot);
            return ResponseEntity.ok(order);
        } else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/parking-lots/{parkingLotid}/orders/{licensePlateNumber}")
    public ResponseEntity fetchCarAndCloseOrder(@PathVariable long parkingLotid, @PathVariable String licensePlateNumber) throws Exception {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotid).orElseThrow(Exception::new);
        parkingLot.getOrders()
                .stream()
                .filter(x -> x.getLicensePlateNumber().equals(licensePlateNumber))
                .findFirst().orElseThrow(Exception::new).setStatus("close");
        parkingLotRepository.save(parkingLot);
        return ResponseEntity.ok().build();
    }
}
