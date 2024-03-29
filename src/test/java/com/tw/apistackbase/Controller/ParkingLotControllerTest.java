package com.tw.apistackbase.Controller;

import com.tw.apistackbase.Model.Order;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
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
                .map(x -> new ParkingLot(x + ": name", x, x + ": location",new ArrayList<>()))
                .collect(Collectors.toList());
    }

    @Test
    public void should_return_a_parkingLot_when_post_it() throws Exception {
        // given
        ParkingLot parkingLot = new ParkingLot("name", 10, "ZhuHai", new ArrayList<>());
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
        Page<ParkingLot> pagedParkingLots = new PageImpl(parkingLots.stream().limit(5).collect(Collectors.toList()));
        when(parkingLotRepository.findAll()).thenReturn(parkingLots);
        when(parkingLotRepository.findAll(any(PageRequest.class))).thenReturn(pagedParkingLots);
        // when
        mockMvc.perform(get("/parking-lots?page=1&pageSize=5"))
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    public void should_return_a_parkingLot_when_find_it_by_id() throws Exception {
        // given
        parkingLots.get(0).setId(1L);
        when(parkingLotRepository.findById(anyLong())).thenReturn(Optional.ofNullable(parkingLots.get(0)));
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
        when(parkingLotRepository.findById(anyLong())).thenReturn(Optional.ofNullable(parkingLots.get(0)));
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

    @Test
    public void should_return_an_order_when_park_a_car() throws Exception {
        // given
        ParkingLot parkingLot = parkingLots.get(0);
        parkingLot.setId(1L);
        when(parkingLotRepository.findById(anyLong())).thenReturn(Optional.of(parkingLot));
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(parkingLot);
        // when
        mockMvc.perform(put("/parking-lots/1/orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"license-plate-number\":\"123456\"\n" +
                        "}"))
                // then
                .andExpect(jsonPath("$.status").value("open"));
    }

    @Test
    public void should_close_an_order_when_fetch_car() throws Exception {
        // given
        ParkingLot parkingLot = parkingLots.get(0);
        parkingLot.setId(1L);
        Order order = new Order("close",new Date(),"123456");
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        parkingLot.setOrders(orders);
        when(parkingLotRepository.findById(anyLong())).thenReturn(Optional.of(parkingLot));
        // when
        mockMvc.perform(delete("/parking-lots/1/orders/123456"))
                // then
                .andExpect(status().isOk());
    }

    @Test
    public void should_error_when_park_a_car_and_parkingLot_is_full () throws Exception {
        // given
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLotRepository.findById(anyLong())).thenReturn(Optional.of(parkingLot));
        when(parkingLot.isAvailable()).thenReturn(false);
        // when
        mockMvc.perform(put("/parking-lots/1/orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"license-plate-number\":\"123456\"\n" +
                        "}"))
                // then
                .andExpect(status().isBadRequest());
    }
}
