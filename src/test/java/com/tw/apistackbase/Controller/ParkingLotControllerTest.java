package com.tw.apistackbase.Controller;

import com.tw.apistackbase.Model.ParkingLot;
import com.tw.apistackbase.Repository.ParkingLotRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    private List<ParkingLot> parkingLots;

    @Before
    public void setUp() throws Exception {
        parkingLots = IntStream.rangeClosed(1, 7).boxed()
                .map(x -> new ParkingLot(x + ": name", x, x + ": location"))
                .collect(Collectors.toList());
    }

    @Test
    public void should_return_a_parkingLot_when_post_it() throws Exception {
        // given
        ParkingLot parkingLot = new ParkingLot("name", 10, "ZhuHai");
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);
        // when
        mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\":\"name\",\n" +
                        "    \"capacity\":10,\n" +
                        "    \"location\":\"Zhuhai\"\n" +
                        "}"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("name"));
    }

    @Test
    public void should_delete_a_parkingLot_when_do_so() throws Exception {
        // given

        // when
        mockMvc.perform(delete("/parking-lots/1"))
                // then
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_parking_lots_page_by_page_when_get_them() throws Exception {
        // given
        when(parkingLotRepository.findAll()).thenReturn(parkingLots);
        // when
        mockMvc.perform(get("/parking-lots?page=1&pageSize=5"))
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    public void should_return_a_parkingLot_when_find_it_by_id() throws Exception {
        // given
        parkingLots.get(0).setId(1L);
        when(parkingLotRepository.findAll()).thenReturn(parkingLots);
        //when
        mockMvc.perform(get("/parking-lots/1"))
                // then
                .andExpect(jsonPath("$.name").value(parkingLots.get(0).getName()));
    }

    @Test
    public void should_return_a_parkingLot_when_update_its_capacity_by_id() throws Exception {
        // given
        parkingLots.get(0).setId(1L);
        parkingLots.get(0).setCapacity(1000);
        when(parkingLotRepository.findAll()).thenReturn(parkingLots);
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLots.get(0));
        // when
        mockMvc.perform(put("/parking-lots/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"capacity\":1000\n" +
                        "}"))
                // then
                .andExpect(jsonPath("$.capacity").value(1000));
    }
}
