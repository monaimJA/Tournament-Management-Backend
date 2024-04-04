package com.capgemini.tournoi;

import com.capgemini.tournoi.entity.*;
import com.capgemini.tournoi.enums.PlayerStatus;
import com.capgemini.tournoi.enums.StatusTeam;
import com.capgemini.tournoi.enums.StatusTournament;
import com.capgemini.tournoi.repos.*;
import com.capgemini.tournoi.security.entities.AppRole;
import com.capgemini.tournoi.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class TournoiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournoiApplication.class, args);
	}

	@Bean
	CommandLineRunner start(AccountService accountService,
							TeamRepository teamsRepository,
							PlayerRepository playerRepository,
							SiteRepository siteRepository,
							TournamentRepository tournamentRepository,
							MatchRepository matchRepository,
							ScoreRepository scoreRepository,
							GoalRepository goalRepository){
		return args->{
			accountService.save(new AppRole(null,"USER"));
			accountService.save(new AppRole(null,"ADMIN"));
			Stream.of("user1","user2","user3","admin").forEach(un->{
				accountService.saveUser(un,"1234","1234");
			});
			accountService.addRoleToUser("admin","ADMIN");
			Stream.of("amin","kamal","samir","said","khalid","zakaria","rachid","moussa","mohamed","salim"
					,"rhal","driss","sulaiman","abdelfattah","el mahdi","hamid","salm","aimad","karim","saad").forEach(
					firstname -> playerRepository.save(Player.builder()
									.firstName(firstname)
									.lastName("Doe")
									.email(firstname+"@gmail.com")
									.phoneNumber("0661265345")
									.playerStatus(PlayerStatus.OBLIGATOIRE)
							.build())
			);
			Stream.of("RABAT", "CASABLANCA").forEach(
					name -> {
						siteRepository.save(Site.builder()
										.name(name)
								.build());
					}
			);
			LocalDate startDate = LocalDate.now();
			Stream.of("Ramathon", "Tounament22").forEach(
					label -> {
						tournamentRepository.save(
								Tournament.builder()
										.label(label)
										.startDate(startDate)
										.endDate(startDate.plusDays(30))
										.statusTournament(StatusTournament.INSCRIPTION)
										.build()
						);
					}
			);
			Stream.of("Real Madrid","Barcelona").forEach(
					name -> {
						teamsRepository.save(Team.builder()
										.name(name)
										.statusTeam(StatusTeam.INSCRIPTION)
										.site(siteRepository.findSiteByNameIs("CASABLANCA"))
										.tournament(tournamentRepository.findAll().get(0))
								.build());
					}
			);

			playerRepository.findAll().forEach(
					player -> {
						player.setTeam(Math.random() > 0.5 ? teamsRepository.findAll().get(0) : teamsRepository.findAll().get(1));
						playerRepository.save(player);
					}
			);
			matchRepository.save(Match.builder()
							.team1(teamsRepository.findAll().get(0))
							.team2(teamsRepository.findAll().get(1))
							.startTime(new Date())
							.score(scoreRepository.save(Score.builder().build()))
					.build());
			Match match = matchRepository.findAll().get(0);
			for (int i =0 ; i < playerRepository.findAll().size() ; i++) {
				for (int j = 0; j < i; j++) {
					match.getScore().getGoals().add(goalRepository.save(Goal.builder()
							.time(LocalTime.of(1, 23, 35))
							.player(playerRepository.findAll().get(i))
							.build()));
					scoreRepository.save(match.getScore());
				}
			}
			matchRepository.save(match);
		};
	}
	@Bean
	BCryptPasswordEncoder getBCPE(){
		return new BCryptPasswordEncoder();
	}
}
