package com.capgemini.tournoi.testUnitaire;

import com.capgemini.tournoi.controllers.MatchController;
import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Score;
import com.capgemini.tournoi.services.MatchServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MatchControllerTest {
    private MockMvc mockMvc;

    @Mock
    private MatchServiceImpl matchService;

    @InjectMocks
    private MatchController matchController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(matchController).build();
    }

    @Test
    public void testCreateMatch() throws Exception {
        MatchRequestDTO requestDTO = new MatchRequestDTO();
        requestDTO.setStartTime("12:20");
        requestDTO.setTeamId1(1L);
        requestDTO.setTeamId2(2L);

        Match match = new Match();
        match.setId(1L);
        match.setStartTime(requestDTO.getStartTime());
        when(matchService.createMatch(any())).thenReturn(match);

        mockMvc.perform(post("/match/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllMatches() throws Exception {
        Match match = new Match();
        match.setId(1L);
        match.setStartTime("12:30");
        when(matchService.getAllMatches()).thenReturn(Collections.singletonList(match));

        mockMvc.perform(get("/match/getAll"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMatchById() throws Exception {
        Match match = new Match();
        match.setId(1L);
        match.setStartTime("13:45");
        when(matchService.getMatchById(1L)).thenReturn(match);

        mockMvc.perform(get("/match/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetMatchScore() throws Exception {
        Score score = new Score();
        score.setId(1L);
        when(matchService.getMatchScore(1L)).thenReturn(score);

        mockMvc.perform(get("/match/1/score"))
                .andExpect(status().isOk());
    }




}
