package com.monaim.tournoi.controlleurTest;

import com.monaim.tournoi.controllers.MatchController;
import com.monaim.tournoi.error.MatchNotFoundException;
import com.monaim.tournoi.services.MatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

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
