package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.entity.Site;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.entity.Tournament;
import com.capgemini.tournoi.enums.PlayerStatus;
import com.capgemini.tournoi.enums.StatusTeam;
import com.capgemini.tournoi.enums.StatusTournament;
import com.capgemini.tournoi.error.PlayerNotFoundException;
import com.capgemini.tournoi.mappers.PlayerMapper;
import com.capgemini.tournoi.repos.PlayerRepository;
import com.capgemini.tournoi.repos.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void getAllPlayersOfATeam() {
        Player player=new Player(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,null,null);
        PlayerDto playerDto=new PlayerDto(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,"team1");
        List<Player> players= Collections.singletonList(player);
        Mockito.when(playerRepository.findAllByTeam_Id(1L)).thenReturn(players);
        Mockito.when(playerMapper.convertPlayerToPlayerDTO(player)).thenReturn(playerDto);
        List<PlayerDto> playerDtos=playerService.getAllPlayersOfATeam(1L);
        Assertions.assertEquals(playerDtos,Collections.singletonList(playerDto));

    }

    @Test
    void getPlayerById() throws PlayerNotFoundException {
        Player player=new Player(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,null,null);
        PlayerDto playerDto=new PlayerDto(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,"team1");
        Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        Mockito.when(playerMapper.convertPlayerToPlayerDTO(player)).thenReturn(playerDto);
        PlayerDto playerDto1=playerService.getPlayerById(1L);
        Assertions.assertEquals(playerDto1,playerDto);
    }

    @Test
    void assignPlayerToTeam() {
        Team team=new Team(1L,"team1",new Site(1L,"RABAT"), StatusTeam.INSCRIPTION,
                new Tournament(1L,"tournament1", new Date(), new Date(),
                        StatusTournament.DEMI_FINAL,null));
        PlayerDto playerDto=new PlayerDto(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,"team1");
        Player player=new Player(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,null,null);
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        Mockito.when(playerMapper.convertPlayerToPlayerDTO(player)).thenReturn(playerDto);
        Mockito.when(playerRepository.save(player)).thenReturn(player);
        playerService.assignPlayerToTeam(player,1L);
        Assertions.assertEquals(playerService.assignPlayerToTeam(player,1L),playerDto);
    }
}