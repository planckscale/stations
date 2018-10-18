package com.home.stations.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.stations.domain.Station;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StationController.class)
public class StationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    StationController controller;


    @Test
    public void show() throws Exception {

        Station station = createStation();
        given(controller.show(station.getId())).willReturn(station);

        mvc.perform(get("/station/"+station.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("stationId", is(station.getStationId())));
    }

    @Test
    public void create() throws Exception {

        Station station = createStation();
        given(controller.create(station)).willReturn(station);

        MvcResult result =
                mvc.perform(post("/station")
                        .content(toJson(station))
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isCreated())
//                .andExpect(jsonPath("stationId", is(station.getStationId())))  // why is return content null for in test for this DSL?
                        .andReturn();

        assertThat(result.getResponse().getContentAsString().contains("\"stationId:"+station.getStationId()+"\""));
    }

    @Test
    public void update() throws Exception {

        Station station = createStation();
        given(controller.update(station.getId(), station)).willReturn(station);

        MvcResult result =
                mvc.perform(put("/station/"+station.getId())
                        .content(toJson(station))
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        assertThat(result.getResponse().getContentAsString().contains("\"stationId:"+station.getStationId()+"\""));
    }

    @Test
    public void remove() throws Exception {

        doNothing().when(controller).delete(anyLong());

        mvc.perform(delete("/station/"+anyLong())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void searchEnabled() throws Exception {

        Station station = createStation();
        List<Station> allEnabled = Arrays.asList(station);
        given(controller.searchEnabled()).willReturn(allEnabled);

        mvc.perform(get("/station/enabled")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].stationId", is(station.getStationId())));
    }

    @Test
    public void searchTerm() throws Exception {

        String searchTerm = "a%20search%20name";
        Station station = createStation();
        List<Station> allSearchedTerm = Arrays.asList(station);
        given(controller.searchStationIdOrNameFuzzy(searchTerm)).willReturn(allSearchedTerm);

        mvc.perform(get("/station/search?term="+searchTerm)
                .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].stationId", is(station.getStationId())));
    }


    private Station createStation() {
        Station station = new Station();
        station.setId(RandomUtils.nextLong()); // note exists after persisted only
        station.setStationId(RandomStringUtils.randomAlphanumeric(5));
        station.setHdEnabled(true);
        station.setName(RandomStringUtils.randomAlphabetic(10));
        station.setCallSign(RandomStringUtils.randomAlphabetic(10));
        return station;
    }

    private String toJson(Station station) throws JsonProcessingException {
        System.out.print(new ObjectMapper().writeValueAsString(station));
        return new ObjectMapper().writeValueAsString(station);
    }
}
