package com.capgemini.tournoi.teamServiceTest;

import com.capgemini.tournoi.dtos.TeamDto;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.mappers.TeamMapper;
import com.capgemini.tournoi.repos.TeamRepository;
import com.capgemini.tournoi.services.TeamService;
import com.capgemini.tournoi.services.TeamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private TeamMapper teamMapper;
    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    public void testTeamsList() {
        List<Team> mockTeams = Arrays.asList(new Team(),new Team());
        List<TeamDto> mockTeamDtos = mockTeams.stream()
                .map(team -> new TeamDto())
                .collect(Collectors.toList());

      when(teamRepository.findAll()).thenReturn(mockTeams);
      when(teamMapper.fromTeam(any())).thenReturn(new TeamDto());

        List<TeamDto> result = teamService.teamsList();
         assertEquals(mockTeamDtos.size(),result.size());
    }
}


