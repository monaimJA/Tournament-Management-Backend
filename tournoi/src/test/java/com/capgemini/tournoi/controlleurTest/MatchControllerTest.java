package com.capgemini.tournoi.controlleurTest;

import com.capgemini.tournoi.controllers.MatchController;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.error.MatchNotFoundException;
import com.capgemini.tournoi.services.MatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MatchControllerTest {
    @InjectMocks
    private MatchController matchController;

    @Mock
    private MatchServiceImpl matchService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testGetMatchById_NonExistingId_ThrowsException() throws Exception {
        // Arrange
        Long id = 999L;
        when(matchService.getMatchById(id)).thenThrow(new MatchNotFoundException("match not found"));

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> matchController.getMatchById(id));
    }



}
