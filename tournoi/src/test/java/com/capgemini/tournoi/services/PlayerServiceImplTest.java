package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.entity.Player;
import com.capgemini.tournoi.enums.PlayerStatus;
import com.capgemini.tournoi.mappers.PlayerMapper;
import com.capgemini.tournoi.repos.PlayerRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

@SpringBootTest
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

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
    void getPlayerById() {
    }

    @Test
    void assignPlayerToTeam() {
    }

    @Test
    void deletePlayerByIdFromTeam() {
    }

    @Test
    void updatePlayerById() {
    }

    @Test
    void getPlayersInTournoiByCardType() {
    }

    @Test
    void getPlayersInMatchByCardType() {
    }

    @Test
    void getAllPlayersOfTournament() {
    }
}