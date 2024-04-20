package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.CreateTournamentRequestDto;
import com.monaim.tournoi.entity.Tournament;
import com.monaim.tournoi.enums.StatusTournamentAndMatch;
import com.monaim.tournoi.error.TournamentAlreadyInProgressException;
import com.monaim.tournoi.globalExceptions.TournamentDateException;
import com.monaim.tournoi.mappers.TournamentMapper;
import com.monaim.tournoi.repos.TournamentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class TournamentServiceImplTest {
    @Mock
    TournamentMapper mapper;
    @Mock
    TournamentRepository tournamentRepository;
    @InjectMocks
    TournamentServiceImpl tournamentService ;
    @Test
    void should_create_tournament() throws TournamentDateException, TournamentAlreadyInProgressException {
        // given
        CreateTournamentRequestDto input = new CreateTournamentRequestDto("rackathon", LocalDate.now(), LocalDate.now().plusDays(30));
        Tournament expected = Tournament.builder().label("rackathon")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .statusTournament(StatusTournamentAndMatch.INSCRIPTION)
                .build();
        when(TournamentMapper.fromTournamentDtoRequest(input)).thenReturn(expected);
        when(tournamentRepository.save(expected)).thenReturn(expected);
        // when
        Tournament result = tournamentService.createTournament(input);
        // then
        assertEquals(expected, result);
    }
    @Test
    void should_throw_exception_when_startDate_is_after_endDate(){
        // given
        CreateTournamentRequestDto input = new CreateTournamentRequestDto("rackathon", LocalDate.now(), LocalDate.now().minusDays(30));
        // then
        assertThrows(TournamentDateException.class, () -> {
            tournamentService.createTournament(input);
        }) ;
    }
}