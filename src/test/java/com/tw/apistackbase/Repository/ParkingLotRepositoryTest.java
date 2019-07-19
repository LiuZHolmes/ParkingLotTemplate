package com.tw.apistackbase.Repository;

import com.tw.apistackbase.Model.ParkingLot;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingLotRepositoryTest {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    private List<ParkingLot> parkingLots;

    @Before
    public void setUp() {
        parkingLots = IntStream.rangeClosed(1, 2).boxed()
                .map(x -> new ParkingLot(x + ": name", x, x + ": location" ,new ArrayList<>()))
                .collect(Collectors.toList());
    }

    @Test
    public void should_throw_exception_when_save_same_name_parkingLot() {
        // given
        parkingLotRepository.save(parkingLots.get(0));
        ParkingLot parkingLot = new ParkingLot(parkingLots.get(0).getName(),10,"GuangZhou" ,new ArrayList<>());
        // when
        assertThrows(DataIntegrityViolationException.class,()->{
            parkingLotRepository.save(parkingLot);
            parkingLotRepository.flush();
        });
    }

    @Test
    public void should_throw_exception_when_save_same_negative_capacity_parkingLot() {
        // given
        ParkingLot parkingLot = new ParkingLot("negative",-1,"GuangZhou",new ArrayList<>());
        // when
        assertThrows(DataIntegrityViolationException.class,()->{
            parkingLotRepository.save(parkingLot);
            parkingLotRepository.flush();
        });
    }
}
