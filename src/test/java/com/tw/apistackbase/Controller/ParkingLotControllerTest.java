package com.tw.apistackbase.Controller;

import com.tw.apistackbase.Model.ParkingLot;
import com.tw.apistackbase.Repository.ParkingLotRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_return_a_parkingLot_when_post_it() throws Exception {
        // given
        ParkingLot parkingLot = new ParkingLot("name",10,"ZhuHai");
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
}
