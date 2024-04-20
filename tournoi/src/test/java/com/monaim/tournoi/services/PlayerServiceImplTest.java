package com.monaim.tournoi.services;

import com.monaim.tournoi.mappers.PlayerMapper;
import com.monaim.tournoi.repos.PlayerRepository;
import com.monaim.tournoi.repos.TeamRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

/*
    @Test
    void getAllPlayersOfATeam() {
        Player player=new Player(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,null,null);
        PlayerDto playerDto=new PlayerDto(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,"team1",2,3);
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
                "0656373562", PlayerStatus.INSCRIT,"team1",3,2);
        Mockito.when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        Mockito.when(playerMapper.convertPlayerToPlayerDTO(player)).thenReturn(playerDto);
        PlayerDto playerDto1=playerService.getPlayerById(1L);
        Assertions.assertEquals(playerDto1,playerDto);
    }

    @Test
    void assignPlayerToTeam() {
        Team team=new Team(1L,"team1",new Site(1L,"RABAT"), StatusTeam.INSCRIPTION,null,
                new Tournament(1L,"tournament1",LocalDate.now() , LocalDate.now(),
                        StatusTournamentAndMatch.DEMI_FINAL,false, null));
        PlayerDto playerDto=new PlayerDto(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,"team1",3,2);
        Player player=new Player(1L,"monaim","ennabbali","monaime08@gmail.com",
                "0656373562", PlayerStatus.INSCRIT,null,null);
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        Mockito.when(playerMapper.convertPlayerToPlayerDTO(player)).thenReturn(playerDto);
        Mockito.when(playerRepository.save(player)).thenReturn(player);
        playerService.assignPlayerToTeam(player,1L);
        Assertions.assertEquals(playerService.assignPlayerToTeam(player,1L),playerDto);
    }*/
}