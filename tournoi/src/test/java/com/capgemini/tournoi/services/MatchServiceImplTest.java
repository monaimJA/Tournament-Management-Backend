package com.capgemini.tournoi.services;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.repos.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest

public class MatchServiceImplTest {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private  MatchServiceImpl matchService;
    private  MatchRequestDTO matchRequestDTO;
    @Test
    public void shouldSaveMatchWithSuccess(){







    }

}