package com.capgemini.tournoi;

import com.capgemini.tournoi.dtos.MatchRequestDTO;
import com.capgemini.tournoi.entity.Match;
import com.capgemini.tournoi.repos.MatchRepository;
import com.capgemini.tournoi.security.entities.AppRole;
import com.capgemini.tournoi.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@SpringBootApplication
public class TournoiApplication {
	@Autowired
	public MatchRepository matchRepository;

	public static void main(String[] args) {
		SpringApplication.run(TournoiApplication.class, args);
		//MatchRequestDTO matchRequestDTO=new MatchRequestDTO();
		//matchRequestDTO.<setTeamId2>(Long.valueOf(1));
		//matchRequestDTO.setTeamId1(Long.valueOf(2));
		//matchRequestDTO.setStartTime();
	}

	//@Bean
	CommandLineRunner start(AccountService accountService){
		return args->{
			accountService.save(new AppRole(null,"USER"));
			accountService.save(new AppRole(null,"ADMIN"));
			Stream.of("user1","user2","user3","admin").forEach(un->{
				accountService.saveUser(un,"1234","1234");
			});
			accountService.addRoleToUser("admin","ADMIN");

		};


    }
	@Bean
	BCryptPasswordEncoder getBCPE(){
		return new BCryptPasswordEncoder();
	}
}
