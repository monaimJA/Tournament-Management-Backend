package com.capgemini.tournoi;

import com.capgemini.tournoi.repos.MatchRepository;
import com.capgemini.tournoi.repos.PlayerRepository;
import com.capgemini.tournoi.repos.TeamRepository;
import com.capgemini.tournoi.repos.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TournoiApplication {

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private TournamentRepository tournoiRepository;
	@Autowired
	private MatchRepository matchRepository;

	public static void main(String[] args) {
		SpringApplication.run(TournoiApplication.class, args);
	}

//	@Bean
//	CommandLineRunner start(AccountService accountService){
//		return args->{
//			accountService.save(new AppRole(null,"USER"));
//			accountService.save(new AppRole(null,"ADMIN"));
//			Stream.of("user1","user2","user3","admin").forEach(un->{
//				accountService.saveUser(un,"1234","1234");
//			});
//			accountService.addRoleToUser("admin","ADMIN");
//
//		};
//		};
//	}
	@Bean
	BCryptPasswordEncoder getBCPE(){
		return new BCryptPasswordEncoder();
	}
}
